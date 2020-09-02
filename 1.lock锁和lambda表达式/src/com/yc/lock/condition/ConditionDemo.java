package com.yc.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现精确打印，当A打印5次，接着B打印7次，最后C打印10次，循环10轮
 */
public class ConditionDemo {
    public static void main(String[] args) {
        Res res = new Res();
        new Thread(()->{
            for(int i=0;i<10;i++){
                res.print5();
            }
        },"线程1").start();
        new Thread(()->{
            for(int i=0;i<10;i++){
                res.print7();
            }
        },"线程2").start();
        new Thread(()->{
            for(int i=0;i<10;i++){
                res.print10();
            }
        },"线程3").start();
    }
}
class Res{

    private int number = 1;

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            while (number != 1){
                condition1.await();
            }
            for(int i=0;i<5;i++){
                System.out.println(Thread.currentThread().getName()+"打印A");
            }
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print7(){
        lock.lock();
        try {
            while (number != 2){
                condition2.await();
            }
            for(int i=0;i<7;i++){
                System.out.println(Thread.currentThread().getName()+"打印B");
            }
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print10(){
        lock.lock();
        try {
            while (number != 3){
                condition3.await();
            }
            for(int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName()+"打印C");
            }
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
