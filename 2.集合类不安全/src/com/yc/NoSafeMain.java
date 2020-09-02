package com.yc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *报错：java.util.ConcurrentModificationException
 * 导致原因：多线程并发争抢同一个资源，并且资源没有加锁
 * 解决办法：1）使用线程安全类new Vector()代替ArrayList
 *          2)使用集合工具类Collections.synchronizedList(new ArrayList<>());
 *          3)使用写时复制类new CopyOnWriteArrayList()代替ArrayList
 */
public class NoSafeMain {
    public static void main(String[] args) {
//        ListNoSafe();
//        SetNoSafe();
        MapNoSafe();
    }

    private static void ListNoSafe() {
        //模拟多线程使用ArrayList报错
        List<String> list = new ArrayList<>();
        List<String> list1 = new Vector<>();
        List<String> list2 = Collections.synchronizedList(new ArrayList<>());
        List<String> list3 = new CopyOnWriteArrayList();
        //模拟多条线程对ArrayList进行增加数据
        for (int i = 0; i < 30; i++) {
            //使用lambda表达式创建线程
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
    private static void SetNoSafe() {
        //模拟多线程使用Set报错
//        Set<String> set = new HashSet();
        Set<String> set = new CopyOnWriteArraySet<>();
        //模拟多条线程对Set进行增加数据
        for (int i = 0; i < 30; i++) {
            //使用lambda表达式创建线程
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
    private static void MapNoSafe() {
        //模拟多线程使用Map报错
        Map<String,String> map = new HashMap<>();
//        Map<String,String> map = new ConcurrentHashMap<>();
        //模拟多条线程对Map进行增加数据
        for (int i = 0; i < 30; i++) {
            //使用lambda表达式创建线程
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
