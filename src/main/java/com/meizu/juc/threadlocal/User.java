package com.meizu.juc.threadlocal;

public class User {
    // threadlocal 使用时候，必须使用static
    private static ThreadLocal<Object> enclosure = new ThreadLocal<>();

    public void set(Object object) {
         enclosure.set(object);
     }
     public Object get() {
        return enclosure.get();
     }
}
