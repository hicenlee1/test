package com.meizu.juc.blockingqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.stream.IntStream;



public class CustomBlockingQueue {
    
    static Logger LOG = Logger.getAnonymousLogger();
    
    ReentrantLock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    int maxlength = 10;
    Queue<Object> q = new LinkedList<Object>();
    
    public void put(Object o) throws InterruptedException {
        lock.lock();
            
        try {
            if (q.size() == 0) {
                notEmpty.signalAll();
            }
            if (q.size() == maxlength) {
                notFull.await();
            }
            q.add(o);
        } finally {
            lock.unlock();
        }
    }
    
    public Object take() throws InterruptedException {
        lock.lock();
        
        try {
            if (q.size() == 0) {
                notEmpty.await();
            }
            if (q.size() == maxlength) {
                notFull.signalAll();
            }
            return q.poll();
        } finally {
            lock.unlock();
        }
    }
    
    public static void main(String[] args) {
        CustomBlockingQueue q = new CustomBlockingQueue();
        new Thread(() -> {
            IntStream.range(0, 100).forEach(index -> {
                try {
                    LOG.info(Thread.currentThread().getName() + "ready to put " + index);
                    q.put(index);
                    LOG.info(Thread.currentThread().getName() + "success put " + index);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }, "put-thread").start();
        
        new Thread(() -> {
            for(;;) {                
                Object element;
                try {
                    Thread.sleep(1000);
                    LOG.info(Thread.currentThread().getName() + "ready to take... ");
                    element = q.take();
                    LOG.info(Thread.currentThread().getName() + "success take " + element);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, "take-thread").start(); 
    }
    
    
    
}
