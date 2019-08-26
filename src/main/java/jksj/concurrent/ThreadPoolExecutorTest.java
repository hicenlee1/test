package jksj.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 20; i++) {
            final int index = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(10000);
                    System.out.println("after sleep 1000, print:" + index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

}
