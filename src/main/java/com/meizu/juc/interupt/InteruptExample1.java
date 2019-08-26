package com.meizu.juc.interupt;
/**
 * interupt 测试--信号量方式终止
 * @author haiyang1
 *
 */

import java.util.logging.Logger;

public class InteruptExample1 implements Runnable{

    private static Logger LOG = Logger.getAnonymousLogger();
    
    volatile boolean stop = false;
    
    public boolean getStop() {
        return this.stop;
    }
    
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    public static void main(String[] args) throws InterruptedException {
        InteruptExample1 example = new InteruptExample1();
        Thread thread = new Thread(example, "interupt-thread");
        LOG.info("start thread");
        thread.start();
        
        Thread.sleep(3000);
        LOG.info("ask thread to stop");
        //设置中断信号量
        example.setStop(true);
        Thread.sleep(3000);
        LOG.info("stop application");

    }

    @Override
    public void run() {
        //每1s检测一次中断信号量
        while(!stop) {
            LOG.info("thread is running");
            long time = System.currentTimeMillis();
            /*
             * 使用while循环模拟 sleep 方法，这里不要使用sleep，否则在阻塞时会 抛
             * InterruptedException异常而退出循环，这样while检测stop条件就不会执行，
             * 失去了意义。
             */
            while(System.currentTimeMillis() - time < 1000) {
            }
        }
        LOG.info("thread exit under request");
        
    }
    

}
