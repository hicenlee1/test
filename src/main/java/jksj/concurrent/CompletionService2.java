package jksj.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 只要有一个成功，就返回，并且停止其他任务
 */
public class CompletionService2 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        Future<Integer> f1 = cs.submit(() -> {
            throw new RuntimeException("error");
//            int interval = new Random().nextInt(2000);
//            System.out.println(Thread.currentThread().getName() + "begin sleep " + interval);
//            try {
//                TimeUnit.MILLISECONDS.sleep(interval);
//            } catch (InterruptedException e) {
//                System.out.println("interupting");
//                return -1;
//            }
//            System.out.println(Thread.currentThread().getName() + "end sleep " + interval);
//            return interval;
        });

        Future<Integer> f2 = cs.submit(() -> {
            int interval = new Random().nextInt(2000);
            System.out.println(Thread.currentThread().getName() + "begin sleep " + interval);
            try {
                TimeUnit.MILLISECONDS.sleep(interval);
            } catch (InterruptedException e) {
                System.out.println("interupting");
                return -1;
            }
            System.out.println(Thread.currentThread().getName() + "end sleep " + interval);
            return interval;
        });

        Future<Integer> f3 = cs.submit(() -> {
            int interval = new Random().nextInt(2000);
            System.out.println(Thread.currentThread().getName() + "begin sleep " + interval);
            try {
                TimeUnit.MILLISECONDS.sleep(interval);
            } catch (InterruptedException e) {
                System.out.println("interupting");
                return -1;
            }
            System.out.println(Thread.currentThread().getName() + "end sleep " + interval);
            return interval;
        });

        futures.add(f1);
        futures.add(f2);
        futures.add(f3);

        Integer result = 0;

        try {

            for (int i = 0; i < 3; i++) {
                try {
                    result = cs.take().get();
                    if (result != null) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            //停止在运行的任务
            for (Future<Integer> f : futures) {
                f.cancel(true);
            }
        }


        System.out.println("result = " + result);
    }
}
