package jksj.concurrent;

import java.util.concurrent.*;

public class CompletionServiceTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<Integer> service = new ExecutorCompletionService(executor, new ArrayBlockingQueue<Future<Integer>>(10));

        service.submit(() -> {
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() + " After sleeping");
            return 3;
        });

        service.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + " After sleeping");
            return 2;
        });

        service.submit(() -> {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " After sleeping");
            return 1;
        });

        for (int i = 0; i < 3; i++) {
            try {
                int v = service.take().get();
                System.out.println(v);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
