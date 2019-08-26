package com.meizu.spring.aop.impl;

import com.meizu.spring.aop.api.IHelloWorld;

public class HelloWorldImpl2 implements IHelloWorld {
    @Override
    public void test(String name) {
        System.out.println("instance 2");
    }

    @Override
    public String sayHello(String name) {
        System.out.println("say hello instance2");
        return "say hello:" + name;
    }


}
