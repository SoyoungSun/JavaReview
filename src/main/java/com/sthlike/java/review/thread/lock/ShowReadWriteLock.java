package com.sthlike.java.review.thread.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ShowReadWriteLock {
    private static ReadWrite readWrite = new ReadWrite();

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new WriteRunnable()).start();
        }
        for (int i = 0; i < 30; i++) {
            new Thread(new ReadRunnable()).start();
        }
    }

    private static class ReadRunnable implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            int total = readWrite.getTotal();
            System.out.printf("the total is %d and get total in %d ms\n",
                    total, System.currentTimeMillis() - start);
        }
    }

    private static class WriteRunnable implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            int random = new Random().nextInt(100);
            readWrite.setTotal(random);
            System.out.printf("set total %d in %d ms\n",
                    random, System.currentTimeMillis() - start);
        }
    }

    private static class ReadWrite {
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();

        private int total;

        public int getTotal() {
            readLock.lock();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                return this.total;
            }
        }

        public void setTotal(int total) {
            writeLock.lock();
            try {
                Thread.sleep(10);
                this.total = total;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }
    }
}
