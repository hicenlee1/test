package com.meizu.pattern.interceptor;

public interface Interceptor {

     Response intercept(TargetInvocation targetInvocation);
}
