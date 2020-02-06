package com.sthlike.java.review.thread.tools;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ShowSemaphore {
    public static void main(String[] args) {
        CalculatorPool pool = new CalculatorPool();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    pool.calculate(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static class CalculatorPool {
        private final int MAX_POOL_SIZE = 3;
        private final LinkedList<Calculator> pool = new LinkedList<>();
        private final Semaphore remain;

        public int calculate(int base) throws InterruptedException {
            long start = System.currentTimeMillis();
            remain.acquire();
            System.out.printf("Thread %d take %d ms to get resource\n",
                    Thread.currentThread().getId(),
                    System.currentTimeMillis() - start);
            Calculator calculator;
            synchronized (pool) {
                calculator = pool.removeFirst();
            }
            int result = calculator.calculate(base);
            synchronized (pool) {
                pool.addLast(calculator);
            }
            System.out.printf("%d threads is waiting for calculator\n",
                    remain.getQueueLength());
            remain.release();
            return result;
        }

        public CalculatorPool() {
            this.remain = new Semaphore(MAX_POOL_SIZE);
            for (int i = 0; i < MAX_POOL_SIZE; i++) {
                pool.addLast(new Calculator());
            }
        }
    }

    public static class Calculator {
        public int calculate(int base) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return base;
        }
    }
}
