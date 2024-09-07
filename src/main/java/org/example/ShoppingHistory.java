package org.example;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ShoppingHistory {
    private String productName;
    private int quantity;
    private double totalPrice;
    private String purchaseDate;
    
    // 静态购物历史列表
    public static List<ShoppingHistory> shoppingHistoryList = new ArrayList<>();
    
    // 静态变量，用于记录所有购物记录的总金额
    private static double totalMoney = 0.0;

    // SQLite数据库连接配置
    private static final String DB_URL = "jdbc:sqlite:shoppingHistory.db";

    // 构造方法
    public ShoppingHistory(String productName, int quantity, double totalPrice, String purchaseDate) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
        
        // 更新总金额
        updateTotalMoney(totalPrice);
    }

    public ShoppingHistory() {
    }

    // Getter 和 Setter 方法
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public static double getTotalMoney() {
        return totalMoney;
    }

    private static void updateTotalMoney(double amount) {
        totalMoney += amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    // 添加购物记录到购物历史列表的方法
    public static void addShoppingRecord(String productName, int quantity, double totalPrice, String purchaseDate) {
        shoppingHistoryList.add(new ShoppingHistory(productName, quantity, totalPrice, purchaseDate));
    }

    // 获取购物历史列表的方法
    public static List<ShoppingHistory> getShoppingHistoryList() {
        return shoppingHistoryList;
    }

    public void showShoppingHistory() {
        // 遍历购物历史列表并显示购物信息
        for (ShoppingHistory history : shoppingHistoryList) {
            System.out.println("购买日期：" + history.getPurchaseDate());
            System.out.println("总金额：" + history.getTotalPrice());
            System.out.println("商品名称：" + history.getProductName());
            System.out.println("数量：" + history.getQuantity());
            System.out.println("---------------------------");
        }
    }

    // 从数据库加载购物历史记录
    public static void loadShoppingHistoryFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM shopping_history"; // 更换为实际的表名

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int quantity = resultSet.getInt("quantity");
                    double totalPrice = resultSet.getDouble("total_price");
                    String purchaseDate = resultSet.getString("purchase_date");

                    // 从数据库中读取记录，并更新购物历史和总金额
                    addShoppingRecord(productName, quantity, totalPrice, purchaseDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 保存购物历史数据到数据库
    public static void saveShoppingHistoryToDatabase() {
        String insertQuery = "INSERT INTO shopping_history (product_name, quantity, total_price, purchase_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            for (ShoppingHistory history : shoppingHistoryList) {
                preparedStatement.setString(1, history.getProductName());
                preparedStatement.setInt(2, history.getQuantity());
                preparedStatement.setDouble(3, history.getTotalPrice());
                preparedStatement.setString(4, history.getPurchaseDate());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // 创建购物历史表
            String createTableQuery = "CREATE TABLE IF NOT EXISTS shopping_history (" +
                    "product_name TEXT," +
                    "quantity INTEGER," +
                    "total_price REAL," +
                    "purchase_date TEXT" +
                    ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
