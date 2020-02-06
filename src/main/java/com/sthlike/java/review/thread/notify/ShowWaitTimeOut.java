package com.sthlike.java.review.thread.notify;

public class ShowWaitTimeOut {
    public static class Show {
        private int condition = 0;

        public synchronized void triggerCondition(int condition) {
            this.condition = condition;
            notifyAll();
        }

        public synchronized void checkCondition(long timeoutMillis) {
            if (timeoutMillis <= 0) {
                while (this.condition <= 10) {
                    try {
                        System.out.printf("thread %d start check conditions without timeout\n",
                                Thread.currentThread().getId());
                        wait();
                        System.out.printf("condition is %d, waiting in thread  %d\n",
                                this.condition, Thread.currentThread().getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("condition is %d now, bigger than 10, thread %d over\n",
                        this.condition, Thread.currentThread().getId());
            } else {
                long timeUntil = System.currentTimeMillis() + timeoutMillis;
                long remain = timeoutMillis;
                while (this.condition <= 10 && remain > 0) {
                    try {
                        System.out.printf("thread %d start check conditions with timeout\n",
                                Thread.currentThread().getId());
                        wait(remain);
                        remain = timeUntil - System.currentTimeMillis();
                        System.out.printf("condition is %d, waiting in thread  %d\n",
                                this.condition, Thread.currentThread().getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("condition is %d now, thread %d timeout\n",
                        this.condition, Thread.currentThread().getId());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Show show = new Show();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    show.checkCondition(0);
                }
            });
            thread.start();
        }

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    show.checkCondition(2000);
                }
            });
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println();
        show.triggerCondition(8);
        Thread.sleep(4000);
        System.out.println();
        show.triggerCondition(12);
    }
}
