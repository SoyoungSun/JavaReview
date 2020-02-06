package com.sthlike.java.review.thread.notify;

public class ShowNotify {
    public static class Show {

        private int condition1 = 0;

        private boolean condition2 = false;

        public synchronized void checkCondition1() {
            while (this.condition1 <= 10) {
                try {
                    System.out.printf("thread %d start check conditions1 now\n",
                            Thread.currentThread().getId());
                    wait();
                    System.out.printf("condition1 is %d, waiting in thread  %d\n",
                            this.condition1, Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("condition1 is %d now, bigger than 10, thread %d over\n",
                    this.condition1, Thread.currentThread().getId());
        }

        public synchronized void checkCondition2() {
            while (!condition2) {
                try {
                    System.out.printf("thread %d start check conditions2 now\n",
                            Thread.currentThread().getId());
                    wait();
                    System.out.printf("condition2 is false, waiting in thread %d\n",
                            Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("condition2 is true now, thread %d over\n",
                    Thread.currentThread().getId());
        }

        public synchronized void changeCondition1(int condition1) {
            this.condition1 = condition1;
            notifyAll();
        }

        public synchronized void changeCondition2(boolean condition2) {
            this.condition2 = condition2;
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Show show = new Show();
        for (int i = 0; i < 3; i++) {
            Thread condition2Runner = new Thread(new Runnable() {
                @Override
                public void run() {
                    show.checkCondition2();
                }
            });
            condition2Runner.start();
        }
        for (int i = 0; i < 3; i++) {
            Thread condition1Runner = new Thread(new Runnable() {
                @Override
                public void run() {
                    show.checkCondition1();
                }
            });
            condition1Runner.start();
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
}
