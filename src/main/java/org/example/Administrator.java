package org.example;
import java.io.Serializable;

public class Administrator implements Serializable {
    String name;
    String code;

    Administrator() {
        name = "admin"; // 用户名
        code = MD5.match("ynuinfo#777");
    }
}
