package com.meizu.juc.produceconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Consumer implements Runnable {
    
    Logger LOG = Logger.getLogger(Consumer.class.getName());
            
    BlockingQueue<?> q;
    
    public Consumer(BlockingQueue<?> q) {
        this.q = q;
    }
    
    @Override
    public void run() {
        for(;;) {
            try {
                Object product = q.take();
                LOG.info("consumer product:" + product);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
