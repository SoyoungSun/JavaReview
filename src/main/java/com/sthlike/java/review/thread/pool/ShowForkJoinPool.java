package com.sthlike.java.review.thread.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class ShowForkJoinPool {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Node tree = new Node(1,
                new Node(2,
                        new Node(3)),
                new Node(4),
                new Node(5,
                        new Node(6,
                                new Node(7))));
        int sum = forkJoinPool.invoke(new CalculatingTask(tree));
        System.out.println(sum);
        forkJoinPool.shutdown();
    }

    public static class CalculatingTask extends RecursiveTask<Integer> {

        private Node node;

        public CalculatingTask(Node node) {
            this.node = node;
        }

        @Override
        protected Integer compute() {
            return this.node.value + this.node.children.stream()
                    .map(child -> new CalculatingTask(child).fork())
                    .collect(Collectors.summingInt(ForkJoinTask::join));
        }
    }

    public static class Node {
        private int value;
        private List<Node> children;

        public Node(int value, Node... children) {
            this.value = value;
            this.children = new ArrayList<Node>(Arrays.asList(children));
        }
    }
}
