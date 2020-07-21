package jksj.concurrent.shua2;

import java.util.concurrent.locks.StampedLock;

/**
 * 升级版读写锁：java8支持
 * 支持乐观读 （非锁）
 * 比 ReentrantReadWriteLock 性能优越的地方在于：
 * ReentrantReadWriteLock 在读取的时候，所有写操作必须全部blokc
 * StampedLock 在读取的时候，可以支持一个线程进行写入
 */
public class Chapter18 {
}

class Point {
    private int x,y;
    final StampedLock sl = new StampedLock();

    double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        int curX = x, curY = y;
        if (!sl.validate(stamp)) {
            //升级为  悲观读锁
            stamp = sl.readLock();
            try {
                curX = x;
                curY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(x*x + y*y);
    }
}
