package com.yc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//    private Lock lock = new ReentrantLock();

    public void put(String str,Object obj){
        readWriteLock.writeLock().lock();
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t ---写入数据"+str);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(str,obj);
            System.out.println(Thread.currentThread().getName()+"\t ---写入完成");
        }finally {
            readWriteLock.writeLock().unlock();
//            lock.unlock();
        }
    }
    public void get(String str){
        readWriteLock.readLock().lock();
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 读取数据"+str);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object obj = map.get(str);
            System.out.println(Thread.currentThread().getName()+"\t 读取数据完成"+obj);
        }finally {
            readWriteLock.readLock().unlock();
//            lock.unlock();
        }
    }
}
public class ReadWriteLockMain {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for(int i=0;i<10;i++){
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }
        for(int i=0;i<10;i++){
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}
