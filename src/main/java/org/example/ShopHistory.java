package org.example;
import java.io.Serializable;

public class ShopHistory implements Serializable{
    String name;//名字
    String time;//购买时间
    int amount;
    public ShopHistory(String n,int a) {
        name=n;
        amount=a;
    }
    public void setTime(String t) {
        time = t;
    }
    public void getHistory() {
        System.out.println("商品名称："+name + " 购买数量:"+amount+" 购买时间:" + time);
    }
}
