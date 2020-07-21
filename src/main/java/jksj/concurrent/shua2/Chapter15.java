package jksj.concurrent.shua2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * dubbo 异步转同步核心代码实现
 */
public class Chapter15 {
    private final Lock lock = new ReentrantLock();
    private Condition done = lock.newCondition();

    private Object response = null;

    private boolean isDone() {
        return response != null;
    }
    //RPC CALLBACK
    public void recieveData(Object response) {
        lock.lock();
        try {
            this.response = response;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Object get(int timeout) throws TimeoutException {
        if (!isDone()) {
            long start = System.currentTimeMillis();
            lock.lock();
            try {
                while (!isDone()) {
                    done.await(timeout, TimeUnit.MILLISECONDS);
                    if (isDone() || System.currentTimeMillis() - start > timeout) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            if (!isDone()) {
                throw new TimeoutException("tiem out");
            }
            return response;
        }
        return response;
    }
}
