package com.meizu.pattern.singleton;

public class SingletonDoubleCheck {

    private SingletonDoubleCheck() {

    }

    private static volatile SingletonDoubleCheck instance = null;



    public static SingletonDoubleCheck getInstance() {
        if (instance == null) {
            synchronized (SingletonDoubleCheck.class) {
                if (instance == null) {
                    instance = new SingletonDoubleCheck();
                }
            }
        }
        return instance;
    }
}
