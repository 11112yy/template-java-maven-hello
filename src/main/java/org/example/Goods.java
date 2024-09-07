package org.example;
import java.io.Serializable;
public class Goods implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 3602333742325018621L;
    long itemCode;     		      //商品编号
    String name;        		      //商品名称
    String factory;      		  //生产厂家
    String manufactureDate;        //生产日期
    double purchasingPrice;		  //采购价
    double retailPrice;   	      //零售价
    int amount;				      //商品数量

    public Goods() {

    }

    public Goods(long i, String n, String f,String m,String t,double p,double r,int a ){  //显示客户所有信息
        this. itemCode=i;
        this.name=n;
        this. factory=f;
        this. manufactureDate=m;
        this. purchasingPrice=p;
        this. retailPrice=r;
        this. amount=a;
    }


    public void getAllMassage() {
        System.out.println("商品编号:"+itemCode);
        System.out.println("商品名称:"+name);
        System.out.println("生产厂家:"+factory);
        System.out.println("生产日期:"+manufactureDate);
        System.out.println("进货价:"+purchasingPrice);
        System.out.println("零售价:"+retailPrice);
        System.out.println("数量:"+amount);
        System.out.println(" ");
    }

}

