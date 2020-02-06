package com.sthlike.java.review.thread.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ShowFixedThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        Future<String> future = executor.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "with return value";
        });
        executor.submit(() -> {
            System.out.println("without return value");
        });
        try {
            String re = future.get();
            System.out.println(re);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.submit(() -> System.out.println("after sleeping"));
        printSIzeOfPoolAndQueen(executor);
        executor.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("exceed limit 1");
        });
        printSIzeOfPoolAndQueen(executor);
        executor.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("exceed limit 2");
        });
        executor.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("exceed limit 3");
        });
        printSIzeOfPoolAndQueen(executor);
        executor.shutdown();
    }

    private static void printSIzeOfPoolAndQueen(ThreadPoolExecutor ex) {
        System.out.printf("pool size is %d and queen size is %d\n", ex.getPoolSize(), ex.getQueue().size());
    }
}
