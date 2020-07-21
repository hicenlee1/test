package com.meizu.pattern.singleton;

public class SingletonEnumMode {

    private SingletonEnumMode(){}

    public static SingletonEnumMode getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    private enum SingletonEnum {
        INSTANCE;

        private SingletonEnumMode singleton;

        private SingletonEnum() {
            singleton = new SingletonEnumMode();
        }

        public SingletonEnumMode getInstance() {
            return singleton;
        }
    }
}

