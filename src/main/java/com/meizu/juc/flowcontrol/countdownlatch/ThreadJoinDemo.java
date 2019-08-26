package com.meizu.juc.flowcontrol.countdownlatch;

import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ThreadJoinDemo {
    static Logger LOG = Logger.getAnonymousLogger();
    /**
     * countdownlatch 和 join 的一个显著区别：
     * join 必须等到线程执行结束，才能执行等待线程
     * countdownlatch 只要发出信号量变更，等待线程就可以执行，不用等到子线程结束
     */
    
    int count = 20;
    Thread[] threads = new Thread[count];
    
    public void deliverBook() {
        IntStream.range(0, count).forEach(index -> {
            Thread t = 
                new Thread(() -> {
                    LOG.info(Thread.currentThread().getName() + "开始给学生" + index + "发放书本");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    LOG.info(Thread.currentThread().getName() + "学生" + index + "发放书本线程结束");
                    
                });
            t.start();
            threads[index] = t;
        });
    }
    
    public void beginClass() throws InterruptedException {
        LOG.info(Thread.currentThread().getName() + "开始准备上课....");
        for (Thread t : threads) {
            t.join();
        }
        LOG.info(Thread.currentThread().getName() + "书本发放完成，开始上课");
    }
    
    public static void main(String[] args) throws InterruptedException {
        ThreadJoinDemo demo = new ThreadJoinDemo();
        demo.deliverBook();
        demo.beginClass();
    }
}
