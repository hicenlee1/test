package com.meizu.pattern.interceptor;

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(TargetInvocation targetInvocation) {
        if (targetInvocation.getTarget() == null) {
            throw new IllegalArgumentException("target is null");
        }
        System.out.println("logging begin");
        Response response = targetInvocation.invoke();
        System.out.println("logging end");
        return response;
    }
}
