package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseInieializer {
private static final String DB_URL="jdbc:sqlite:users.db";
public void initializeDatabase() {
 try (Connection connection= DriverManager.getConnection(DB_URL);
Statement satement=connection.createStatement()){
 String creatTableQyery="CREATE TABLE IF NOT EXISTS USERS (id INTEGER PRIMARY KEY AUTOINCEREMENT,username TEXT,password TEXT)";
 satement.executeUpdate(creatTableQyery);
 System.out.println("Database initialized successfully!");
}catch(SQLException e){
 System.out.println("Faile to initialize database:"+e.getMessage());
}
 }
 public static String getDbUrl() {
 return DB_URL;
}
}