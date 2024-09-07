package org.example;

import java.util.Iterator;

import java.util.Scanner;

import static org.example.User.userList;

public class Administrator {
    private String account = "admin";
    private String password = "ynuinfo#777";

    public Administrator() {
    }

    public Administrator(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //修改自身密码
    public void changePassword(String account,String password)
    {

    }

    public boolean adminLogin(Scanner sc,Administrator admin)
    {
        while (true){
            System.out.println("请输入管理员账号");
            String account = sc.next();
            System.out.println("请输入管理员密码");
            String password = sc.next();
            if(account.equals(admin.getAccount())){
                if(password.equals(admin.getPassword())){
                    System.out.println("登录成功");
                    return true;
                }
                else{
                    System.out.println("您输入的密码有问题");
                }
            }
            else {
                System.out.println("您输入的用户名不存在");
            }
        }
    }



    public void changeAdminPassword(Scanner sc,Administrator admin)
    {
        System.out.println("请输入修改后的密码");
        admin.password = sc.next();
    }

    public void moveUser(Scanner sc){
        System.out.println("请输入想要删除的用户的账号ID");
        String id = sc.next();
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (id.equals(user.getID())) {
                System.out.println("真的要删除"+id+"这个用户吗,如果真的要删除，请输入1");
                int judgment = Integer.parseInt(sc.next());
                switch (judgment){
                    case 1:
                        iterator.remove();
                        System.out.println("删除成功");
                        break;
                    default:
                        System.out.println("你的输入不符合规定");
                }

            }
        }
    }

    public void searchUser(Scanner sc){
        System.out.println("请输入想要查询的用户的账号ID");
        String id = sc.next();
        for (User user : userList) {
            if (id.equals(user.getID())) {
                System.out.println("账号: " + user.getID());
                System.out.println("用户名: " + user.getName());
                System.out.println("等级: " + user.getGrade());
                System.out.println("总消费: " + user.getMoney());
                System.out.println("手机号: " + user.getPhone());
                System.out.println("邮箱: " + user.getEmail());
                System.out.println("---------------------------");
            }
        }
    }
}


