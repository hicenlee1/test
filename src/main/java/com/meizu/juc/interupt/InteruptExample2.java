package com.meizu.juc.interupt;
/**
 * interupt 测试 interupt()方式终止
 * @author haiyang1
 *
 */

import java.util.logging.Logger;

public class InteruptExample2 implements Runnable {

    private static Logger LOG = Logger.getAnonymousLogger();
    
    
    public static void main(String[] args) throws InterruptedException {
        InteruptExample2 example = new InteruptExample2();
        Thread thread = new Thread(example, "interupt-thread");
        LOG.info("start thread");
        thread.start();
        
        Thread.sleep(3000);
        LOG.info("ask thread to stop");
        //设置中断信号量
        thread.interrupt();
        Thread.sleep(3000);
        LOG.info("stop application");

    }

    @Override
    public void run() {
        //每1s检测一次中断信号量
        while(!Thread.currentThread().isInterrupted()) {
            LOG.info("thread is running");
            long time = System.currentTimeMillis();
            // 使用while循环模拟 sleep
            while(System.currentTimeMillis() - time < 1000) {
            }
        }
        LOG.info("thread exit under request");
        
    }
    

}
