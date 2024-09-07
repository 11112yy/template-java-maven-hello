package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.example.Commodity.shangpinList;

public class ShoppingCart {
    String name;
    double ItemPrice;
    int number;
    static List<ShoppingCart> shoppingCartsList = new ArrayList<>();

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



    public void moveShangpin(Scanner sc){
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

        for (Commodity commodity : shangpinList) {
            if (goods.equals(commodity.getShangpin())) {
                System.out.println("请输入要添加的商品数量：");
                i = 0;
                int number = sc.nextInt();

                if (number <= commodity.getNumber()) {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setName(commodity.getShangpin());
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

    public void clearCart() {
        shoppingCartsList.clear();
    }

    // 存储购物车商品数据的文件路径
    private static final String SHOPPING_CART_FILE_PATH = "src/main/java/org/example/shopping_cart.txt";

    // 将购物车商品列表保存到文件的方法
    public static void saveShoppingCartToFile() {
        try (FileWriter fileWriter = new FileWriter(SHOPPING_CART_FILE_PATH)) {
            for (ShoppingCart cart : shoppingCartsList) {
                fileWriter.write(cart.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中加载购物车商品列表的方法
    public static void loadShoppingCartFromFile() {
        shoppingCartsList.clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SHOPPING_CART_FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] cartData = line.split(",");
                if (cartData.length == 2) {
                    ShoppingCart cart = new ShoppingCart(cartData[0], Integer.parseInt(cartData[1]));
                    shoppingCartsList.add(cart);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // 将购物车商品对象转换为逗号分隔的字符串
        return name + "," + number;
    }

    public static void createEmptyFileIfNotExists() {
        File file = new File(SHOPPING_CART_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







}
