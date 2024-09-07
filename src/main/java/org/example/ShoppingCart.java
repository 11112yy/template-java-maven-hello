package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.example.Commodity.goodsList;
import java.sql.*;

public class ShoppingCart {
    String name;
    double ItemPrice;
    int number;
    static List<ShoppingCart> shoppingCartsList = new ArrayList<>();

    // SQLite数据库连接配置
    private static final String DB_URL = "jdbc:sqlite:shoppingCart.db";

    public ShoppingCart() {
    }

    public ShoppingCart(String name,  int number) {
        this.name = name;
        this.number = number;
    }

    public double getItemPrice(String name) {
        return ItemPrice;
    }

    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }

    public ShoppingCart(double itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    public void moveGoods(Scanner sc){
        System.out.println("请输入想要移除的商品的名称");
        String name = sc.next();
        Iterator<ShoppingCart> iterator = shoppingCartsList.iterator();
        int i=1;
        while (iterator.hasNext()) {
            ShoppingCart commodity = iterator.next();
            if (name.equals(commodity.getName())) {
                iterator.remove();
                System.out.println("已经成功从购物车中移除");
                i=0;
                break;
            }
        }
        if(i==1){
            System.out.println("查找不到这个商品");
        }
    }


    public void add(Scanner sc) {
        System.out.println("请输入要添加的商品名称：");
        String goods = sc.next();
        int i = 1;

        for (Commodity commodity : goodsList) {
            if (goods.equals(commodity.getGoods())) {
                System.out.println("请输入要添加的商品数量：");
                i = 0;
                int number = sc.nextInt();

                if (number <= commodity.getNumber()) {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setName(commodity.getGoods());
                    shoppingCart.setNumber(number);
                    shoppingCartsList.add(shoppingCart);
                    System.out.println("添加成功");
                    break;
                } else {
                    System.out.println("库存不足，现在还剩下" + commodity.getNumber());
                }
            }
        }

        if (i == 1) {
            System.out.println("商品不存在");
        }
    }

    public void changeGoodsNumber(Scanner sc) {
        System.out.println("请输入想要修改的商品名称：");
        String name = sc.next();
        System.out.println("请输入新的商品数量：");
        int newQuantity = sc.nextInt();

        Iterator<ShoppingCart> iterator = shoppingCartsList.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            ShoppingCart shoppingCart = iterator.next();
            if (name.equals(shoppingCart.getName())) {
                shoppingCart.setNumber(newQuantity);
                System.out.println("已成功修改商品数量");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("找不到该商品");
        }
    }

    // 加载购物车数据从数据库
    public static void loadShoppingCartFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM shopping_cart"; // 更换为实际的表名

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int number = resultSet.getInt("number");

                    ShoppingCart shoppingCart = new ShoppingCart(name, number);
                    shoppingCartsList.add(shoppingCart);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 保存购物车数据到数据库
    public static void saveShoppingCartToDatabase() {
        String insertQuery = "INSERT INTO shopping_cart (product_name, quantity) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            for (ShoppingCart cart : shoppingCartsList) {
                preparedStatement.setString(1, cart.getName());
                preparedStatement.setInt(2, cart.getNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // 创建购物车表
            String createTableQuery = "CREATE TABLE IF NOT EXISTS shopping_cart (" +
                    "product_name TEXT," +
                    "quantity INTEGER" +
                    ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
