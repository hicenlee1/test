package com.meizu.pattern.interceptor;

public class InterceptorDemo {
    public static void main(String[] args) {
        TargetInvocation targetInvocation = new TargetInvocation();
        targetInvocation.addInterceptor(new LogInterceptor());
        targetInvocation.addInterceptor(new AuditInterceptor());
        targetInvocation.setRequest(new Request());
        targetInvocation.setTarget(request -> {
            System.out.println("execute biz logic");
            return new Response();});

        targetInvocation.invoke();
    }
}
