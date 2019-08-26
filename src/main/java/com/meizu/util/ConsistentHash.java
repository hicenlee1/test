package com.meizu.util;

import sun.reflect.generics.tree.Tree;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 一致性哈希
 */
public class ConsistentHash {

    private TreeMap<Integer, Map<String, Object>> cacheManager = new TreeMap<Integer, Map<String, Object>>();

    int virtualNode = 5;

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     *
     * @param str
     * @return
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    public ConsistentHash() {

    }

    public ConsistentHash(Collection<String> hosts) {
        hosts.forEach(host -> {
                    this.addHost(host, true);
                }
        );
    }

    public void addHost(final String host, boolean addVirtualNode) {
        System.out.println("add host :" + host + ",hash:" + getHash(host));
        cacheManager.put(getHash(host), new HashMap<String, Object>());
        if (addVirtualNode) {
            IntStream.rangeClosed(1, 5).forEach(
                    index -> {
                        String hostName = host + "virtual" + index;
                        System.out.println("add host :" + hostName + ",hash:" + getHash(hostName));
                        cacheManager.put(getHash(hostName), new HashMap<String, Object>());
                    }
            );
        }
    }

    public void removeHost(final String host) {
        System.out.println("remove host:" + host);
        int hashCode = getHash(host);
        cacheManager.remove(hashCode);
        IntStream.rangeClosed(1, 5).forEach(index ->
            {
                String hostName = host + "virtual" + index;
                System.out.println("remove host :" + hostName);
                cacheManager.remove(getHash(hostName));
            });
    }


    private Map<String, Object> getCache(String key) {
        int hashCode = getHash(key);
        SortedMap<Integer, Map<String, Object>> tailMap = cacheManager.tailMap(hashCode);
        Map<String, Object> cache = null;
        //找不到节点，就使用第一个节点
        if (tailMap.isEmpty()) {
            cache = cacheManager.firstEntry().getValue();
        } else {
            cache = tailMap.get(tailMap.firstKey());
        }
        return cache;
    }


    public void put(String key, Object value) {
        Map<String, Object> cache = getCache(key);
        cache.put(key, value);
    }

    public Object get(String key) {
        Map<String, Object> cache = getCache(key);
        return cache.get(key);
    }

    public static void main(String[] args) {
        ConsistentHash consistentHash = new ConsistentHash(Arrays.asList("10.129.0.1", "10.129.0.2"));

        consistentHash.put("123", "123");
        consistentHash.put("1234", "1234");
        consistentHash.put("1235", "1235");
        consistentHash.put("1236", "1236");
        consistentHash.put("1237", "1237");
        consistentHash.put("1238", "1238");

        System.out.println("before remove node:");
        System.out.println("123:" + consistentHash.get("123"));
        System.out.println("1234:" + consistentHash.get("1234"));
        System.out.println("1235:" + consistentHash.get("1235"));
        System.out.println("1236:" + consistentHash.get("1236"));
        System.out.println("1237:" + consistentHash.get("1237"));
        System.out.println("1238:" + consistentHash.get("1238"));

        consistentHash.removeHost("10.129.0.1");
        System.out.println("after remove node:");
        System.out.println("123:" + consistentHash.get("123"));
        System.out.println("1234:" + consistentHash.get("1234"));
        System.out.println("1235:" + consistentHash.get("1235"));
        System.out.println("1236:" + consistentHash.get("1236"));
        System.out.println("1237:" + consistentHash.get("1237"));
        System.out.println("1238:" + consistentHash.get("1238"));
    }
}
