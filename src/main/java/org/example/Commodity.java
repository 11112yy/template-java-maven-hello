package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Commodity {
    private String numbering;
    private String shangpin;
    private String factory;
    private String time;
    double purchasePrice;
    double sellingPrice;
    int number;
    static List<Commodity> shangpinList = new ArrayList<>();

    public Commodity() {;
    }

    public Commodity(String numbering, String shangpin, String factory, String time,  double purchasePrice, double sellingPrice, int number) {
        this.numbering = numbering;
        this.shangpin = shangpin;
        this.factory = factory;
        this.time = time;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.number = number;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public String getShangpin() {
        return shangpin;
    }

    public void setShangpin(String shangpin) {
        this.shangpin = shangpin;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void add(Scanner sc) {
        System.out.println("请您输入要购买的商品名称");
        String  shangpin = sc.next();
        System.out.println("请您输入商品编号");
        String numbering = sc.next();
        System.out.println("请您输入厂家");
        String factory= sc.next();
        System.out.println("请您输入生产日期");
        String time = sc.next();
        System.out.println("请您输入进价");
        Double purchasePrice = sc.nextDouble();
        System.out.println("请您输入零售价");
        Double sellingPrice = sc.nextDouble();
        System.out.println("请您输入购买数量");
        int number = sc.nextInt();

        //商品编号、商品名称、生产厂家、生产日期、进货价、零售价格、数量。
        Commodity commodity = new Commodity(numbering, shangpin, factory, time, purchasePrice,sellingPrice ,number);
        shangpinList.add(commodity);
    }

    public static void showShangpin() {
        for (Commodity commodity : shangpinList) {
            System.out.println("商品名称"+commodity.getShangpin());
            System.out.println("商品编号: " + commodity.getNumbering());
            System.out.println("厂家: " + commodity.getFactory());
            System.out.println("生产日期: " + commodity.getTime());
            System.out.println("进价: " + commodity.getPurchasePrice());
            System.out.println("零售价: " + commodity.getSellingPrice());
            System.out.println("购买数量: " + commodity.getNumber());
            System.out.println("---------------------------");
        }
    }

    public void changeShangpinNumber(Scanner sc)
    {
        System.out.println("请您输入想要修改的商品名称");
        String name = sc.next();
        while (true) {
            System.out.println("请您输入修改后的数量");
            int number = sc.nextInt();
            for (Commodity commodity : shangpinList) {
                if (commodity.getShangpin().equals(name)) {
                    commodity.setNumber(number);
                    System.out.println("商品以修改完毕！");
                    return;
                }
            }
            System.out.println("找不到您指定的商品请确认好商品");
        }
    }

    public void moveGoods(Scanner sc){
        System.out.println("请您输入想要删除的商品名称");
        String name = sc.next();
        Iterator<Commodity> iterator = shangpinList.iterator();
        while (iterator.hasNext()) {
            Commodity commodity = iterator.next();
            if (name.equals(commodity.getShangpin())) {
                iterator.remove();
                System.out.println("删除成功");
                break;
            }else {
                System.out.println("查找失败");
            }
        }
    }

    public void searchshangpin(Scanner sc){
        System.out.println("请您输入想要查询的商品的名称");
        String name = sc.next();
        for (Commodity commodity : shangpinList) {
            if (name.equals(commodity.getShangpin())) {
                System.out.println("商品名称"+commodity.getShangpin());
                System.out.println("商品编号: " + commodity.getNumbering());
                System.out.println("厂家: " + commodity.getFactory());
                System.out.println("生产日期: " + commodity.getTime());
                System.out.println("进价: " + commodity.getPurchasePrice());
                System.out.println("零售价: " + commodity.getSellingPrice());
                System.out.println("商品数量: " + commodity.getNumber());
                System.out.println("---------------------------");
            }else {
                System.out.println("查找失败");
            }
        }
    }

    // 存储商品数据的文件路径
    private static final String SHANGPIN_FILE_PATH = "src/main/java/org/example/goods_data.txt";

    // 将商品列表保存到文件的方法
    public static void saveGoodsToFile() {
        try (FileWriter fileWriter = new FileWriter(SHANGPIN_FILE_PATH)) {
            for (Commodity commodity :shangpinList) {
                fileWriter.write(commodity.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中加载商品列表的方法
    public static void loadGoodsFromFile() {
        shangpinList.clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SHANGPIN_FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] commodityData = line.split(",");
                if (commodityData.length == 8) {
                    Commodity commodity = new Commodity(
                            commodityData[0], commodityData[1], commodityData[2], commodityData[3],
                             Double.parseDouble(commodityData[4]),
                            Double.parseDouble(commodityData[5]), Integer.parseInt(commodityData[6])
                    );
                    shangpinList.add(commodity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // 将商品对象转换为逗号分隔的字符串
        return numbering + "," + shangpin + "," + factory + "," + time + ","  + "," + purchasePrice + "," + sellingPrice + "," + number;
    }

    public static void createEmptyFileIfNotExists() {
        File file = new File(SHANGPIN_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}




