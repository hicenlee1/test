package jksj.concurrent;

import java.util.Vector;
import java.util.concurrent.*;

/**
 * CountDownLatch 和 CyclicBarrier 是 Java 并发包提供的两个非常易用的线程同步工具类，这两个
 * 工具类用法的区别在这里还是有必要再强调一下：
 *
 * CountDownLatch 主要用来解决一个线程等待
 * 多个线程的场景，可以类比旅游团团长要等待所有的游客到齐才能去下一个景点；而
 *
 *
 * CyclicBarrier 是一组线程之间互相等待，更像是几个驴友之间不离不弃。
 *
 * 除此之外
 * CountDownLatch 的计数器是不能循环利用的，也就是说一旦计数器减到 0，再有线程调用
 * await()，该线程会直接通过。
 *
 * 但CyclicBarrier 的计数器是可以循环利用的，而且具备自动重置的
 * 功能，一旦计数器减到 0 会自动重置到你设置的初始值。
 *
 *
 * 除此之外，CyclicBarrier 还可以设置回
 * 调函数，可以说是功能丰富。
 */
public class CountdownLatchAndCylicBarrierTest {

    public static void main(String[] args) {
        Executor e = Executors.newFixedThreadPool(2);
        while(true) {
            CountDownLatch latch = new CountDownLatch(2);
            e.execute(() -> {
                //List dorders = getDOrders();
                latch.countDown();
            });

            e.execute(() -> {
                //List porders = getPOrders();
                latch.countDown();
            });

            try {
                latch.await();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //diff = check(dorders, porders);
        }
    }




    Vector porders = new Vector();
    Vector dorders = new Vector();

    void check() {
        porders.remove(0);
        dorders.remove(0);
        //check(porders, dorders);
    }

    public void cylicBarrier() {


        Executor callBackExecutor = Executors.newSingleThreadExecutor();
        CyclicBarrier barrier = new CyclicBarrier(2,()-> {
            callBackExecutor.execute(() -> {
                check();
            });
        });

        Executor runExecutor = Executors.newFixedThreadPool(2);
        while(true) {
            runExecutor.execute(() -> {
                //List dorders = getDOrders();
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            runExecutor.execute(() -> {
                //List porders = getPOrders();
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

    }

}
