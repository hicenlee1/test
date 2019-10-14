package com.meizu.disruptor;

public class FalseSharing implements Runnable{
    public final static int NUM_THREADS = 4;
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;
    private static VolatileLog[] longs = new VolatileLog[NUM_THREADS];
    public FalseSharing(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLog();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long begin = System.nanoTime();
        runTest();
        long duration = System.nanoTime() -begin;
        System.out.println("duration =>" + duration + " ns");
    }

    public static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS;
        while (0 != i--) {
            longs[arrayIndex].value = i;
        }
    }

    public final static class VolatileLog {
        public volatile long value = 0L;
        //public long p1, p2, p3, p4, p5, p6, p7;
    }
}
