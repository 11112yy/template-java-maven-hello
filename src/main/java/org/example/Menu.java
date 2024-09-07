package org.example;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Menu<E> {
    List<E> list1;
    List<Goods> list2;
    Customer cus;
    Administrator admin;
    FileStorage filestorage;
    Scanner scanner = new Scanner(System.in);

    public Menu(List<E> list1, List<Goods> list2, FileStorage filestorage) {
        this.list1 = list1;
        this.list2 = list2;
        this.filestorage = filestorage;
    }

    public void showLoginMenu() {
        do {
            System.out.println("欢迎进入购物管理系统");
            System.out.println("1.用户登录");
            System.out.println("2.管理员登陆");
            System.out.println("3.用户注册");
            System.out.println("4.退出");
            System.out.print("请输入数字:");
            String s = scanner.next();

            if (s.equals("1")) {
                boolean flag = true;
                for (int i = 0; i < 5; i++) {
                    System.out.print("请输入用户名");
                    String s1 = scanner.next();
                    System.out.print("请输入密码");
                    String s2 = scanner.next();
                    for (int j = 1; j < list1.size(); j++) {
                        Customer user = (Customer) list1.get(j);
                        if (s1.equals(user.name) && MD5.match(s2).equals(user.code)) {
                            // 用户登录成功，显示购物二级菜单！！！
                            cus = user;
                            flag = false;
                            cusMenu();
                            break;
                        }
                    }
                    if (!flag)
                        break;
                    if (flag) {
                        System.out.println("用户名和密码不匹配");
                        System.out.println("按'1'继续，按其他任意键返回:");
                        String s3 = scanner.next();
                        if (s3.equals("1"))
                            continue;
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    System.out.println("用户不存在");
                    filestorage.close();
                    System.exit(0);
                }

            }

            else if (s.equals("2")) {
                admin = (Administrator) list1.get(0);
                boolean flag = true;
                for (int i = 0; i < 5; i++) { // 五次尝试机会
                    System.out.print("请输入用户名：");
                    String s1 = scanner.next();
                    System.out.print("请输入管理员密码：");
                    String s2 = scanner.next();
                    if (s1.equals(admin.name) && MD5.match(s2).equals(admin.code)) {
                        flag = false;
                        adminCus();
                        break;
                    } else {
                        System.out.println("账户或密码错误");
                        System.out.println("按'1'继续，按其他任意键返回");
                        String s3 = scanner.next();
                        if (s3.equals("1"))
                            continue;
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    System.out.println("抱歉，您不能进入系统");
                    filestorage.close();
                    System.exit(0);
                }
            }

            else if (s.equals("3")) {
                do {
                    boolean flag = true;
                    System.out.println("请输入用户名,按'n'返回");
                    String s1 = scanner.next();
                    if (s1.equals("n")) {
                        break;
                    }
                    for (int n = 1; n < list1.size(); n++) {
                        Customer user = (Customer) list1.get(n);
                        if (s1.equals(user.name)) {
                            System.out.println("用户名已存在");
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        System.out.println("请输入密码,密码必须是大小写字母、数字和标点符号的组合：");
                        String s2 = scanner.next();
                        if (s2.length() > 8 && s2.matches(".*\\d+.*") && s2.matches(".*[A-Z]+.*")
                                && s2.matches(".*[a-z]+.*") && s2.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*")) {
                            Customer cu = new Customer();
                            Date nowTime = new Date();
                            Calendar calendar = Calendar.getInstance();
                            cu.name = s1; // 用户名
                            cu.code = MD5.match(s2); // 密码
                            cu.registerTime = String.format("%tF", nowTime); // 注册时间
                            cu.ID = calendar.getTimeInMillis(); // 用注册时间与1970年1月1日8点的插值表示用户ID
                            System.out.println("请输入您的电话号码");
                            cu.phoneNumber = scanner.next();
                            System.out.println("请输入您的邮箱");
                            cu.E_mail = scanner.next();
                            cus = cu;
                            list1.add((E) cu);
                            System.out.println("注册成功");
                            cusMenu();
                            break;
                        } else
                            System.out.println("密码不符合要求");
                    }

                } while (true);
            }

            else if (s.equals("4")) { // 用户选择"退出"
                System.out.println("谢谢您的使用！");
                filestorage.close(); // 上传数据
                System.exit(0);
            }

            else // 一级菜单输入错误，需要重新选择
                System.out.print("输入有误！请重新输入数字: ");

        } while (true);

    }

    // 顾客菜单
    public void cusMenu() {
        do {
            System.out.println("欢迎使用购物管理系统");
            System.out.println("1.修改密码");
            System.out.println("2.购物结算");
            // 用户选择服务项目
            System.out.print("请输入数字,按其他任意键返回");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();

            // 用户选择"修改密码"
            if (s.equals("1")) {
                cusCode();
            }

            // 用户选择"购物结算"
            else if (s.equals("2")) {
                // 定义购物结算类的对象，并处理整个购物结算的流程
                shopMenu();

            }

            // 用户选择"退出登录"
            else {
                break;
            }
        } while (true);
    }

    // 密码修改菜单
    public void cusCode() {
        System.out.println("购物管理系统>顾客>修改密码");
        do {
            System.out.println("请输入新的密码，密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合,输入'n'则返回");
            String str = scanner.next();
            if (str.length() > 8 && str.matches(".*\\d+.*") && str.matches(".*[A-Z]+.*") && str.matches(".*[a-z]+.*")
                    && str.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*")) {
                cus.code = str;
                System.out.println("密码修改成功");
                break;
            } else if (str.equals("n"))
                break;
            else
                System.out.println("您输入的密码格式错误");
        } while (true);
    }

    // 顾客购物管理菜单
    public void shopMenu() {
        do {
            System.out.println("购物管理系统 >购物管理");
            System.out.println("1.购物车管理");
            System.out.println("2.结账");
            System.out.println("3.查看购物历史");
            System.out.print("请选择,输入数字或按其他任意键返回上一级菜单:");
            String s = scanner.next();
            if (s.equals("1")) {
                do {
                    System.out.println("为您找到以下商品");
                    for (int i = 0; i < list2.size(); i++) {
                        Goods goods = (Goods) list2.get(i);
                        goods.getAllMassage();
                    }
                    System.out.println("请输入要添加商品的名称,清空购物车请按'1',返回请按'n'");
                    String s1 = scanner.next();
                    if (s1.equals("1")) {
                        cus.shoppingCar.clear();
                        System.out.println("购物车已清空");
                        continue;
                    }
                    if (s1.equals("n")) {
                        break;
                    }
                    int amount;
                    do {
                        System.out.print("您要将该商品在购物车中设置为多少");
                        String a = scanner.next();
                        if (a.matches("[\\d]+")) {
                            amount = Integer.parseInt(a);
                            break;
                        } else
                            System.out.println("您输入的不是纯数字，请重试");
                    } while (true);
                    if (amount <= 0) {
                        System.out.println("您将清空购物车中的该商品，请考虑清楚");
                        System.out.println("输入'n':撤销");
                        String s3 = scanner.next();
                        if (s3.equals("n")) {
                            continue;
                        }
                    }
                    for (int i = 0; i < list2.size(); i++) {
                        Goods goods = (Goods) list2.get(i);
                        if (s1.equals(goods.name) && amount <= goods.amount) {
                            ShopCar shopcar = new ShopCar(s1, amount, goods.retailPrice, i);
                            cus.shoppingCar.add(shopcar);
                            System.out.println("商品添加成功");
                            break;
                        }

                        else
                            System.out.println("商品不存在或库存不足");
                    }
                } while (true);

            }

            else if (s.equals("2")) {
                System.out.println("按'1'确认支付，按其他任意键取消");
                String s4 = scanner.next();
                if (s4.equals("1")) {
                    int total = 0;
                    for (int i = 0; i < cus.shoppingCar.size(); i++) {
                        ShopCar shopcar = cus.shoppingCar.get(i);
                        Goods goods = (Goods) list2.get(shopcar.index);
                        goods.amount -= shopcar.amount; // 扣除仓库中对应数量的商品
                        total += shopcar.money * shopcar.amount;
                        ShopHistory shopHistory = new ShopHistory(shopcar.name, shopcar.amount);
                        shopHistory.setTime(String.format("%tF", new Date()));
                        cus.shopHistory.add(shopHistory);

                    }
                    cus.totalMoney += total; // 计算累积消费金额
                    if (cus.totalMoney > 100)
                        cus.grade = "铜牌客户";
                    if (cus.totalMoney > 500)
                        cus.grade = "银牌客户";
                    if (cus.totalMoney > 1000)
                        cus.grade = "金牌客户";
                    cus.shoppingCar.clear(); // 结算后清空购物车
                    System.out.println("结算成功，本次消费"+total+"元");
                } else {
                    continue;
                }
            } else if (s.equals("3")) {
                for (int i = 0; i < cus.shopHistory.size(); i++) {
                    ShopHistory shopHistory = cus.shopHistory.get(i);
                    shopHistory.getHistory();
                }

            } else {
                break;
            }
        } while (true);

    }

    // 管理员菜单
    public void adminCus() {

        do {
            System.out.println("购物管理系统 >管理员");
            System.out.println("1.密码管理");
            System.out.println("2.用户管理");
            System.out.println("3.商品管理");
            System.out.print("请选择,输入数字或按其他任意键返回上一级菜单:");
            String s = scanner.next();
            if (s.equals("1")) {
                adminCode();
            } else if (s.equals("2")) {
                userAdmin();
            } else if (s.equals("3")) {
                goodsAdmin();
            } else {
                break;
            }

        } while (true);
    }

    // 管理员密码
    public void adminCode() {

        do {
            System.out.println("购物管理系统 >管理员>密码管理");
            System.out.println("1.修改管理员密码");
            System.out.println("2.重置用户密码");
            System.out.print("请选择,输入数字或按其他任意键返回上一级菜单:");
            String s = scanner.next();
            if (s.equals("1")) {
                System.out.println("请输入新密码");
                String s1 = scanner.next();
                admin.code = MD5.match(s1);
                System.out.println("修改成功");
            } else if (s.equals("2")) {
                do {
                    System.out.println("请输入您要重置密码账户的用户名,按'n'返回上一级菜单");
                    String s1 = scanner.next();
                    if (s1.equals("n")) {
                        break;
                    }
                    boolean flag = true;
                    for (int n = 1; n < list1.size(); n++) {
                        Customer user = (Customer) list1.get(n);
                        if (s1.equals(user.name)) {
                            user.code = MD5.match("XXXY+yyds2020");
                            System.out.println("密码已重置为XXXY+yyds2020");
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        System.out.println("账户不存在，请重试");
                } while (true);
            } else {
                break;
            }
        } while (true);
    }

    // 客户查询
    public void userAdmin() {

        do {
            System.out.println("购物管理系统 >管理员>客户查询");
            System.out.println("1.查看所有客户信息");
            System.out.println("2.客户查询");
            System.out.print("请选择,输入数字或按其他任意键返回上一级菜单:");
            String s = scanner.next();
            if (s.equals("1")) {
                for (int n = 1; n < list1.size(); n++) {
                    Customer user = (Customer) list1.get(n);
                    user.getAllMassage();
                }
            } else if (s.equals("2")) {
                do {
                    System.out.println("请输入您要查找的客户ID或者用户名，按'n'返回");
                    String s1 = scanner.next();
                    if (s1.equals("n")) {
                        break;
                    }
                    for (int i = 1; i < list1.size(); i++) {
                        Customer user = (Customer) list1.get(i);
                        if (s1.equals(user.name) || s1.equals(" " + user.ID)) {
                            user.getAllMassage();
                            System.out.println("按'1'则删除该用户信息，是否确认，否则按其他任意键返回");
                            String s2 = scanner.next();
                            if (s2.equals("1")) {
                                list1.remove(i);
                            } else {
                                break;
                            }
                        }
                    }
                    System.out.println("用户不存在，请重试");
                } while (true);
            } else {
                break;
            }

        } while (true);
    }

    // 商品管理
    public void goodsAdmin() {

        do {
            System.out.println("购物管理系统 >管理员>商品管理");
            System.out.println("1.列出所有商品的信息");
            System.out.println("2.添加商品的信息");
            System.out.println("3.查询商品的信息");
            System.out.print("请选择,输入数字,按其他任意键返回");
            String s = scanner.next();
            if (s.equals("1")) {
                System.out.println(list2.size());
                for (int i = 0; i < list2.size(); i++) {
                    Goods goods = (Goods) list2.get(i);
                    goods.getAllMassage();
                }
            } else if (s.equals("2")) {
                long i;
                do {
                    System.out.println("请输入商品编号");
                    String i0 = scanner.next();
                    if (i0.matches("[\\d]+")) {
                        i = Long.parseLong(i0);
                        break;
                    } else
                        System.out.println("您输入的不是纯数字，请重试");
                } while (true);
                System.out.println("请输入商品名称");
                String n = scanner.next();
                System.out.println("请输入生产厂家");
                String f = scanner.next();
                System.out.println("请输入生产日期");
                String m = scanner.next();
                System.out.println("请输入型号");
                String t = scanner.next();
                double p;
                do {
                    System.out.println("请输入进货价");
                    String p0 = scanner.next();
                    if (p0.matches("[\\d]+")) {
                        p = Double.parseDouble(p0);
                        break;
                    } else
                        System.out.println("您输入的不是纯数字，请重试");
                } while (true);
                double r;
                do {
                    System.out.println("请输入零售价");
                    String r0 = scanner.next();
                    if (r0.matches("[\\d]+")) {
                        r = Double.parseDouble(r0);
                        break;
                    } else
                        System.out.println("您输入的不是纯数字，请重试");
                } while (true);
                int a;
                do {
                    System.out.println("请输入商品数量");
                    String a0 = scanner.next();
                    if (a0.matches("[\\d]+")) {
                        a = Integer.parseInt(a0);
                        break;
                    } else
                        System.out.println("您输入的不是纯数字，请重试");
                } while (true);
                Goods goods = new Goods(i, n, f, m, t, p, r, a);
                list2.add(goods);
            } else if (s.equals("3")) {
                System.out.println("请输入商品名称，按'n'跳过");
                String n = scanner.next();
                System.out.println("请输入生产厂家，按'n'跳过");
                String f = scanner.next();
                int r1;
                do {
                    System.out.println("请输入零售价下限，仅接受整数，按'0'跳过");
                    String r0 = scanner.next();
                    if (r0.matches("[\\d]+")) {
                        r1 = Integer.parseInt(r0);
                        break;
                    } else
                        System.out.println("您输入的不是纯数字，仅接受整数，请重试");
                } while (true);
                int r2;
                do {
                    System.out.println("请输入零售价上限，按'0'跳过");
                    String r0 = scanner.next();
                    if (r0.matches("[\\d]+")) {
                        r2 = Integer.parseInt(r0);
                        break;
                    } else
                        System.out.println("您输入的不是纯数字或不是整数，请重试");
                } while (true);
                r2 = (r2 == 0) ? Integer.MAX_VALUE : r2; // 如果用户没有设置上限则把上限设置成一个较大数
                boolean flag = true;
                for (int i = 0; i < list2.size(); i++) {
                    int j = 0;
                    Goods goods = (Goods) list2.get(i);
                    if (n.equals(goods.name) || n.equals("n"))
                        j++;
                    if (f.equals(goods.factory) || f.equals("n"))
                        j++;
                    if (r1 <= goods.retailPrice && r2 >= goods.retailPrice)
                        j++;
                    if (!n.equals(f))
                        j++;
                    if (j == 4) {
                        goods.getAllMassage();
                        flag = false;
                        do {
                            System.out.println("想修改商品编号请按1");
                            System.out.println("想修改商品名称请按2");
                            System.out.println("想修改生产厂家请按3");
                            System.out.println("想修改生产日期请按4");
                            System.out.println("想修改型号请按5");
                            System.out.println("想修改进货价请按6");
                            System.out.println("想修改零售价请按7");
                            System.out.println("想修改商品数量请按8");
                            System.out.println("删除该商品请按9");
                            System.out.println("按其他任意键返回");
                            System.out.println("请选择");
                            String s1 = scanner.next();
                            if (s1.equals("1")) {
                                long i1;
                                do {
                                    System.out.println("请输入新的商品编号");
                                    String i0 = scanner.next();
                                    if (i0.matches("[\\d]+")) {
                                        i1 = Long.parseLong(i0);
                                        break;
                                    } else
                                        System.out.println("您输入的不是纯数字，请重试");
                                } while (true);
                                goods.itemCode = i1;
                                System.out.println("修改成功");
                            } else if (s1.equals("2")) {
                                System.out.println("请输入新的商品名称");
                                goods.name = scanner.next();
                                System.out.println("修改成功");
                            } else if (s1.equals("3")) {
                                System.out.println("请输入新的生产厂家");
                                goods.factory = scanner.next();
                                System.out.println("修改成功");
                            } else if (s1.equals("4")) {
                                System.out.println("请输入新的生产日期");
                                goods.manufactureDate = scanner.next();
                                System.out.println("修改成功");
                            } else if (s1.equals("5")) {
                                System.out.println("请输入新的型号");
                                goods.type = scanner.next();
                                System.out.println("修改成功");
                            } else if (s1.equals("6")) {
                                double p;
                                do {
                                    System.out.println("请输入进货价");
                                    String p0 = scanner.next();
                                    if (p0.matches("[\\d]+")) {
                                        p = Double.parseDouble(p0);
                                        break;
                                    } else
                                        System.out.println("您输入的不是纯数字，请重试");
                                } while (true);
                                goods.purchasingPrice = p;
                                System.out.println("修改成功");
                            } else if (s1.equals("7")) {
                                double r;
                                do {
                                    System.out.println("请输入零售价");
                                    String r0 = scanner.next();
                                    if (r0.matches("[\\d]+")) {
                                        r = Double.parseDouble(r0);
                                        break;
                                    } else
                                        System.out.println("您输入的不是纯数字，请重试");
                                } while (true);
                                goods.retailPrice = r;
                                System.out.println("修改成功");
                            } else if (s1.equals("8")) {
                                int a;
                                do {
                                    System.out.println("请输入商品数量");
                                    String a0 = scanner.next();
                                    if (a0.matches("[\\d]+")) {
                                        a = Integer.parseInt(a0);
                                        break;
                                    } else
                                        System.out.println("您输入的不是纯数字，请重试");
                                } while (true);
                                goods.amount = a;
                                System.out.println("修改成功");
                            } else if (s1.equals("9")) {
                                System.out.println("请确认是否继续删除该商品信息,按1继续删除，其他任意键返回");
                                if (scanner.next().equals("1")) {
                                    list2.remove(i);
                                    System.out.println("删除成功");
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        } while (true);
                    }
                }
                if (flag) {
                    System.out.println("未找到对应商品");
                }

            } else {
                break;
            }
        } while (true);
    }

}