package com.meizu.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreDemo implements Runnable {

    Semaphore s;
    
    public SemaphoreDemo(Semaphore s ) {
        this.s = s;
    }
    /**
     * Semaphore 和lock 的区别：
     * lock 必须先 lock ，才能 unlock
     * semaphore 不必 acquire，就能release(也就是说，不是owner也可以释放锁，可以用作死锁检测)
     */
    @Override
    public void run() {
        try {
            s.acquire();
            System.out.println(Thread.currentThread().getName() + " begin");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " finish");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        s.release();
    }
    
    public static void main(String[] args) {
        Semaphore s =new Semaphore(3);
        IntStream.range(0, 5).forEach(index -> {
            new Thread(new SemaphoreDemo(s), "thread" + index).start(); 
        });
    }

}
