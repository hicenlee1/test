package com.meizu.juc.flowcontrol.cylicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class CylicBarrierDemo {
    
    private static Logger LOG = Logger.getAnonymousLogger();
    public static void main(String[] args) {
        /**
         * CylicBarrier
         * 思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。
         * 叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用(CountdownLatch 不可重用)。
         * 我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了
         */
        CyclicBarrier barrier = new CyclicBarrier(3, ()-> {
            System.out.println("arrive barrier");
        });
        
        IntStream.range(0, 3).forEach(index -> {
            new Thread(() -> {
               LOG.info("start thread " + Thread.currentThread().getName());
               Random r = new Random();
               try {
                   int sleeptime = r.nextInt(2000);
                   LOG.info(Thread.currentThread().getName() + " sleep " + sleeptime + " and wait");
                   Thread.sleep(sleeptime);
                   barrier.await();
                   
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               LOG.info("end thread " + Thread.currentThread().getName());
               
            }, "thread-test" + index).start(); 
        });
        //初次的3个线程越过barrier状态后，又可以用来进行新一轮的使用。而CountDownLatch无法进行重复使用
        IntStream.range(3, 6).forEach(index -> {
            new Thread(() -> {
               LOG.info("start thread " + Thread.currentThread().getName());
               Random r = new Random();
               try {
                   int sleeptime = r.nextInt(2000);
                   LOG.info(Thread.currentThread().getName() + " sleep " + sleeptime + " and wait");
                   Thread.sleep(sleeptime);
                   barrier.await();
                   
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               LOG.info("end thread " + Thread.currentThread().getName());
               
            }, "thread-test" + index).start(); 
        });
        
        
    }
}
