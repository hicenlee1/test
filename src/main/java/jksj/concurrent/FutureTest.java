package jksj.concurrent;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.util.concurrent.*;

public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("time1:" + System.currentTimeMillis());
        ExecutorService e = Executors.newFixedThreadPool(1);
        Future f1 = e.submit(new RunImpl());
        Object o = f1.get();
        System.out.println("runnable return :" + o);
        System.out.println("time2:" + System.currentTimeMillis());
        Future<String> f2 = e.submit(new CallImpl());
        String r = f2.get();
        System.out.println("callable return :" + r);
        System.out.println("time3:" + System.currentTimeMillis());

        User u = new User();
        Future<User> f3 = e.submit(new RunImplWithParam(u), u);
        System.out.println(u == f3.get());
        System.out.println(u.getName());
    }
}

class CallImpl implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        System.out.println("calling");
        return "return calling";
    }
}

class RunImpl implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("running");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;
}

class RunImplWithParam implements Runnable {
    User u;
    public RunImplWithParam(User u) {
        this.u = u;
    }
    @Override
    public void run() {
        u.setName("Tom");
        u.setAge(10);
    }
}