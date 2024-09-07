package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        User.createEmptyFileIfNotExists();
        ShoppingHistory.createEmptyFileIfNotExists();
        ShoppingCart.createEmptyFileIfNotExists();
        Commodity.createEmptyFileIfNotExists();
        Administrator.createEmptyFileIfNotExists();


        Scanner sc = new Scanner(System.in);
        User.loadUsersFromFile();
        ShoppingHistory.loadShoppingHistoryFromFile();
        ShoppingCart.loadShoppingCartFromFile();
        Commodity.loadGoodsFromFile();
    
        Administrator admin= Administrator.loadAdminFromFile();
        

        Menu menu = new Menu();
        //进入一级界面
        System.out.println("----------------欢迎进1入购物系统----------------");
        menu.firstInterface(sc);


    }
}
