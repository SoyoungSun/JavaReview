package com.sthlike.java.review.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShowCondition {
    public static void main(String[] args) throws InterruptedException {
        Show show = new Show();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    show.checkCondition2();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    show.checkCondition1();
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println();
        show.changeCondition1(8);
        Thread.sleep(1000);
        System.out.println();
        show.changeCondition1(11);
        Thread.sleep(1000);
        System.out.println();
        show.changeCondition2(true);
        Thread.sleep(1000);
    }

    private static class Show {
        private Lock lock = new ReentrantLock();
        private Condition lockCondition1 = lock.newCondition();
        private Condition lockCondition2 = lock.newCondition();

        private int condition1 = 0;

        private boolean condition2 = false;

        public void checkCondition1() {
            lock.lock();
            try {
                while (this.condition1 <= 10) {
                    try {
                        System.out.printf("thread %d start check conditions1 now\n",
                                Thread.currentThread().getId());
                        lockCondition1.await();
                        System.out.printf("condition1 is %d, waiting in thread  %d\n",
                                this.condition1, Thread.currentThread().getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("condition1 is %d now, bigger than 10, thread %d over\n",
                        this.condition1, Thread.currentThread().getId());
            } finally {
                lock.unlock();
            }
        }

        public void checkCondition2() {
            lock.lock();
            try {
                while (!condition2) {
                    try {
                        System.out.printf("thread %d start check conditions2 now\n",
                                Thread.currentThread().getId());
                        lockCondition2.await();
                        System.out.printf("condition2 is false, waiting in thread %d\n",
                                Thread.currentThread().getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("condition2 is true now, thread %d over\n",
                        Thread.currentThread().getId());
            } finally {
                lock.unlock();
            }
        }

        public void changeCondition1(int condition1) {
            lock.lock();
            try {
                this.condition1 = condition1;
                lockCondition1.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void changeCondition2(boolean condition2) {
            lock.lock();
            try {
                this.condition2 = condition2;
                lockCondition2.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
