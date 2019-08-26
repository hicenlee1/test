package com.meizu.juc.produceconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


public class Producer implements Runnable {
    Logger LOG = Logger.getLogger(Producer.class.getName());
    
    BlockingQueue q;
    
    static AtomicInteger inc = new AtomicInteger(0);
    
    public Producer(BlockingQueue q) {
        this.q = q;
    }
    @Override
    public void run() {
        for (;;) {            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int product = inc.getAndIncrement();
            LOG.info("produce product: " + product);
            q.offer(product);
        }
    }
    
}
