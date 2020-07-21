package jksj.concurrent.shua2;

/**
 * 线程生命周期：
 * NEW
 *  |
 *  RUNNABLE-----BLOCK、WATING、TIMED_WAITING
 *  |
 *  TERMINATED
 *
 *  new Thread()  ----NEW
 *  Thread.start  ----NEW -> Runnable
 *  synchronized 等待锁   Runnable-> Block
 *  synchronized 等到锁   Block-> Runnable
 *
 *  object.wait          Runnable -> Waiting
 *  Thread.join          Runnable -> Waiting
 *  LockSupport.park     Runnable -> Waiting
 *
 *  Thread.sleep(long)   Runnable ->TIMED_WAITING
 *  Thread.join(long)    Runnalbe ->Timed_waiting
 *  LockSupport.parkNanos Runnable->Timed_waiting
 *  LockSupport.parkUntil(dealline)  Runnable->waiting
 *
 *
 *  线程执行结束，或者跑出异常   Runnable ->Terminated
 *
 *  手工终止线程：不能使用stop (会强制停用线程，可能导致synchronized 的锁没有释放）
 *              可以通过 interrupte 方式结束线程
 *
 */

public class Chapter9 {
    //结束线程示例
    void endThread() {
        while(true) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //重新标记  终端标记
                Thread.interrupted();
            }
        }
    }
}
