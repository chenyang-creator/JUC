package com.yc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        closeDoor2();
        //closeDoor();
    }
    public static void closeDoor2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" 离开教室");
                //计数+1
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        //当达到new CountDownLatch(10)当中设置的数字时，才进行以下线程的开始，也就是倒数计数
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+" 班长，离开教室");
    }

    public static void closeDoor(){
        for(int i=0;i<10;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" 离开教室");
            },String.valueOf(i)).start();
        }
        System.out.println(Thread.currentThread().getName()+" 班长，离开教室");
    }
}
