package com.meizu.util.com.meizu.util.skiplist;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

public class Task implements  Runnable {

    private String id;
    private ConcurrentSkipListMap<String, Contact> map;

    public Task (ConcurrentSkipListMap<String, Contact> map, String id) {
        this.id = id;
        this.map = map;
    }

    @Override
    public void run() {
        IntStream.range(1000, 2000).forEach(index -> {
            map.put(id + index, new Contact(id, index));
        });
    }
}
