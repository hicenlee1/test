package com.meizu.pattern.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TargetInvocation {
    private List<Interceptor> interceptorList = new ArrayList<>();
    private Iterator<Interceptor> interceptors;
    private Target target;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    private Request request;

    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
        interceptors = interceptorList.iterator();
    }

    public Response invoke() {
        if (interceptors.hasNext()) {
            Interceptor interceptor = interceptors.next();
            //此处是整个算法的关键，会递归调用invoke
            return interceptor.intercept(this);
        } else {
            return target.execute(request);
        }
    }
}
