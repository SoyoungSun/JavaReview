package com.sthlike.java.review.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShowScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService executor = (ScheduledExecutorService) Executors.newScheduledThreadPool(2);
        executor.schedule(() -> System.out.println("executing1"), 2, TimeUnit.SECONDS);
        executor.schedule(() -> System.out.println("executing2"), 1, TimeUnit.SECONDS);
        executor.schedule(() -> System.out.println("executing3"), 3, TimeUnit.SECONDS);
        System.out.println("before");
        executor.shutdown();
    }

}
