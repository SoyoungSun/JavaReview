package com.sthlike.java.review.thread.cas;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ShowAtomicReference {
    public static void main(String[] args) {
        AtomicReference<String> atomicReference = new AtomicReference<>("test");
        System.out.println(atomicReference.get());
        atomicReference.compareAndSet("test", "changed");
        System.out.println(atomicReference.get());

        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("aaa", 0);
        System.out.println(atomicStampedReference.getReference());
        atomicStampedReference.compareAndSet("aaa", "bbb", 0, 1);
        System.out.println(atomicStampedReference.getReference());
        atomicStampedReference.compareAndSet("bbb", "ccc", 0, 2);
        System.out.println(atomicStampedReference.getReference());
    }
}
