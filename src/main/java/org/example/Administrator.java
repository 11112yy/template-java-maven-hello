package org.example;

import java.io.*;
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

    public boolean adminLogin(Scanner sc) {
        // 从文件中加载管理员数据
        Administrator admin = loadAdminFromFile();

        if (admin == null) {
            System.out.println("管理员账号不存在，请联系系统管理员。");
            return false;
        }

        while (true) {
            System.out.println("请输入管理员账号");
            String inputAccount = sc.next();
            System.out.println("请输入管理员密码");
            String inputPassword = sc.next();

            if (inputAccount.equals(admin.getAccount()) && inputPassword.equals(admin.getPassword())) {
                System.out.println("登录成功");
                return true;
            } else {
                System.out.println("用户名或密码错误，请重新输入。");
            }
        }
    }



    public void changeAdminPassword(Scanner sc, Administrator admin) {
        System.out.println("请输入修改后的密码");
        String newPassword = sc.next();
        admin.setPassword(newPassword); // 更新当前管理员对象的密码
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

    // 存储管理员数据的文件路径
    private static final String ADMIN_FILE_PATH = "src/main/java/org/example/admin_data.txt";

    // 将管理员数据保存到文件的方法
    public static void saveAdminToFile(Administrator admin) {
        try (FileWriter fileWriter = new FileWriter(ADMIN_FILE_PATH)) {
            fileWriter.write(admin.toString() + "\n");
            fileWriter.flush(); // 确保数据被写入文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 从文件中加载管理员数据的方法
    public static Administrator loadAdminFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ADMIN_FILE_PATH))) {
            String line = bufferedReader.readLine();
            if (line != null) {
                String[] adminData = line.split(",");
                if (adminData.length == 2) {
                    return new Administrator(adminData[0], adminData[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {
        // 将管理员对象转换为逗号分隔的字符串
        return account + "," + password;
    }

    public static void createEmptyFileIfNotExists() {
        File file = new File(ADMIN_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}


