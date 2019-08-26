package com.meizu.juc.synchronusqueue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class SynchronousProducer implements Runnable {
    static Logger LOG = Logger.getAnonymousLogger();
    
    private BlockingQueue<String> q;
    
    public SynchronousProducer(BlockingQueue<String> q) {
        this.q = q;
    }
    
    @Override
    public void run() {
        for(;;) {
            try {
                String uuid = UUID.randomUUID().toString();
                LOG.info("product => " + uuid);
                q.put(uuid);
                LOG.info("put to queue =>" + uuid);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }

    }

}
