package com.meizu.juc.flowcontrol.countdownlatch;


import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class CountDownLatchDemo2 {
    static Logger LOG = Logger.getAnonymousLogger();
    /**
     * countdownlatch 和 join 的一个显著区别：
     * join 必须等到线程执行结束，才能执行等待线程
     * countdownlatch 只要发出信号量变更，等待线程就可以执行，不用等到子线程结束
     */
    
    int setenceCount = 10;
    
    CountDownLatch complete = new CountDownLatch(setenceCount);
    
    public void read() throws InterruptedException {
        
        IntStream.range(0, 10).forEach(index -> {
            try {
                LOG.info("老师开始读" + index + "句");
                Thread.sleep(1000);
                complete.countDown();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
        
    }
    
    public void write() {
        int studentCount = 10;
        IntStream.range(0, studentCount).forEach(index -> {
            new Thread(() -> {
                try {
                    LOG.info("学生" + index + "开始听写");
                    complete.await();
                    LOG.info("学生" + index + "开始听写结束");
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }).start();
        });
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo2 client = new CountDownLatchDemo2();
        
        client.write();
        client.read();
        
    }
}
