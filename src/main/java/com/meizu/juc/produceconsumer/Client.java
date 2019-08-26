package com.meizu.juc.produceconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Client {
    
    public static void main(String[] args) {
        BlockingQueue<?> q = new ArrayBlockingQueue<>(100);
        ExecutorService service = Executors.newCachedThreadPool(new ProConThreadFactory("pro-consu-model"));
        IntStream.range(0, 1).forEach(index -> {
            service.execute(new Producer(q));
        });
        
        IntStream.range(0, 2).forEach(index -> {
            service.execute(new Consumer(q));
        });
        
    }
}
