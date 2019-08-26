package com.meizu.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class CallableDemo implements Callable<Integer>{
    static Logger LOG = Logger.getAnonymousLogger();
    
    AtomicInteger inc = new AtomicInteger(0);
    
    @Override
    public Integer call() throws Exception {
        int v = inc.getAndIncrement();
        LOG.info("generate " + v);
        Thread.sleep(1000);
        LOG.info("return " + v);
        return v;
    }
    
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> f1 = service.submit(new CallableDemo());
        
        try {
            int v1 = f1.get();
            LOG.info("get " + v1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Future<Integer> f2 = service.submit(new CallableDemo());
        try {
            int v2 = f2.get(500, TimeUnit.MILLISECONDS);
            System.out.println("v2=" + v2);
            
            // test cancel
//            Thread.sleep(100);
//            if (!f2.isDone()) {
//                if (!f2.isCancelled()) {
//                    System.out.println("cancel job");
//                    f2.cancel(false);
//                }
//            }
//            int v2 = f2.get();
//            System.out.println("v2=" + v2);
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
