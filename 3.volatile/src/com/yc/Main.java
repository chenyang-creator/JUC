package com.yc;

import java.util.concurrent.atomic.AtomicInteger;

class MyDate{
    volatile int number = 0;  //volatile修饰，使其可见性
//    int number = 0;

    public void addTo60(){
        this.number = 60;
    }
    //注意，此时number前边是加了volatile关键字修饰的保证可见性，但是volatile不保证原子性
    public void addPlus(){
        number++;
    }
    //保证原子性
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1.验证volatile的可见性
 *  1.1假如 int number = 0;number变量之前没有添加volatile关键字修饰
 *  1.2添加了volatile，可以解决可见性问题
 *
 * 2.验证volatile不保证原子性
 *  2.1原子性指的是不可分割，完整性，要么同时成功，要么同时失败
 *  2.2如何解决？
 *      *加synchronized
 *      *使用JUC下的原子类
 */
public class Main {
    public static void main(String[] args) {
        enSureAtomic();
//        ensureSeeVolatile();
    }

    public static void enSureAtomic() {
        MyDate myDate = new MyDate();//资源类
        for(int i=0;i<20;i++){
            new Thread(()->{
                for(int j=0;j<1000;j++){
                    myDate.addPlus();
                    myDate.addMyAtomic();
                }
            },String.valueOf(i)).start();
        }
        //等待上面20个线程全部计算完成后再用main线程取得最终的结果值
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        //出现丢失写值的情况
        System.out.println(Thread.currentThread().getName()+"\t finally number value: "+myDate.number);
        //使用原子类，保证原子性
        System.out.println(Thread.currentThread().getName()+"\t finally number value: "+myDate.atomicInteger);
    }

    //证明1：volatile 可以保证可见性，及时通知其他线程主物理内存的值已经被修改
    public static void ensureSeeVolatile(){
        //线程操作资源类
        MyDate myDate = new MyDate();//资源类
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //三秒钟后将资源类的number修改为60
            myDate.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t updated number value: "+myDate.number);
        },"AAA").start();
        //第二个线程，main线程
        while (myDate.number == 0){//只要number没有修改，就一直在这里循环

        }
        //有些java版本不加volatile，也有可见性
        System.out.println(Thread.currentThread().getName()+"\t mission is over");
    }
}
