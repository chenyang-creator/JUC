package com.yc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class blockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingDeque = new ArrayBlockingQueue<>(3);
        System.out.println(blockingDeque.add("a"));
        System.out.println(blockingDeque.add("b"));
        System.out.println(blockingDeque.add("c"));
        System.out.println(blockingDeque.add("a"));

    }
}