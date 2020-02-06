package com.sthlike.java.review.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowSingleExecutor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            System.out.println("executed by single thread");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("executed by single thread");
            }
        });
        executorService.shutdown();
    }
}
