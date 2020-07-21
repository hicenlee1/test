package com.meizu.juc.loop.print100;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程，循环输出 1，2，3
 */
public class LoopPrint100 {
    public static void main(String[] args) {
//        LoopPrint100LockImpl lockImpl = new LoopPrint100LockImpl();
//        lockImpl.print100();
        SemaphoreImpl semaphoreImle = new SemaphoreImpl();
        semaphoreImle.print100();


//        System.out.println("123");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("456");
    }

}

/**
 * lock 实现版本
 */
class LoopPrint100LockImpl {
    Lock lock = new ReentrantLock();
    Condition c0 = lock.newCondition();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    int count = 0;
    int terminate = 4;


    public void print100() {

        Thread t0 = new Thread(() -> {
            lock.lock();
            try {
                while (count <= terminate) {
                    while (count % 3 != 0) {
                        try {
                            c0.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //这里一定需要重新判断是否 < terminate，否则可能进入的时候满足条件，但是唤醒时候已经不满足
                    if (count <= terminate) {
                        System.out.println(Thread.currentThread().getName() + ":" + count);
                    }
                    //count++ 和 signal 不能放在if 条件中，否则可能导致唤醒后续线程后，因为没有+1，仍然阻塞在 c1.wait,但是当前线程已经
                    //结束while 循环，无法通知,线程无法结束
                    count++;
                    c1.signal();
                }
            } finally {
                lock.unlock();
            }
        }, "TRHEAD-0");

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                while (count <= terminate) {
                    while (count %3 != 1) {
                        try {
                            c1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //这里一定需要重新判断是否 < terminate，否则可能进入的时候满足条件，但是唤醒时候已经不满足
                    if (count <= terminate) {
                        System.out.println(Thread.currentThread().getName() + ":" + count);
                    }
                    count++;
                    c2.signal();
                }
            } finally {
                lock.unlock();
            }
        }, "THREAD-1");

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                while (count <= terminate) {
                    while (count % 3 != 2) {
                        try {
                            c2.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //这里一定需要重新判断是否 < terminate，否则可能进入的时候满足条件，但是唤醒时候已经不满足
                    if (count <= terminate) {
                        System.out.println(Thread.currentThread().getName() + ":" + count);
                    }
                    count++;
                    c0.signal();
                }
            } finally {
                lock.unlock();
            }
        }, "THREAD-2");
        t0.start();
        t1.start();
        t2.start();
    }
}


class SemaphoreImpl {
    Semaphore s0 = new Semaphore(1);
    Semaphore s1 = new Semaphore(0);
    Semaphore s2 = new Semaphore(0);
    int count = 0;
    int terminate = 100;

    public void print100() {
        Thread t0 = new Thread(() -> {
            while (count <= terminate) {
                try {
                    s0.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count <= terminate) {
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                }
                count++;
                //特别注意这里：虽然s1 初始值为0，但是仍然可以release.
                s1.release();

            }
        }, "SEMA-THREAD-0");

        Thread t1 = new Thread(() -> {
            while (count <= terminate) {
                try {
                    s1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count <= terminate) {
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                }
                count++;
                s2.release();
            }
        }, "SEMA-THREAD-1");

        Thread t2 = new Thread(() -> {
            while (count <= terminate) {
                try {
                    s2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count <= terminate) {
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                }
                count++;
                s0.release();
            }
        },"SEMA-THREAD-2");
        t0.start();
        t1.start();
        t2.start();
    }

}