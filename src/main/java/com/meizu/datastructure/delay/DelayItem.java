package com.meizu.datastructure.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayItem<T> implements Delayed {
    
    private T submit;
    
    private long timeout;
    
    private long expire;
    
    
    public DelayItem(T submit) {
        this.submit = submit;
    }
    
    public DelayItem(T submit, long timeout, TimeUnit unit) {
        this.submit = submit;
        this.timeout = timeout;
        this.expire = System.nanoTime() + unit.toNanos(timeout);
    }

    public T getItem() {
        return submit;
    }
    
    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        
        
        return diff == 0L ? 0 : (diff < 0L ? -1 : 1);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // TODO Auto-generated method stub
        return this.expire - System.nanoTime();
    }
    

}
