package org.example;
import java.io.Serializable;

public class ShopCar implements Serializable {
    String name;//名字
    int amount;//数量
    double money;//价格
    String time;//购买时间
    int index;//商品位置
    public ShopCar(String s, int a, double m,int i) {
        name = s;
        amount = a;
        money = m;
        index=i;
    }

    public void setTime(String t) {
        time = t;
    }

    public void getHistory() {
        System.out.println(name + " " + time);
    }
}
