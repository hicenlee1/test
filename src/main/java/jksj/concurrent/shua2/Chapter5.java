package jksj.concurrent.shua2;

import java.util.ArrayList;
import java.util.List;

/**
 * chapter3 优化
 */
public class Chapter5 {
}

class Account1 {
    private int id;
    private int balance;

    /**
     * 此方案存在问题：可能死锁。
     * 死锁产生的四个条件：
     * 1. 互斥   资源x ,y 只能被一次线程占用
     * 2. 占有且等待  线程1已经获得资源，在等待资源y
     * 3. 不可抢占 其他线程不能抢占线程1持有的资源
     * 4. 循环等待 线程1等待线程2的资源，线程2等待线程1的资源
     * @param target
     * @param amt
     */
    public void transfer(Account1 target, int amt) {
        synchronized (this) {
            synchronized (target) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }

    //解决死锁，破坏其中一个条件即可
    //条件1，无法破坏
    //条件2，占有且等待，只需要一次申请所有的资源即可
    Allocate allocate = new Allocate();
    public void transferVoidDeadlockCondition2(Account1 target, int amt) {

        while (!allocate.apply(this, target)) {
            ;
        }

        try {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        } finally {
            allocate.free(this, target);
        }
    }

    //条件3，不可抢占。  需要JUC 支持
    //条件4， 循环等待。  只需要按照顺序获取锁即可
    public void transferVoidDeadlockCondition4(Account1 target, int amt) {
        Account1 left = this;
        Account1 right = target;
        if (right.id < left.id) {
            right = this;
            left = target;
        }
        synchronized (left) {
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }

}

class Allocate {
    private List<Object> als = new ArrayList<>();
    //一次申请所有的资源

    synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
            return true;
        }
    }

    synchronized void free(Object from , Object to) {
        als.remove(from);
        als.remove(to);
    }

}
