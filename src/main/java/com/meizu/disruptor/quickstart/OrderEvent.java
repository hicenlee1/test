package com.meizu.disruptor.quickstart;

public class OrderEvent {
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    private long value;

}
