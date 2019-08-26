package com.meizu.juc.produceconsumer;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


public class ProConThreadFactory implements ThreadFactory {
    
    Logger LOG = Logger.getLogger(ProConThreadFactory.class.getName());
    private String prefix;
    
    private static AtomicInteger index = new AtomicInteger(0);
    
    public ProConThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + "-thread-" + index.getAndIncrement();
        LOG.info("create thread ,name " + name);
        Thread t = new Thread(r, name);
        return t;
    }

}
