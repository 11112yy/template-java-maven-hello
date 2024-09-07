package org.example;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    String name; // 用户名
    String code; // 用户密码
    long ID; // 用户ID
    String grade; // 用户级别
    String registerTime; // 用户注册时间
    int totalMoney; // 用户累计消费金额
    String phoneNumber; // 用户手机号
    String E_mail; // 用户邮箱
    List<ShopCar> shoppingCar = new ArrayList<ShopCar>(); // 购物车
    List<ShopHistory> shopHistory=new ArrayList<ShopHistory>();//购物历史

    public void getAllMassage() {
        System.out.println("用户名:" + name);
        System.out.println("用户ID:" + ID);
        System.out.println("用户级别:" + grade);
        System.out.println("用户注册时间:" + registerTime);
        System.out.println("用户累计消费金额:" + totalMoney);
        System.out.println("用户手机号:" + phoneNumber);
        System.out.println("用户邮箱:" + E_mail);
        System.out.println(" ");
    }

}
