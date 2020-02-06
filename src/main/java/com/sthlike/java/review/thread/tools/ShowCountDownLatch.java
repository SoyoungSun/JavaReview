package com.sthlike.java.review.thread.tools;

import java.util.concurrent.CountDownLatch;

public class ShowCountDownLatch {
    private static final CountDownLatch lock = new CountDownLatch(3);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new CountDownRunnable1()).start();
            new Thread(new CountDownRunnable2()).start();
        }

        new Thread(new WaitRunnable()).start();
    }

    public static class WaitRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("wait runnable start waiting");
            try {
                lock.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("wait runnable end");
        }
    }

    public static class CountDownRunnable1 implements Runnable {

        @Override
        public void run() {
            System.out.println("countdown1 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.countDown();
            System.out.println("countdown1 end");
        }
    }

    public static class CountDownRunnable2 implements Runnable {

        @Override
        public void run() {
            System.out.println("countdown2 start");
            try {
                Thread.sleep(1300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.countDown();
            System.out.println("countdown2 end");
        }
    }
}
