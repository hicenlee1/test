package jksj.concurrent.shua2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * JUC 之  读写锁 ReentrantReadWriteLock
 */
public class Chapter17 {

}

class Cache<K, V> {
    final Map<K, V> cache = new HashMap<>();
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final Lock readLock = lock.readLock();
    final Lock writeLock = lock.writeLock();

    V get(K key) {
        V v = null;
        readLock.lock();
        try {
            v = cache.get(key);
        } finally {
            readLock.unlock();
        }
        if (v != null) {
            return v;
        }

        writeLock.lock();
        try {
            if (cache.get(key) == null) {
                //read from db?
                //v = **
                //cache.put(key, v)
            }
        } finally {
            writeLock.unlock();
        }
        return v;
    }

}
