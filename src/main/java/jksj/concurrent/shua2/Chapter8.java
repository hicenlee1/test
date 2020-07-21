package jksj.concurrent.shua2;

/**
 * 管程：java解决 互斥 + 同步问题的万能钥匙
 * 解决互斥的方案：把变量和变量的操作封装起来
 * 解决同步的方案：条件变量+等待队列
 *
 * java 管程关键字
 * synchronized + wait notify notifyAll
 *
 *
 * wait 使用模式：
 *
 * whlie(条件不满足)
 *    wait
 *
 * 原因：MESA方案，在notify 之后，不会释放锁，仅仅是把线程从条件变量的等待队列，移到入口的等待队列，
 * 然后继续当前线程的执行，直到结束
 * 重新执行的时候，可能条件已经失效
 */
public class Chapter8 {
}
