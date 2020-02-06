package com.sthlike.java.review.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ShowCachedThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        executor.submit(runnable);
        printSIzeOfPoolAndQueen(executor);
        executor.submit(runnable);
        printSIzeOfPoolAndQueen(executor);
        executor.submit(runnable);
        printSIzeOfPoolAndQueen(executor);
        executor.submit(runnable);
        printSIzeOfPoolAndQueen(executor);
        executor.shutdown();
    }

    private static void printSIzeOfPoolAndQueen(ThreadPoolExecutor ex) {
        System.out.printf("pool size is %d and queen size is %d\n", ex.getPoolSize(), ex.getQueue().size());
    }
}
