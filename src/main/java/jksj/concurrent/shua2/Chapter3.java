package jksj.concurrent.shua2;

/**
 * java 解决原子性问题  的方案之一：
 * 加锁  synchronized
 * synchronized ：互斥 ＋ JVM模型提供的 可见性
 * 常见问题：不是同一把锁
 *
 */
public class Chapter3 {
}

//一把锁 保护多个资源
class Account {
    private int balance;

    public void transfer(Account target, int amt) {
        synchronized (Account.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }
}

