package ReentrantAndRecursionLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//资源类
class Phone implements Runnable{
    //模拟synchronized为可重入锁
    public synchronized void sendMES() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoked sendMES()");
        //可重入执行另一个加synchronized方法
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t #####invoked sendEmail()");
    }

    //============以下是模拟ReentrantLock可重入锁===============
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally {
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        }finally {
            lock.unlock();
        }
    }
}

/**
 * 可重入锁（也叫递归锁）
 * 指的是同一线程最外层函数获得锁之后，内层递归函数仍然能获得该锁的代码，
 * 在同一线程在最外层方法获取锁的时候，在进入内层方法会自动获取锁
 *
 * 也就是说，线程可以进入任何一个它已经拥有的锁所同步着的代码块
 */
public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendMES();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T1").start();
        new Thread(()->{
            try {
                phone.sendMES();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T2").start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        Thread t3 = new Thread(phone,"T3");
        Thread t4 = new Thread(phone,"T4");
        t3.start();
        t4.start();
    }
}
