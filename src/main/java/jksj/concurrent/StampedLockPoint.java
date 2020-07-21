package jksj.concurrent;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock 使用注意事项
 * 对于读多写少的场景 StampedLock 性能很好，简单的应用场景基本上可以替代 ReadWriteLock，
 * 但是StampedLock 的功能仅仅是 ReadWriteLock 的子集，在使用的时候，还是有几个地方需要注
 * 意一下。
 * StampedLock 在命名上并没有增加 Reentrant，想必你已经猜测到 StampedLock 应该是<bold>不可重入</bold>
 * 的。事实上，的确是这样的，StampedLock 不支持重入。这个是在使用中必须要特别注意的。
 * 另外，StampedLock 的悲观读锁、写锁都<bold>不支持条件变量</bold>，这个也需要你注意。
 * 还有一点需要特别注意，那就是：如果线程阻塞在 StampedLock 的 readLock() 或者 writeLock()
 * 上时，此时调用该阻塞线程的 interrupt() 方法，会导致 CPU 飙升。例如下面的代码中，线程 T1
 * 获取写锁之后将自己阻塞，线程 T2 尝试获取悲观读锁，也会阻塞；如果此时调用线程 T2 的
 * interrupt() 方法来中断线程 T2 的话，你会发现线程 T2 所在 CPU 会飙升到 100%
 *
 *
 *
 * 所以，使用 StampedLock 一定不要调用中断操作，如果需要支持中断功能，一定使用可中断的
 * 悲观读锁 readLockInterruptibly() 和写锁 writeLockInterruptibly()。这个规则一定要记清楚。
 */
public class StampedLockPoint<K, V> {
    private int x, y;
    final StampedLock sl = new StampedLock();

    public double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        //读入局部变量，读取的过程中，数据可能被修改
        int curX = x;
        int curY = y;
        //判断读取的过程中，是否有写操作，如果有，sl.validate 返回False
        if (!sl.validate(stamp)) {
            //升级为悲观读锁
            stamp = sl.writeLock();
            try {
                curX = x;
                curY = y;
            } finally {
                sl.unlock(stamp);
            }
        }
        return Math.sqrt(curX * curX + curY * curY);
    }

    public void put(K k, V v) {
        long stamp = sl.writeLock();
        try {
            //写条件变量
        } finally {
            sl.unlock(stamp);
        }
    }
}