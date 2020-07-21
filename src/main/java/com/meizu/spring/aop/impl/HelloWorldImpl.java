package com.meizu.spring.aop.impl;

import com.meizu.spring.aop.api.IHelloWorld;

public class HelloWorldImpl implements IHelloWorld {
    @Override
    public void test(String name) {
        System.out.println("name = [" + name + "]");
    }

    @Override
    public String sayHello(String name) {
        System.out.println("say hello:" + name);
        return "say hello:" + name;
    }
}
