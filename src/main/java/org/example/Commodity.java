package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class Commodity {
    private String numbering;
    private String goods;
    private String factory;
    private String time;
    private String model;
    double purchasePrice;
    double sellingPrice;
    int number;
    static List<Commodity> goodsList = new ArrayList<>();

    // SQLite数据库连接配置
    private static final String DB_URL = "jdbc:sqlite:goods.db";

    public Commodity() {
        ;
    }

    public Commodity(String numbering, String goods, String factory, String time, String model, double purchasePrice, double sellingPrice, int number) {
        this.numbering = numbering;
        this.goods = goods;
        this.factory = factory;
        this.time = time;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.number = number;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void add(Scanner sc) {
        System.out.println("请输入要添加的商品名称");
        String goods = sc.next();
        System.out.println("请输入商品编号");
        String numbering = sc.next();
        System.out.println("请输入生产厂家");
        String factory = sc.next();
        System.out.println("请输入生产日期");
        String time = sc.next();
        System.out.println("请输入型号");
        String model = sc.next();
        System.out.println("请输入进货价");
        Double purchasePrice = sc.nextDouble();
        System.out.println("请输入零售价格");
        Double sellingPrice = sc.nextDouble();
        System.out.println("请输入数量");
        int number = sc.nextInt();

        //商品编号、商品名称、生产厂家、生产日期、型号、进货价、零售价格、数量。
        Commodity commodity = new Commodity(numbering, goods, factory, time, model, purchasePrice, sellingPrice, number);
        goodsList.add(commodity);
    }

    public static void showGoods() {
        for (Commodity commodity : goodsList) {
            System.out.println("商品名称" + commodity.getGoods());
            System.out.println("商品编号: " + commodity.getNumbering());
            System.out.println("生产厂家: " + commodity.getFactory());
            System.out.println("生产日期: " + commodity.getTime());
            System.out.println("型号: " + commodity.getModel());
            System.out.println("进货价: " + commodity.getPurchasePrice());
            System.out.println("零售价格: " + commodity.getSellingPrice());
            System.out.println("数量: " + commodity.getNumber());
            System.out.println("---------------------------");
        }
    }

    public void changeGoodsNumber(Scanner sc) {
        System.out.println("请输入想要修改的商品名称");
        String name = sc.next();
        while (true) {
            System.out.println("请输入修改后的数量");
            int number = sc.nextInt();
            for (Commodity commodity : goodsList) {
                if (commodity.getGoods().equals(name)) {
                    commodity.setNumber(number);
                    System.out.println("商品修改成功！");
                    return;
                }
            }
            System.out.println("找不到指定的商品");
        }
    }

    public void moveGoods(Scanner sc) {
        System.out.println("请输入想要删除的商品的名称");
        String name = sc.next();
        Iterator<Commodity> iterator = goodsList.iterator();
        while (iterator.hasNext()) {
            Commodity commodity = iterator.next();
            if (name.equals(commodity.getGoods())) {
                iterator.remove();
                System.out.println("删除成功");
                break;
            } else {
                System.out.println("查找不到这个商品");
            }
        }
    }

    public void searchgoods(Scanner sc) {
        System.out.println("请输入想要查询的商品的名称");
        String name = sc.next();
        for (Commodity commodity : goodsList) {
            if (name.equals(commodity.getGoods())) {
                System.out.println("商品名称" + commodity.getGoods());
                System.out.println("商品编号: " + commodity.getNumbering());
                System.out.println("生产厂家: " + commodity.getFactory());
                System.out.println("生产日期: " + commodity.getTime());
                System.out.println("型号: " + commodity.getModel());
                System.out.println("进货价: " + commodity.getPurchasePrice());
                System.out.println("零售价格: " + commodity.getSellingPrice());
                System.out.println("数量: " + commodity.getNumber());
                System.out.println("---------------------------");
            } else {
                System.out.println("查找不到这个商品");
            }
        }
    }

    // 加载商品数据从数据库
    public static void loadGoodsFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM goods"; // 更换为实际的表名

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String numbering = resultSet.getString("numbering");
                    String goods = resultSet.getString("goods");
                    String factory = resultSet.getString("factory");
                    String time = resultSet.getString("time");
                    String model = resultSet.getString("model");
                    double purchasePrice = resultSet.getDouble("purchase_price");
                    double sellingPrice = resultSet.getDouble("selling_price");
                    int number = resultSet.getInt("number");

                    Commodity commodity = new Commodity(numbering, goods, factory, time, model, purchasePrice, sellingPrice, number);
                    goodsList.add(commodity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 保存商品数据到数据库
    public static void saveGoodsToDatabase() {
        String insertQuery = "INSERT INTO goods (numbering, goods, factory, time, model, purchase_price, selling_price, number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            for (Commodity commodity : goodsList) {
                preparedStatement.setString(1, commodity.getNumbering());
                preparedStatement.setString(2, commodity.getGoods());
                preparedStatement.setString(3, commodity.getFactory());
                preparedStatement.setString(4, commodity.getTime());
                preparedStatement.setString(5, commodity.getModel());
                preparedStatement.setDouble(6, commodity.getPurchasePrice());
                preparedStatement.setDouble(7, commodity.getSellingPrice());
                preparedStatement.setInt(8, commodity.getNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // 创建商品表
            String createGoodsTableQuery = "CREATE TABLE IF NOT EXISTS goods (" +
                    "numbering TEXT PRIMARY KEY," +
                    "goods TEXT," +
                    "factory TEXT," +
                    "time TEXT," +
                    "model TEXT," +
                    "purchase_price REAL," +
                    "selling_price REAL," +
                    "number INTEGER" +
                    ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createGoodsTableQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}