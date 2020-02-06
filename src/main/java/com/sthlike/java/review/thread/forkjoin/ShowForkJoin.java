package com.sthlike.java.review.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ShowForkJoin {

    public static void main(String[] args) {
        TreeNode project = constructProject();
        ForkJoinPool pool = new ForkJoinPool();
        ComputeTask task = new ComputeTask(project);
        pool.invoke(task);
        System.out.println(task.join());
    }

    public static class ComputeTask extends RecursiveTask<Long> {
        private TreeNode node;

        public ComputeTask(TreeNode node) {
            this.node = node;
        }

        @Override
        protected Long compute() {
            System.out.println(getPool().getQueuedTaskCount());
            if (NodeType.SUB_PROJECT.equals(node.type)) {
                for (TreeNode item : this.node.children) {
                    this.node.amount += item.calcCost();
                }
            } else {
                List<ComputeTask> tasks = new ArrayList<>();
                for (TreeNode treeNode : this.node.children) {
                    ComputeTask task = new ComputeTask(treeNode);
                    tasks.add(task);
                }
                invokeAll(tasks);
                for (ComputeTask task : tasks) {
                    this.node.amount += task.join();
                }
            }
            return this.node.calcCost();
        }
    }

    public enum NodeType {
        PROJECT, SUB_PROJECT, ITEM
    }

    public static class TreeNode {
        public TreeNode(NodeType type, long amount, TreeNode parent) {
            this.type = type;
            this.amount = amount;
            if (parent != null) {
                this.parent = parent;
                this.parent.children.add(this);
            }
        }

        public long calcCost() {
            return this.amount;
        }

        private NodeType type;
        private TreeNode parent;
        private long amount;
        private List<TreeNode> children = new ArrayList<>();
    }


    private static TreeNode constructProject() {
        TreeNode project = new TreeNode(NodeType.PROJECT, 0, null);
        for (int i = 0; i < 20; i++) {
            TreeNode subProject = new TreeNode(NodeType.SUB_PROJECT, 0, project);
            for (int j = 0; j < 5000; j++) {
                new TreeNode(NodeType.ITEM, j, subProject);
            }
        }
        return project;
    }

}
