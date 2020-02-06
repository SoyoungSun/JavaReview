package com.sthlike.java.review.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MainShow {
    public static void main(String[] args) {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = mxBean.dumpAllThreads(true, true);
        for (ThreadInfo t : threadInfos) {
            System.out.printf("[%s] %s\n", t.getThreadId(), t.getThreadName());
        }
    }
}
