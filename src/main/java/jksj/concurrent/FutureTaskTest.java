package jksj.concurrent;

import com.meizu.util.com.meizu.util.skiplist.Main;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {

    public static void main(String[] args) {
        FutureTask ft2 = new FutureTask(new T2Task());
        FutureTask ft1 = new FutureTask(new T1Task(ft2));
        Thread t1 = new Thread(ft1);
        Thread t2 = new Thread(ft2);

        t1.start();
        t2.start();
    }
}

class T1Task implements Callable<String> {
    FutureTask<String> ft;
    public T1Task(FutureTask<String> ft) {
        this.ft = ft;
    }
    @Override
    public String call() throws Exception {
        System.out.println("T1 洗水壶");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("T1 烧开水");
        TimeUnit.SECONDS.sleep(15);
        String result = ft.get();
        System.out.println("T1 拿到茶叶"+ result);
        System.out.println("T1 泡茶...");
        return "上茶：" + result;
    }
}

class T2Task implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("T2 洗茶壶");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T2 洗茶杯");
        TimeUnit.SECONDS.sleep(2);

        System.out.println("T2 拿茶叶");
        TimeUnit.SECONDS.sleep(1);

        return "龙井";
    }
}
