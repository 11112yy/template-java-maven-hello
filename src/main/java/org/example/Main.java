package org.example;
public class Main {
    public static void main(String args[]) {
        FileStorage filestorage = new FileStorage(); // 创建file对象并读取数据
        Menu menu = new Menu(filestorage.getList1(), filestorage.getList2(), filestorage);
        menu.showLoginMenu();
    }
}