package jksj.concurrent.shua2;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Semaphore 使用场景：
 * 1. 类似lock ，互斥场景使用（操作系统中的基本语义）
 * 2. JAVA 中提供的主要原因：
 *    允许多个线程同时访问临界区（使用场景：对象池）
 *
 */
public class Chapter16 {

    public static void main(String[] args) {
        ObjectPool<String, String> pool = new ObjectPool<>(10, "pool");

        ExecutorService executor = Executors.newCachedThreadPool();
        IntStream.rangeClosed(1, 20).forEach(x -> {
            executor.submit( () -> {
                pool.exec(p -> {
                    String v = Thread.currentThread().getName() + ":" + p + new Random().nextInt();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(v);
                    return v;
                });
            });
        });
        executor.shutdown();
    }
}

class ObjectPool<T, R> {
    final List<T> pool;
    Semaphore sem;
    ObjectPool(int size, T t) {
        sem = new Semaphore(size);
        //注意：这里不能使用arraylist,必须使用线程安全容器
        //虽然信号量控制了同时访问的线程数量，但是无法控制 线程是否并发，可能存在多个线程同时add 或者  remove 的情况，所以需要同步
        pool = new Vector<>(size);
//        for (int i = 0; i < size; i++) {
//            pool.add(t);
//        }
        IntStream.rangeClosed(1, size).forEach(x -> pool.add(t));
    }

    R exec(Function<T, R> func) {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        T t = null;
        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            sem.release();
        }

    }
}
