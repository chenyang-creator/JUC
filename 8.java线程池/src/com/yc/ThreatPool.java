package com.yc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreatPool {
    public static void main(String[] args) {
//        createThreadPoorByExecutors();

    }

    private static void createThreadPoorByExecutors() {
        //        ExecutorService threadPool = Executors.newFixedThreadPool(5);    //一个线程池中有5个线程处理
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();  //一个线程池只有1个线程处理
        ExecutorService threadPool = Executors.newCachedThreadPool();    //一个线程池根据需要创建多少线程，可多可少
        try {
            //迷你10个顾客来银行办理业务，目前池子里有5个工作人员提供服务
            for(int i=0;i<10;i++){
                //暂停几秒钟线程
                //TimeUnit.SECONDS.sleep(1);
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
