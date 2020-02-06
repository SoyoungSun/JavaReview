package com.sthlike.java.review.thread;

public class DaemonShow {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.printf("print in %s\n", Thread.currentThread().getName());
                    }
                } finally {
                    System.out.printf("%s final\n", Thread.currentThread().getName());
                }
            }
        };

        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        t1.setDaemon(true);
        t1.start();
        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        t2.start();
        t2.interrupt();
        System.out.printf("main over after %d millis\n", System.currentTimeMillis() - start);
    }
}
