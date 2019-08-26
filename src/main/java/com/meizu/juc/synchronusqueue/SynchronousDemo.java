package com.meizu.juc.synchronusqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousDemo {
    
    
    
    public static void main(String[] args) {
        BlockingQueue<String> q = new SynchronousQueue<String>();
        SynchronousProducer producer = new SynchronousProducer(q);
        SynchronousConsumer consumer1 = new SynchronousConsumer(q);
        SynchronousConsumer consumer2 = new SynchronousConsumer(q);
        
        new Thread(producer, "producer").start();
        new Thread(consumer1, "consumer1").start();
        new Thread(consumer2, "consumer2").start();

    }

}
