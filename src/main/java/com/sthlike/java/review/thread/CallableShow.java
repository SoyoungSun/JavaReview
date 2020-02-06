package com.sthlike.java.review.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableShow {

    public static void main(String[] args) {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName();
            }
        };
        FutureTask<String> ft1 = new FutureTask<String>(callable);
        FutureTask<String> ft2 = new FutureTask<String>(callable);
        Thread t1 = new Thread(ft1);
        t1.setName("t1");
        Thread t2 = new Thread(ft2);
        t2.setName("t2");
        t1.start();
        t2.start();
        try {
            System.out.println(ft1.get());
            System.out.println(ft2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("main over");
    }


}
