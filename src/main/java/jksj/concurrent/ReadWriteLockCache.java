package jksj.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockCache<K, V> {

    final Map<K, V> cache = new HashMap<>();

    final ReadWriteLock lock = new ReentrantReadWriteLock();
    final Lock rl = lock.readLock();
    final Lock wl = lock.writeLock();


    public V get(K key) {
        V v = null;
        rl.lock();
        try {
            v = cache.get(key);
        } finally {
            rl.unlock();
        }
        if (v == null) {
            wl.lock();
            try {
                if (v == null) {
                    //read from db?
                }
            } finally {
                wl.unlock();
            }
        }
        return v;
    }

    public V put(K k, V v) {
        wl.lock();
        try {
            return cache.put(k, v);
        } finally {
            wl.unlock();
        }
    }
}
