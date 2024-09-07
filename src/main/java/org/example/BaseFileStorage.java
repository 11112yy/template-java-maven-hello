package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhou xiao fen
 * @Date 2023/8/23 20:38
 **/
public abstract class BaseFileStorage<E> {
    List<E> list1 = new ArrayList<E>(); // 管理员和用户链表
    List<Goods> list2 = new ArrayList<Goods>(); // 商品链表

    public BaseFileStorage() {
        read();
    }

    public abstract void read();

    public List<E> getList1() {
        return list1;
    }

    public List<Goods> getList2() {
        return list2;
    }
}
