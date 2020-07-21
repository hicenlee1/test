package jksj.concurrent.shua2;

import com.meizu.pattern.interceptor.Response;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Chapter31_Way2 {

    //异步转同步的第二种写法

    public static void main(String[] args) {
        Chapter31_Way2 client = new Chapter31_Way2();
        long begin = System.currentTimeMillis();
        boolean success = client.handleRequest(new Request());
        long end = System.currentTimeMillis();

        System.out.println("success=" + success+ ",time: " + (end - begin) / 1000);
    }


    private Map<Long, Object> mutexes = new ConcurrentHashMap<>();
    private Map<Long, Result> results = new ConcurrentHashMap<>();
    int timeout = 1000;

    public boolean handleRequest(Request request) {

        long id = new Random().nextLong();
        try {

            Messages msg = new Messages();
            msg.setId(id);
            msg.setRequest(request);

            Object mutex = new Object();
            mutexes.put(id, mutex);
            sendMessage(msg, new CallBack<Result>() {
                @Override
                public void onComplete(Result result) {
                    onResult(result);
                }
            });

            synchronized (mutex) {
                mutex.wait(timeout);
                //mutex.wait();
            }

            Result result = results.remove(id);
            if (result != null && result.isSuccess()) {
                return true;
            }


        } catch (InterruptedException e) {
            //超时退出
        } finally {
            mutexes.remove(id);
        }
        return false;

    }

    public void sendMessage(Messages msg, CallBack<Result> c) {
        System.out.println("正在发送消息");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Result resp = new Result();
                    resp.setRequestId(msg.getId());
                    resp.setSuccess(true);
                    //set other attributes
                    c.onComplete(resp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        }).start();
    }


    public void onResult(Result result) {
        long id = result.getRequestId();
        Object m = mutexes.get(id);
        if (m != null) { //可能超时已经删除了
            synchronized (m) {
                m.notifyAll();
                results.put(id, result);
            }
        }

    }
}

class Request {

}

class Messages {
    long id;
    Request request;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

class Result {

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    private long requestId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private boolean success;

    private Object object;
}

interface CallBack<T> {
    void onComplete(T t);
}



