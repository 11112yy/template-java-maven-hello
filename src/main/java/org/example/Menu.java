package org.example;


import java.util.Scanner;

public class Menu {

    Administrator admin = new Administrator();
    User user = new User();
    public static Commodity commodity = new Commodity();
    public static ShoppingCart shoppingCart = new ShoppingCart();
    public static ShoppingHistory shoppingHistory = new ShoppingHistory();
    Payment payment = new Payment();
    private User currentUser; // 用于保存当前登录的用户对象



    public void firstInterface(Scanner sc)
    {
        while(true){
            System.out.println("请您填写相应数字完成想要的操作");
            System.out.println("1.管理员登录");
            System.out.println("2.用户登录");
            System.out.println("3.用户注册");
            System.out.println("4.退出程序");

            int judgment = sc.nextInt();

            switch (judgment){
                case 1 :
                    //管理员登录
                    if(admin.adminLogin(sc)){
                        //登录成功后进入二级界面管理员界面
                        while (adminInterface(sc)){

                        }

                    }
                    break;
                case 2 :
                    //用户登录
                    currentUser = user.userLogin(sc);
                    if(currentUser != null){
                        //登录成功后进入二级界面用户界面
                        while (userInterface(sc)){

                        }
                    }
                    break;
                case 3 :
                    //用户注册
                    user.enroll(sc);
                    //注册成功之后重新执行一级界面
                    break;
            
                case 4 :
                    //退出程序
                    User.saveUsersToFile();
                    ShoppingHistory.saveShoppingHistoryToFile();
                    ShoppingCart.saveShoppingCartToFile();
                    Commodity.saveGoodsToFile();
                    Administrator.saveAdminToFile(admin);
                    System.exit(0);
                    break;
                default:
                    System.out.println("您的输入有误");
            }
        }
    }

    public boolean adminInterface(Scanner sc)
    {
        System.out.println("---------------管理员界面----------------");
        System.out.println("请输入你想要进行的操作，填写相应数字");
        System.out.println("1.修改自身密码");
        System.out.println("2.重置指定用户密码");
        System.out.println("3.列出所有用户信息");
        System.out.println("4.删除客户信息");
        System.out.println("5.查询客户信息");
        System.out.println("6.列出所有商品信息");
        System.out.println("7.添加商品信息");
        System.out.println("8.修改商品信息");
        System.out.println("9.删除商品信息");
        System.out.println("10.查询商品信息");
        System.out.println("11.退出登录");

        int judgment = sc.nextInt();

        switch (judgment){
            case 1:
                //修改自身密码
                admin.changeAdminPassword(sc,admin);
                return false;

            case 2:
                //重置指定用户密码
                return true;

            case 3:
                //列出所有用户信息
                User.showUser();
                return true;

            case 4:
                //删除客户信息
                admin.moveUser(sc);
                return true;

            case 5:
                //查询客户信息
                admin.searchUser(sc);
                return true;

            case 6:
                //列出所有商品信息
                Commodity.showShangpin( );
                return true;

            case 7:
                //添加商品信息
                commodity.add(sc);
                return true;

            case 8:
                //修改商品信息
                commodity.changeShangpinNumber(sc);
                return true;
            case 9:
                //删除商品信息
                commodity.moveGoods(sc);
                return true;
            case 10:
                //查询商品信息
                commodity.searchshangpin(sc);
                return true;
            case 11:
                //退出登录
                return false;
            default:
                System.out.println("您的输入有误");
        }
        return false;
    }

    public boolean userInterface(Scanner sc){
        System.out.println("---------------用户界面----------------");
        System.out.println("请输入您想要进行的操作，并填写相应数字");
        System.out.println("1.修改自身密码");
        System.out.println("2.将商品添加至购物车");
        System.out.println("3.将商品从购物车中移除");
        System.out.println("4.修改购物车中商品信息");
        System.out.println("5.支付");
        System.out.println("6.查看购物历史");
        System.out.println("7.退出登录");

        int judgment = sc.nextInt();

        switch (judgment){
            case 1:
                //修改自身密码
                user.changeUserPassword(sc,currentUser);
                return false;

            case 2:
                //将商品添加至购物车
                shoppingCart.add(sc);
                return true;

            case 3:
                //将商品从购物车中移除
                shoppingCart.moveShangpin(sc);
                return true;
            case 4:
                //修改购物车中商品信息
                shoppingCart.changeGoodsNumber(sc);
                return true;
            case 5:
                //支付
                Payment.processPayment(currentUser);
                return true;
            case 6:
                //查看购物历史
                shoppingHistory.showShoppingHistory();
                return true;
            case 7:
                //退出登录
                return false;
            default:
                System.out.println("输入错误");
        }
        return false;
    }
}

