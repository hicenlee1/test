package jksj.concurrent.shua2;

/**
 * JAVA 内存模型 解决 可见性  和  有序性问题
 * JAVA 解决 可见性问题和有序性问题的手段：
 * 关键字：  volatile  final   synchronize （禁用缓存和 编译优化）
 * 内存模型：  happens-before 原则  （前一个操作对后一个操作是可见的）
 * 1. 程序的顺序性原则  在一段程序中，前面的操作  happens-before 后面的操作
 * 2. volatile 变量规则：  一个volatile变量的写操作  happens-before 后续对这个变量的读操作
 * 3. 传递性规则： if  a happens-before b &  b happens-before c   then  a happens-before  c
 * 4. 管程锁规则： 对一个锁的解锁  happens-before  后续对这个锁的加锁操作
 * 5. 线程start 规则：线程A 启动线程B后，线程B能够看到线程A 在启动线程B之前的操作
 * 6. 线程join规则：线程A在等待线程B的join,那么线程B返回后，线程A能够看到线程B中的所有操作
 */
public class Chapter2 {

}
