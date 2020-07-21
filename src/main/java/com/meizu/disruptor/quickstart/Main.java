package com.meizu.disruptor.quickstart;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        OrderEventFactory factory = new OrderEventFactory();
        int rangeBufferSize = 4;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>
    }
}
