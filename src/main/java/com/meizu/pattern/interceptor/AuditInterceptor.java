package com.meizu.pattern.interceptor;

public class AuditInterceptor implements Interceptor {
    @Override
    public Response intercept(TargetInvocation targetInvocation) {
        if (targetInvocation.getTarget() == null) {
            throw new IllegalArgumentException("target is null");
        }
        System.out.println("before audit");
        Response response = targetInvocation.invoke();
        System.out.println("after audit");
        return response;
    }
}
