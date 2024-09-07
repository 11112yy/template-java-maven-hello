package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingHistory {
    private String productName;
    private int quantity;
    private double Price;
    private String purchaseDate;
    public  double totalMoney;

    // 静态购物历史列表
    public static List<ShoppingHistory> shoppingHistoryList = new ArrayList<>();

    // 构造方法
    public ShoppingHistory(String productName, int quantity, double Price, String purchaseDate,double totalMoney) {
        this.productName = productName;
        this.quantity = quantity;
        this.Price = Price;
        this.purchaseDate = String.valueOf(purchaseDate);
        this.totalMoney=totalMoney;

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

    public double getTotalMoney() {
        totalMoney=Price*quantity;
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalMoney=getTotalMoney();
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
        this.totalMoney=getTotalMoney();
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = String.valueOf(purchaseDate);
    }

    // 添加购物记录到购物历史列表的方法
    public static void addShoppingRecord(String productName, int quantity, double Price, String purchaseDate,double totalMoney) {
        shoppingHistoryList.add(new ShoppingHistory(productName, quantity, Price, purchaseDate,totalMoney));

    }

    // 获取购物历史列表的方法
    public static List<ShoppingHistory> getShoppingHistoryList() {
        return shoppingHistoryList;
    }

    public void showShoppingHistory() {
        // 遍历购物历史列表并显示购物信息
        for (ShoppingHistory history : shoppingHistoryList) {
            System.out.println("购买日期：" + history.getPurchaseDate());
            System.out.println("总金额：" + history.getTotalMoney());
            System.out.println("商品名称：" + history.getProductName());
            System.out.println("数量：" + history.getQuantity());
            System.out.println("---------------------------");
        }
    }

    // 存储购物历史数据的文件路径
    private static final String SHOPPING_HISTORY_FILE_PATH = "src/main/java/org/example/shopping_history.txt";

    // 将购物历史列表保存到文件的方法
    public static void saveShoppingHistoryToFile() {
        try (FileWriter fileWriter = new FileWriter(SHOPPING_HISTORY_FILE_PATH)) {
            for (ShoppingHistory history : shoppingHistoryList) {
                fileWriter.write(history.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中加载购物历史列表的方法
    public static void loadShoppingHistoryFromFile() {
        shoppingHistoryList.clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SHOPPING_HISTORY_FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] historyData = line.split(",");
                if (historyData.length == 5) {
                    ShoppingHistory history = new ShoppingHistory(historyData[0], Integer.parseInt(historyData[1]),
                            Double.parseDouble(historyData[2]), historyData[3], Double.parseDouble(historyData[4]));
                    shoppingHistoryList.add(history);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // 将购物历史对象转换为逗号分隔的字符串

        return productName + "," + quantity + "," + Price + "," + purchaseDate + "," + totalMoney;
    }

    public static void createEmptyFileIfNotExists() {
        File file = new File(SHOPPING_HISTORY_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

