package com.meizu.juc.lockfree;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeDemo {
    
    /**
     * 同步设置办法
     */
    private volatile int intMax;
    
    public synchronized void setSynchronized(int value) {
        if (value > intMax) {
            intMax = value;
        }
    }
    
    public int getIntMax() {
        return intMax;
    }
    
    /**
     * 无锁版本：适合非激烈竞争的场景，没有锁，性能更好
     * lock-fress 三要素：
     * 循环    cas  回退
     * lock-free  可能导致ABA问题
     */
    private AtomicInteger max = new AtomicInteger();
    
    public void setLockfree(int value) {
        int current;
        do {
            current = max.intValue();
            if (value > current) {
                max.set(value);
            }
        } while(!max.compareAndSet(current, value));
    }
    
    public int getLockfree() {
        return max.intValue();
    }
}
