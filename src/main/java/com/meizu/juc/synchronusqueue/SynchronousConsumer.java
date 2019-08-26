package com.meizu.juc.synchronusqueue;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class SynchronousConsumer implements Runnable {
    static Logger LOG = Logger.getAnonymousLogger();
    
    private BlockingQueue<String> q;
    
    public SynchronousConsumer(BlockingQueue<String> q) {
        // TODO Auto-generated constructor stub
        this.q = q;
    }
    @Override
    public void run() {
        for(;;) {
            try {
                LOG.info(Thread.currentThread().getName() +  " ready to consume product");
                String product = q.take();
                LOG.info(Thread.currentThread().getName() +  " consume product =>" + product);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
