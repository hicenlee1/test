package com.meizu.juc.interupt;
/**
 * interupt 中断阻塞状态进程
 * @author haiyang1
 *
 */

import java.util.logging.Logger;

public class InteruptExample3 implements Runnable {

    private static Logger LOG = Logger.getAnonymousLogger();
    
    
    public static void main(String[] args) throws InterruptedException {
        InteruptExample3 example = new InteruptExample3();
        Thread thread = new Thread(example, "interupt-thread");
        LOG.info("start thread");
        thread.start();
        
        Thread.sleep(3000);
        LOG.info("ask thread to stop");
        //Thread.interrupt()方法不会中断一个正在运行的线程。
        //这一方法实际上完成的是，设置线程的中断标示位，在线程受到阻塞的地方（如调用sleep、wait、join等地方）
        //抛出一个异常InterruptedException，并且中断状态也将被清除，这样线程就得以退出阻塞的状态。下面是具体实现：
        thread.interrupt();
        Thread.sleep(3000);
        LOG.info("stop application");

    }

    @Override
    public void run() {
        //每1s检测一次中断信号量
        while(!Thread.currentThread().isInterrupted()) {
            LOG.info("thread is running");
            try {
                /*
                 * 如果线程阻塞，将不会去检查中断信号量stop变量，所 以thread.interrupt()
                 * 会使阻塞线程从阻塞的地方抛出异常，让阻塞线程从阻塞状态逃离出来，并
                 * 进行异常块进行 相应的处理
                 */
                
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.info("thread interupted...");
                /*
                 * 如果线程在调用 Object.wait()方法，或者该类的 join() 、sleep()方法
                 * 过程中受阻，则其中断状态将被清除
                 */
                
                //中不中断由自己决定，如果需要真真中断线程，则需要重新设置中断位，如果
                //不需要，则不用调用
                Thread.currentThread().interrupt();
            }
        }
        LOG.info("thread exit under request");
        
    }
    

}
