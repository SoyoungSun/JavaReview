package com.sthlike.java.review.thread;

public class ThreadShow {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Thread t = new MyThread();
        t.setPriority(5);
        t.setName("t");
        t.start();
    }
}
