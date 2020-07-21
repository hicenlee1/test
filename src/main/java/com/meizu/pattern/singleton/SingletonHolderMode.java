package com.meizu.pattern.singleton;

public class SingletonHolderMode {

    private SingletonHolderMode() {

    }

    /**
     * 类级别的内部类，也就是静态成员式内部类，该内部类与外部类的实例没有绑定关系，而且只有在被调用时才装载，从而实现延迟加载
     * @return
     */
    public static SingletonHolderMode getInstance() {
        return SingletonHolder.instance;
    }

    static class SingletonHolder {
        /**
         * 静态初始化器，由JVM保证线程安全
         */
        private static SingletonHolderMode instance = new SingletonHolderMode();
    }
}
