package com.yc.lock;

public class Main {
    public static void main(String[] args) {
        System.out.println("==============");
        //先将资源类加载进来
        Ticket ticket = new Ticket();
        //使用匿名内部类，来实现多线程
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<40;i++){
                    ticket.sell();
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<40;i++){
                    ticket.sell();
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<40;i++){
                    ticket.sell();
                }
            }
        }, "C").start();
        */
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sell();}, "AA").start();

        //使用lambda表达式
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sell();}, "A").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sell();}, "B").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sell();}, "C").start();

    }
}
