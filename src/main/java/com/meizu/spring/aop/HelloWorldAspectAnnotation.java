package com.meizu.spring.aop;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloWorldAspectAnnotation {

    /**
     * 定义公共切点
     * @param param
     */
    @Pointcut(value = "execution(* com.meizu..*.sayHello(..)) && args(param)", argNames = "param")
    public void pointcut(String param) {

    }


    @Before(value = "pointcut(param)", argNames = "param")
    public void beforeAdvice(String param) {
        System.out.println("===========before advice,annotation, param:" + param);
    }

    @After(value = "pointcut(param)", argNames = "param")
    public void afterAdvice(String param) {
        System.out.println("===========after advice,annotation, param:" + param);
    }

    @AfterReturning(returning = "rvt", pointcut = "execution(* com.meizu..*.sayHello(..))")
    public void afterReturn(String rvt) {
        System.out.println("===========after returning获取目标方法返回值："+rvt);
    }

    @Around("execution(* com.meizu..*.sayHello(..))")
    public void round(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("around-before");
        jp.proceed();
        System.out.println("around-end");
    }
}
