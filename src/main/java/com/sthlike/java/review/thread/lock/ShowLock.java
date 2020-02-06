package com.sthlike.java.review.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShowLock {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new LockRunnable()).start();
        }
    }

    private static class LockRunnable implements Runnable {

        private final Lock lock = new ReentrantLock();

        @Override
        public void run() {
            System.out.printf("Thread %d will get lock\n",
                    Thread.currentThread().getId());
            lock.lock();
            System.out.printf("Thread %d got the lock\n",
                    Thread.currentThread().getId());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.printf("Thread %d released the lock\n",
                        Thread.currentThread().getId());
            }
        }
    }
}
