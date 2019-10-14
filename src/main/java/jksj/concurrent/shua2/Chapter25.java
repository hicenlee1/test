package jksj.concurrent.shua2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 批量执行异步任务
 *
 * CompletionService
 */
public class Chapter25 {
    public static void main(String[] args) {
        QueryPrice price = new QueryPrice();
        price.queryPrice();

        ForkingCluster cluster = new ForkingCluster();
        cluster.getFirst();
    }
}

class QueryPrice {

    void queryPrice() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<Integer> cs = new ExecutorCompletionService(executor);
        cs.submit(() -> {
            Thread.sleep(3000);
            return 1;
        });

        cs.submit(() -> {
            Thread.sleep(2000);
            return 2;
        });

        cs.submit(() -> {
            Thread.sleep(1000);
            return 3;
        });

        for (int i = 0; i < 3; i++) {
            try {
                int v = cs.take().get();
                System.out.println("return value: " + v);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}

class ForkingCluster {
    void getFirst() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        CompletionService<Integer> cs = new ExecutorCompletionService<>(es);
        List<Future<Integer>> futures = new ArrayList<>();
        futures.add(cs.submit(() -> {
            Thread.sleep(1000);
            System.out.println("thread-" + Thread.currentThread().getName() + " return ");
            return 1;
        }));

        futures.add(cs.submit(() -> {
            Thread.sleep(2000);
            System.out.println("thread-" + Thread.currentThread().getName() + " return ");
            return 2;
        }));

        futures.add(cs.submit(() -> {
            Thread.sleep(3000);
            System.out.println("thread-" + Thread.currentThread().getName() + " return ");
            return 3;
        }));

        try {
            int v = cs.take().get();
            System.out.println(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (Future<Integer> f : futures) {
            f.cancel(true);
        }
        es.shutdown();

    }
}
