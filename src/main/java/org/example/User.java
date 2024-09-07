package org.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class User {

    private String ID;
    private String name;
    private String password;
    private String grade;
    private String time;
    private double money;
    private String phone;
    private String email;

    public static List<User> userList = new ArrayList<User>();

    public User() {
    }

    public User(String ID, String name, String password, String grade, String time, double money, String phone, String email) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.grade = grade;
        this.time = time;
        this.money = money;
        this.phone = phone;
        this.email = email;
    }
    

    public void updateTotalMoney(double totalMoney) {
        this.money = totalMoney;
        this.grade = putGrade(totalMoney);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void enroll(Scanner sc) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        String id;
        while (true) {
            System.out.println("请您输入长度大于5的账号");
            id = sc.next();
            int i=1;
            for (User user : userList) {
                if (id.length() >= 5) {
                    if(user.getID().equals(id)){
                        System.out.println("用户名重复");
                        i=0;
                    }
                }else {
                    System.out.println("账号错误");
                }
            }
            if(i==1){
                break;
            }

        }
        while (true) {
            System.out.println("请您输入长度大于8个字符，必须是大小写字母、数字和标点符号的组合的密码");
            String password = sc.next();
            if (isValidPassword(password)) {
                // 校验密码通过，创建新用户对象并添加到列表
                System.out.println("请输入用户名");
                String name = sc.next();
                System.out.println("请输入手机号");
                String phone = sc.next();
                System.out.println("请输入邮箱");
                String email = sc.next();
                
                ShoppingHistory shoppingHistory = new ShoppingHistory();
                User user = new User(id, name, password, "", time, 0, phone, email);
                userList.add(user);
                return; // 结束方法
            } else {
                System.out.println("密码不符合要求，请重新输入");
            }
        }
    }

    public User userLogin(Scanner sc)
    {
        while (true){
            System.out.println("请输入用户账号");
            String ID = sc.next();
            System.out.println("请输入用户密码");
            String password = sc.next();
            int i=1;
            for (User user : userList) {
                if(ID.equals(user.getID())){
                    i=0;
                    if(password.equals(user.getPassword())){
                        System.out.println("登录成功");
                        return user;
                    }
                    else{
                        System.out.println("您输入的密码有误");
                    }
                }
            }
            if(i==1){
              System.out.println("用户名不存在");
            }

        }
    }

    public void changeUserPassword(Scanner sc,User use)
    {
        String id = use.ID;
        while (true) {
            System.out.println("请输入修改后的密码，密码长度大于8个字符，并且必须包含大小写字母、数字和标点符号的组合");
            String password = sc.next();
            for (User user : userList) {
                if (user.getID().equals(id)&&isValidPassword(password)) {
                    user.setPassword(password);
                    System.out.println("密码修改成功！");
                    return; // 找到用户并修改密码后，结束方法
                }
            }
            System.out.println("找不到指定的用户,或密码不符合要求");
        }
    }

    // 校验密码是否符合要求
    private boolean isValidPassword(String password) {
        // 密码长度大于8个字符，并且必须包含大小写字母、数字和标点符号的组合
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}]).{8,}$";
        return password.matches(pattern);
    }

    public String putGrade(double money){
        String grade = "普通客户";
        if(money<=5000&&money>1000)
            grade="vip客户";
        else if(money>5000)
            grade="svip客户";
        return grade;
    }

    public static void showUser() {
        for (User user : userList) {
            System.out.println("账号: " + user.getID());
            System.out.println("用户名: " + user.getName());
            System.out.println("等级: " + user.getGrade());
            System.out.println("总消费: " + user.getMoney());
            System.out.println("手机号: " + user.getPhone());
            System.out.println("邮箱: " + user.getEmail());
            System.out.println("注册时间: " + user.getTime());
            System.out.println("---------------------------");
        }
    }

    // 存储用户数据的文件路径
    private static final String USER_FILE_PATH = "src/main/java/org/example/user_data.txt";

    // 将用户列表保存到文件的方法
    public static void saveUsersToFile() {
        try (FileWriter fileWriter = new FileWriter(USER_FILE_PATH)) {
            for (User user : userList) {
                fileWriter.write(user.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中加载用户列表的方法
    public static void loadUsersFromFile() {
        userList.clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                User user = User.parseUserFromString(line);
                if (user != null) {
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        // 将用户对象转换为逗号分隔的字符串
        return ID + "," + name + "," + password + "," + grade + "," + time + "," + money + "," + phone + "," + email;
    }

    public static User parseUserFromString(String userDataString) {
        String[] userData = userDataString.split(",");
        if (userData.length == 8) {
            return new User(userData[0], userData[1], userData[2], userData[3], userData[4], Double.parseDouble(userData[5]), userData[6], userData[7]);
        }
        return null;
    }

    public static void createEmptyFileIfNotExists() {
        try {
            File file = new File(USER_FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}

