package org.example;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStorage<E> extends BaseFileStorage<E> {

    private final String USER_FILE_NAME = "user.xlsx";
    private final String GOODS_FILE_NAME = "goods.xlsx";

    @SuppressWarnings("unchecked")
    public FileStorage() {
        super();
    }

    @Override
    public void read() {
        readUsers();
        readGoods();
    }

    private void readUsers() {
        File file = new File(USER_FILE_NAME);
        if (!file.exists()) {
            getList1().add((E)new Administrator());
            return;
        }

        //创建工作薄对象
        try {
            XSSFWorkbook workbook=new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("sheet");
            int rowNum = sheet.getLastRowNum() + 1;
            if (rowNum <= 1) {
                return;
            }
            for (int i = 1; i < rowNum; i++) {
                XSSFRow row = sheet.getRow(i);

                if (i == 1) {
                    Administrator administrator = new Administrator();
                    administrator.name = row.getCell(0).getStringCellValue();
                    administrator.code = row.getCell(1).getStringCellValue();
                    getList1().add((E)administrator);
                    continue;
                }

                Customer customer = new Customer();
                customer.name = row.getCell(0).getStringCellValue();
                customer.code = row.getCell(1).getStringCellValue();
                customer.ID = (long) row.getCell(2).getNumericCellValue();
                customer.grade = row.getCell(3).getStringCellValue();
                customer.registerTime = row.getCell(4).getStringCellValue();
                customer.totalMoney = (int) row.getCell(5).getNumericCellValue();
                customer.phoneNumber = row.getCell(6).getStringCellValue();
                customer.E_mail = row.getCell(7).getStringCellValue();
                customer.shoppingCar = JSONObject.parseArray(row.getCell(8).getStringCellValue(), ShopCar.class);
                customer.shopHistory = JSONObject.parseArray(row.getCell(9).getStringCellValue(), ShopHistory.class);
                getList1().add((E)customer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    private void readGoods() {
        File file = new File(GOODS_FILE_NAME);
        if (!file.exists()) {
            return;
        }
        //创建工作薄对象
        try {
            XSSFWorkbook workbook=new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("sheet");
            int rowNum = sheet.getLastRowNum();
            if (rowNum <= 1) {
                return;
            }
            for (int i = 2; i < rowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                Goods goods = new Goods();
                goods.itemCode = (long)row.getCell(0).getNumericCellValue();
                goods.name = row.getCell(1).getStringCellValue();
                goods.factory = row.getCell(2).getStringCellValue();
                goods.manufactureDate = row.getCell(3).getStringCellValue();
                goods.type = row.getCell(4).getStringCellValue();
                goods.purchasingPrice = row.getCell(5).getNumericCellValue();
                goods.retailPrice = row.getCell(6).getNumericCellValue();
                goods.amount = (int)row.getCell(7).getNumericCellValue();
                getList2().add(goods);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


    }


    private void writeUsers() {
        //创建工作薄对象
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建工作表对象
        XSSFSheet sheet = workbook.createSheet("sheet");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("用户名");
        row.createCell(1).setCellValue("用户密码");
        row.createCell(2).setCellValue("用户ID");
        row.createCell(3).setCellValue("用户级别");
        row.createCell(4).setCellValue("用户注册时间");
        row.createCell(5).setCellValue("用户累计消费金额");
        row.createCell(6).setCellValue("用户手机号");
        row.createCell(7).setCellValue("用户邮箱");
        row.createCell(8).setCellValue("购物车");
        row.createCell(9).setCellValue("购物历史");


        for(int i = 0; i < getList1().size(); i++) {
            if (i == 0) {
                Administrator administrator = (Administrator)(getList1().get(0));
                row = sheet.createRow(1);
                row.createCell(0).setCellValue(administrator.name);
                row.createCell(1).setCellValue(administrator.code);
                continue;
            }
            Customer customer = (Customer) getList1().get(i);
            row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(customer.name);
            row.createCell(1).setCellValue(customer.code);
            row.createCell(2).setCellValue(customer.ID);
            row.createCell(3).setCellValue(customer.grade);
            row.createCell(4).setCellValue(customer.registerTime);
            row.createCell(5).setCellValue(customer.totalMoney);
            row.createCell(6).setCellValue(customer.phoneNumber);
            row.createCell(7).setCellValue(customer.E_mail);
            row.createCell(8).setCellValue(JSONObject.toJSONString(customer.shoppingCar));
            row.createCell(9).setCellValue(JSONObject.toJSONString(customer.shopHistory));
        }

        try {
            FileOutputStream out = new FileOutputStream(USER_FILE_NAME);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void writeGoods() {
        //创建工作薄对象
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建工作表对象
        XSSFSheet sheet = workbook.createSheet("sheet");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("商品编号");
        row.createCell(1).setCellValue("商品名称");
        row.createCell(2).setCellValue("生产厂家");
        row.createCell(3).setCellValue("生产日期");
        row.createCell(4).setCellValue("型号");
        row.createCell(5).setCellValue("进货价");
        row.createCell(6).setCellValue("零售价");
        row.createCell(7).setCellValue("数量");

        for(int i = 0; i < getList2().size(); i++) {
            Goods goods = getList2().get(i);

            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(goods.itemCode);
            row.createCell(1).setCellValue(goods.name);
            row.createCell(2).setCellValue(goods.factory);
            row.createCell(3).setCellValue(goods.manufactureDate);
            row.createCell(4).setCellValue(goods.type);
            row.createCell(5).setCellValue(goods.purchasingPrice);
            row.createCell(6).setCellValue(goods.retailPrice);
            row.createCell(7).setCellValue(goods.amount);
        }

        try {
            FileOutputStream out = new FileOutputStream(GOODS_FILE_NAME);
            workbook.write(out);
            out.close();
        }catch (Exception e) {

        }
    }


    public void close() {
        File userFile = new File(USER_FILE_NAME);
        File goodsFile = new File(GOODS_FILE_NAME);
        if (userFile.exists()) {
            userFile.delete();
        }

        if (goodsFile.exists()) {
            goodsFile.delete();
        }

        writeUsers();
        writeGoods();

    }

}
