package com.meizu.juc.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class CountDownLatchDemo1 {
    
    static Logger LOG = Logger.getAnonymousLogger();
    /**
     * countdownlatch 和 join 的一个显著区别：
     * join 必须等到线程执行结束，才能执行等待线程
     * countdownlatch 只要发出信号量变更，等待线程就可以执行，不用等到子线程结束
     */
    
    int count = 20;
    
    CountDownLatch complete = new CountDownLatch(count);
    
    public void deliverBook() {
        IntStream.range(0, count).forEach(index -> {
            new Thread(() -> {
                LOG.info(Thread.currentThread().getName() + "开始给学生" + index + "发放书本");
                complete.countDown();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                LOG.info(Thread.currentThread().getName() + "学生" + index + "发放书本线程结束");
                
            }).start();
        });
    }
    
    public void beginClass() throws InterruptedException {
        LOG.info(Thread.currentThread().getName() + "开始准备上课....");
        complete.await();
        LOG.info(Thread.currentThread().getName() + "书本发放完成，开始上课");
    }
    
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo1 demo = new CountDownLatchDemo1();
        demo.deliverBook();
        demo.beginClass();
    }
    
}
