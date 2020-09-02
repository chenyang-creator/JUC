package com.yc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类
 */
public class Ticket {
    //属性：剩余票数,初始值赋予30
    private int numbers = 30;
    /**
     * lock锁,Lock接口有三个实现类:
     * ReentrantLock：可重入锁
     */
    Lock lock = new ReentrantLock();
    //方法，买票
    public void sell(){
        //lock模板要求
        lock.lock();
        try{
            //业务逻辑代码
            if(numbers > 0){
                System.out.println(Thread.currentThread().getName()+"  购买了第"+(numbers--)+"张票，还剩余"+numbers+"张票！");
            }
        }catch (Exception e){       //在lock官方模板代码中，可以不应写catch
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
