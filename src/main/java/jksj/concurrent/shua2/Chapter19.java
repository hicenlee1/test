package jksj.concurrent.shua2;

import java.util.Vector;
import java.util.concurrent.*;

/**
 * CountDonwLatch 和 CylicBarrier 使用
 * CountDownLatch 使用场景：一个线程等待多个线程完成
 * CylicBarrier : 是CountDownLatch的增强，当计数清0后，会自动恢复成原始值，因为可以重复使用
 */
public class Chapter19 {
}

class CountDownLatchTest {

    void check() {
        ExecutorService s = Executors.newFixedThreadPool(2);
        while (true) {
            CountDownLatch latch = new CountDownLatch(2);
            s.submit(() -> {
                //porder = getPorder();
                latch.countDown();
            });

            s.submit(() -> {
                //dorder = getDorder();
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                break;
            }
            //check(porder, dorder);

        }
    }
}

class CylicBarrierTest {
    Vector porders = new Vector<>();
    Vector dorders = new Vector();
    Executor e = Executors.newFixedThreadPool(1);
    CyclicBarrier barrier = new CyclicBarrier(2, () -> {
        e.execute(() -> {
            porders.remove(0);
            dorders.remove(0);
            //check(order, dorder);
        });
    });

    void checkAll() {
        Executor e2 = Executors.newFixedThreadPool(2);
        e2.execute(() -> {
//            porders.add(getPorder());
            try {
                barrier.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        });

        e2.execute(() -> {
//            dorders.add(getDorder());
            try {
                barrier.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        });
    }
}



