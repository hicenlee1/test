package jksj.concurrent.shua2;

import java.util.ArrayList;
import java.util.List;

/**
 * chapter5 的 apply方法，在持续时间很久的情况下，会导致CPU长期空转，可以使用
 * wait,notify 方式优化
 */
public class Chapter6 {
}

class Allocate1{

    List<Object> acls = new ArrayList<>();
    public synchronized void apply(Account1 from, Account1 to) {
       if (acls.contains(from) || acls.contains(to)) {
           try {
               wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       } else {
           acls.add(from);
           acls.add(to);
       }
    }

    public synchronized void free(Account1 from, Account1 to) {
        acls.remove(from);
        acls.remove(to);
        notifyAll();
    }
    //sleep 和 wait 区别
    //1. sleep 不会释放锁, wait 会释放锁
    //2. wait 必须在synchronized 代码块中
    //3. sleep 不会有InterruptedException
    //4. wait是object方法   sleep是thread方法

    //相同点：都会释放CPU
}
