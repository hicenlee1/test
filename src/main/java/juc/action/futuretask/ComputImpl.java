package juc.action.futuretask;

import java.util.concurrent.TimeUnit;

public class ComputImpl implements Computable<Integer, Integer> {
    @Override
    public Integer compute(Integer integer) {
        Integer result = 0;
        try {
            System.out.println(Thread.currentThread().getName() + " interrupting...");
            TimeUnit.SECONDS.sleep(5);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Integer(integer);
    }
}
