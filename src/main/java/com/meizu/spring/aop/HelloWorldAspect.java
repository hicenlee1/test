package com.meizu.spring.aop;

public class HelloWorldAspect {

    public void beforeAdvice() {
        System.out.println("before advice");
    }

    public void afterAdvice() {
        System.out.println("after advice");
    }

    public void beforeAdvice(String param) {
        System.out.println("before advice ,param :" + param);
    }
}
