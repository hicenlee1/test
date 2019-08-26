package jksj.concurrent;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SemaphoreObjectPool<T, R> {

    public List<T> pool;

    Semaphore sem;

    public SemaphoreObjectPool(int size, T t) {
        pool = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        sem = new Semaphore(size);
    }

    R exec(Function<T, R> func) {
        T t = null;
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            return null;
        }


        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            sem.release();
        }
    }

    public static void main(String[] args) {
        SemaphoreObjectPool<Long, String> objectPool = new SemaphoreObjectPool(5, 2L);
        objectPool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }

}
