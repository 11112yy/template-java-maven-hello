package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Payment {
    private double totalAmount;

    public Payment(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Payment() {

    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public static void processPayment(User user) {
        // 获取购物车中的商品列表
        List<ShoppingCart> cartItems = ShoppingCart.shoppingCartsList;

        // 获取商品列表
        List<Commodity> itemList = Commodity.goodsList;

        double total=0;

        // 更新商品列表和购物历史列表
        for (ShoppingCart cartItem : cartItems) {
            // 找到商品列表中对应的商品
            Commodity purchasedItem = null;
            for (Commodity item : itemList) {
                if (item.getGoods().equals(cartItem.getName())) {
                    purchasedItem = item;
                    break;
                }
            }

            // 商品列表中对应商品数量减少
            if (purchasedItem != null) {
                int newQuantity = purchasedItem.getNumber() - cartItem.getNumber();
                purchasedItem.setNumber(newQuantity);
            }
            


            total+=cartItem.getNumber()*purchasedItem.getSellingPrice();

            // 购物历史列表添加信息
            ShoppingHistory.addShoppingRecord(cartItem.getName(),cartItem.getNumber(),cartItem.getNumber()*purchasedItem.getSellingPrice(),getCurrentTime());
            user.updateTotalMoney(total);
        }

        // 清空购物车
        cartItems.clear();
        System.out.println("支付成功");

    }






    private static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentTime = formatter.format(date);
        return currentTime;
    }
}
