package com.sthlike.java.review.thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class ShowAtomic {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(8);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.compareAndExchange(8, finalI);
                    System.out.println(atomicInteger.get());
                }
            }).start();
        }

    }
}
