package com.meizu.util.com.meizu.util.skiplist;

import org.hibernate.engine.jdbc.CharacterStream;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

/**
 * 在非多线程的情况下，应当尽量使用TreeMap。此外对于并发性相对较低的并行程序可以使用Collections.synchronizedSortedMap将TreeMap进行包装，也可以提供较好的效率。
 * 对于高并发程序，应当使用ConcurrentSkipListMap，能够提供更高的并发度。
 *
 *
 * 所以在多线程程序中，如果需要对Map的键值进行排序时，请尽量使用ConcurrentSkipListMap，可能得到更好的并发度。
 * 注意，调用ConcurrentSkipListMap的size时，由于多个线程可以同时对映射表进行操作，所以映射表需要遍历整个链表才能返回元素个数，这个操作是个O(log(n))的操作。
 */
public class Main {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Contact> map = new ConcurrentSkipListMap<String, Contact>();
        int threadSize = 25;
        Thread[] threads = new Thread[25];
        byte count = 0;
        for (char i = 'A'; i < 'Z'; i++) {
            threads[count] = new Thread(new Task(map, String.valueOf(i)));
            threads[count].start();
            count++;
        }

        IntStream.rangeClosed(0, 24).forEach(
                index -> {
                    try {
                        threads[index].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        System.out.println("size: " + map.size());
        System.out.println("first-> key:" + map.firstKey() + "; entity:" + map.firstEntry().getValue());

        ConcurrentNavigableMap subMap = map.subMap("A1996", "B1002");

        Map.Entry<String, Contact> entry = null;
        do {
            entry = subMap.pollFirstEntry();
            if (entry != null) {
                System.out.println("key:" + entry.getKey() + "; value:" + entry.getValue());
            }
        } while (entry != null);

        // 返回此映射的部分视图，其键大于等于 fromKey。
        //ConcurrentNavigableMap<K,V> tailMap(K fromKey)
        //// 返回此映射的部分视图，其键大于（或等于，如果 inclusive 为 true）fromKey。
        //ConcurrentNavigableMap<K,V> tailMap(K fromKey, boolean inclusive)

    }
}
