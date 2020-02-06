package com.sthlike.java.review.thread.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ShowCyclicBarrier {
    private static final CyclicBarrier lock = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("all thread end");
        }
    });

    public static void main(String[] args) {
        new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();
    }

    public static class Runnable1 implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable1 start");
            try {
                lock.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("Runnable1 end");
        }
    }

    public static class Runnable2 implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable2 start");
            try {
                lock.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("Runnable2 end");
        }
    }
}
