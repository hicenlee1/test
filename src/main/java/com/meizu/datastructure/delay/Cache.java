package com.meizu.datastructure.delay;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Cache<K, V> {

    static Logger LOG = Logger.getLogger(Cache.class.getName());
     
    
    private DelayQueue<DelayItem<Pair<K, V>>> q =  new DelayQueue<DelayItem<Pair<K, V>>>(); 
    
    private ConcurrentMap<K, V> cacheMap = new ConcurrentHashMap<K, V>();
    
    public Cache() {
        
        
        Thread checkThread = new Thread(() -> {
            daemonCheck();
        });
        
        checkThread.setDaemon(true);
        checkThread.setName("check-thread");
        checkThread.start();
    }
    
    
    public void put(K k, V v, long timeout, TimeUnit unit) {
        V oldValue = cacheMap.put(k, v);
        if (oldValue != null) {
            LOG.info("remove exists object " + k + ", and reset timeout");
            q.remove(new DelayItem<Pair<K, V>>(new Pair<K, V>(k, oldValue)));
        }
        
        q.offer(new DelayItem<Pair<K,V>>(new Pair<K, V>(k, v), timeout, unit));
    }
    
    public V get(K key) {
        return cacheMap.get(key);
    }
    
    public void daemonCheck() {
        for(;;) {
                
                try {
                    DelayItem<Pair<K, V>> delayItem = q.take();
                    if (delayItem != null) {
                        Pair<K, V> pair = delayItem.getItem();
                        cacheMap.remove(pair.key, pair.value);
                        LOG.info("remove expire cache object " + pair.key);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Cache<String, Integer> cache = new Cache<String, Integer>();
        cache.put("zhangsan", 10, 5, TimeUnit.SECONDS);
        cache.put("lisi", 20, 10, TimeUnit.SECONDS);
        
        
        LOG.info(String.valueOf(cache.get("zhangsan")));
        Thread.sleep(2000);
        LOG.info(String.valueOf(cache.get("zhangsan")));
        Thread.sleep(3000);
        LOG.info(String.valueOf(cache.get("zhangsan")));
        LOG.info(String.valueOf(cache.get("lisi")));
        cache.put("lisi", 30, 3, TimeUnit.SECONDS);
        Thread.sleep(2000);
        LOG.info(String.valueOf(cache.get("lisi")));
        Thread.sleep(2000);
        LOG.info(String.valueOf(cache.get("lisi")));
        LOG.info("end");
    }
    
}
