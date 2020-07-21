package jksj.concurrent.shua2;

/**
 * 创建多少个线程合适：
 * 如果是CPU密集型任务，创建  core +1 个线程即可
 * 如果是IO 密集型任务，创建  CPU core * ((IO执行时间/CPU执行时间) + 1)
 */
public class Chapter10 {
}
