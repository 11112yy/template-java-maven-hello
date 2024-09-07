package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
      DatabaseInieializer databaseInieializer=new DatabaseInieializer();
       databaseInieializer.initializeDatabase();
        ShoppingCart.initializeDatabase();
        ShoppingHistory.initializeDatabase();
        User.initializeDatabase();
        Commodity.initializeDatabase();

        Scanner sc = new Scanner(System.in);

        Connection connection1 = DriverManager.getConnection("jdbc:sqlite:user.db");
        Connection connection2 = DriverManager.getConnection("jdbc:sqlite:shoppingHistory.db");
        Connection connection3 = DriverManager.getConnection("jdbc:sqlite:shoppingCart.db");
        Connection connection4 = DriverManager.getConnection("jdbc:sqlite:goods.db");
      // Connection connection5 = DriverManager.getConnection("jdbc:sqlite:user.db");

        User.loadUsersFromDatabase();
        ShoppingHistory.loadShoppingHistoryFromDatabase( );
        ShoppingCart.loadShoppingCartFromDatabase();
        Commodity.loadGoodsFromDatabase();

        Menu menu = new Menu();
        //进入一级界面
        System.out.println("----------------欢迎进入购物系统----------------");
        menu.firstInterface(sc);

        User.saveUsersToDatabase();
        ShoppingHistory.saveShoppingHistoryToDatabase();
        ShoppingCart.saveShoppingCartToDatabase();
        Commodity.saveGoodsToDatabase();

        connection1.close();
        connection2.close();
        connection3.close();
        connection4.close();

    }
}
