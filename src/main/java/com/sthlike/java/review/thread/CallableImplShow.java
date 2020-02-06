package com.sthlike.java.review.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableImplShow {
    public static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return Thread.currentThread().getName();
        }
    }

    public static void main(String[] args) {
        FutureTask<String> ft = new FutureTask<>(new MyCallable());
        Thread t = new Thread(ft);
        t.setName("t");
        t.start();
        try {
            System.out.println(ft.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
