package com.sthlike.java.review.thread.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

public class ShowExchanger {
    private static final Exchanger<Set<String>> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Consumer().start();
        new Producer().start();
    }

    public static class Producer extends Thread {
        @Override
        public void run() {
            Set<String> set = new HashSet<>();
            while (!isInterrupted()) {
                try {
                    if (!set.isEmpty()) {
                        set.clear();
                        System.out.printf("consumer over, size is %d\n", set.size());
                    }
                    set = exchanger.exchange(set);
                    System.out.printf("get products from producer, size is %d\n", set.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer extends Thread {
        @Override
        public void run() {
            Set<String> set = new HashSet<>();
            while (!isInterrupted()) {
                for (int i = 0; i < 10; i++) {
                    set.add(Integer.toString(i));
                }
                try {
                    Thread.sleep(1000);
                    System.out.printf("%d products produced\n", set.size());
                    set = exchanger.exchange(set);
                    System.out.printf("get empty set from consumer, size is %d\n", set.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
