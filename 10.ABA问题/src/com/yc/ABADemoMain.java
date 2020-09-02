package com.yc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemoMain {
    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=====以下是ABA问题的产生=====");
        new Thread(()->{
            atomicInteger.compareAndSet(100,101);
            atomicInteger.compareAndSet(101,100);
        },"T1").start();
        new Thread(()->{
            try {
                //延迟一秒，保证T1线程先执行
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //及时T1线程对目标进行了修改，T2线程判断目标文件的值和期望值一样仍然可以执行成功
            System.out.println(atomicInteger.compareAndSet(100, 2020)+"\t"+atomicInteger.get());
        },"T2").start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println("======以下是ABA问题的解决");

        new Thread(()->{
            //得到目标对象的版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"版本号是："+stamp);
            try {
                //延迟1秒是为了让T4线程得到当前对象的版本号
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //模拟ABA
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"当前版本号:"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"当前版本号:"+atomicStampedReference.getStamp());
        },"T3").start();
        new Thread(()->{
            //得到目标对象的版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"版本号是："+stamp);
            try {
                //延迟3秒是让上边T3线程先执行一次ABA
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicStampedReference.compareAndSet(100, 2020, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"修改是否成功:"+b+"\t当前版本号:"+atomicStampedReference.getStamp());
        },"T4").start();

    }
}
