package jksj.concurrent.shua2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class Chapter31 {

    public static void main(String[] args) {
        Chapter31 client = new Chapter31();
        client.handleRequest();
    }
    void handleRequest() {
        int id = 0;
        Message msg = new Message(id, "");
        GuardedSuspension<Message> go = GuardedSuspension.create(id);

        send(msg, new Callback<Message>() {
            @Override
            public void onComplete(Message returnMsg) {
                go.fireEvent(returnMsg.getId(), returnMsg);
            }
        });
        long begin = System.currentTimeMillis();
        Message returnMsg = go.get(t -> t!=null);
        long end = System.currentTimeMillis();
        System.out.println("return msg =" + returnMsg+ ",execute time:" + (end-begin)/1000);
    }


    void send(Message msg, Callback<Message> callback) {
        //主线程发送消息
        //模拟回调消息线程
        new Thread(
                new Runnable() {

                    @Override
                    public void run() {
                        try {
//                            TimeUnit.SECONDS.sleep(1);
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Message returnMessage = new Message(msg.getId(), "rturn message");
                        callback.onComplete(returnMessage);
                    }
                }
        ).start();
    }
}

class Message{
    int id;
    Object msg;
    Message(int id, Object msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString(){
        if (msg == null) {
            return "empty message";
        } else {
            return "id:" + id + ",msg:" + msg;
        }
    }
}


class GuardedSuspension<T> {
    T object;


    Lock lock = new ReentrantLock();
    Condition done = lock.newCondition();
    int time = 2;

    static Map<Object, GuardedSuspension> goes = new ConcurrentHashMap<>();

    static <K> GuardedSuspension create(K key) {
        GuardedSuspension go = new GuardedSuspension();
        goes.put(key, go);
        return go;
    }

    static <K, T>  void fireEvent(K key, T obj) {
        GuardedSuspension go = goes.remove(key);
        if (go != null) {
            go.onChanged(obj);
        }
    }

    void onChanged(T obj) {
        lock.lock();
        try {
            this.object = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

    T get(Predicate<T> p) {
        lock.lock();
        try {
            while(!p.test(object)) {
//                done.await(time, TimeUnit.SECONDS);

                //done.await 返回值
                //false 表示超时
                //true表示在超时时间之前返回
                if (!done.await(time, TimeUnit.SECONDS)) {
                    return null;
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return object;
    }

}

interface Callback<T> {
    void onComplete(T object);
}
