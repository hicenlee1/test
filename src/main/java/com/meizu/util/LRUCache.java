package com.meizu.util;

import java.util.LinkedHashMap;

public class LRUCache<K, V> extends LinkedHashMap<K, V>{
    
    private int capacity;
    
    public LRUCache(int capacity) {
        super(capacity, .75f, true);
        this.capacity = capacity; 
    }
    
    @Override
    public boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return this.size() > capacity;
    }
    
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<String, String>(5);
        
        cache.put("key1", "1");
        cache.put("key2", "2");
        cache.put("key3", "3");
        cache.put("key4", "4");
        cache.put("key5", "5");
        cache.get("key2");
        cache.put("key6", "6");
        
        System.out.println(cache);
        
    }
}
