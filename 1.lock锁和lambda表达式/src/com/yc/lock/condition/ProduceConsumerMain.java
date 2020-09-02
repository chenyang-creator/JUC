package com.yc.lock.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产一个，消费一个，来回交替来10轮
 */
public class ProduceConsumerMain {
    public static void main(String[] args) {
        testSynchronized();
//        testLock();
    }
    public static void testSynchronized(){
        Resource resource = new Resource();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    resource.increaseBySyn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    resource.decreaseBySyn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
    }
    public static void testLock(){
        Resource resource = new Resource();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resource.increaseByLock();
            }
        },"A").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resource.decreaseByLock();
            }
        },"B").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resource.increaseByLock();
            }
        },"C").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resource.decreaseByLock();
            }
        },"D").start();
    }
}
//资源类
class Resource{
    private int number = 0;

    private Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    public synchronized void increaseBySyn() throws InterruptedException {
        while(number != 0){
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"生产number:"+number);
        this.notifyAll();
    }
    public synchronized void decreaseBySyn() throws InterruptedException {
        while(number == 0){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"消费number:"+number);
        this.notifyAll();
    }

    public void increaseByLock(){
        lock.lock();
        try {
            while(number != 0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"生产number:"+number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void decreaseByLock(){
        lock.lock();
        try {
            while(number == 0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"消费number:"+number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
