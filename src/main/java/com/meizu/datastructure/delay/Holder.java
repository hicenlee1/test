package com.meizu.datastructure.delay;

public class Holder<T> {
    private T t;
    
    public Holder(T t) {
        this.t = t;
    }
    
    public T getInstance() {
        return this.t;
    }
}
