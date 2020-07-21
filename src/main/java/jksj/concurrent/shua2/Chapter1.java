package jksj.concurrent.shua2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发程序出现问题的根源：
 * 缓存 导致的  可见性  问题
 * 线程切换 导致的  原子性  问题
 * 编译优化  导致的  有序性  问题
 */
public class Chapter1 {

    //CPU缓存导致的可见性问题  和 线程切换导致的原子性问题示例
    private static  int count = 0;
    public void add10k() {
        for (int i = 0; i < 20000; i++) {
            count++;
        }
    }

    public static int calc() throws InterruptedException {
        Chapter1 c = new Chapter1();

        Thread t1 = new Thread(() -> c.add10k());
        Thread t2 = new Thread(() -> c.add10k());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println(calc());
        System.out.println(calcMonitor());
        System.out.println(calcAtomic());
    }

    private static  int countMonitor = 0;
    //解决方案1:  管程
    public synchronized void add10kMonitor() {
        for (int i = 0; i < 20000; i++) {
            countMonitor++;
        }
    }

    public static int calcMonitor() throws InterruptedException {
        Chapter1 c = new Chapter1();

        Thread t1 = new Thread(() -> c.add10kMonitor());
        Thread t2 = new Thread(() -> c.add10kMonitor());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        return countMonitor;
    }

    //解决方案2   原子类
    private static AtomicInteger countAtomic = new AtomicInteger(0);
    public void add10kAtomic() {
        for (int i = 0; i < 20000; i++) {
            countAtomic.getAndIncrement();
        }
    }

    public static int calcAtomic() throws InterruptedException {
        Chapter1 c = new Chapter1();

        Thread t1 = new Thread(() -> c.add10kAtomic());
        Thread t2 = new Thread(() -> c.add10kAtomic());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        return countAtomic.get();
    }

}
