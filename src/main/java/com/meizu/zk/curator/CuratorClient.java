package com.meizu.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CuratorClient {

    private static final String ZK_URL = "127.0.0.1:2181";

    private static final int TIME_OUT = 6000;

    static CuratorFramework curatorFramework;

    static {
        RetryPolicy retryPolicy = new RetryNTimes(3, 1000);
        curatorFramework = CuratorFrameworkFactory.builder().retryPolicy(retryPolicy)
                .connectString(ZK_URL).sessionTimeoutMs(TIME_OUT)
                .build();
        curatorFramework.start();
    }

//    public CuratorFramework getConnection() {
//        RetryPolicy retryPolicy = new RetryNTimes(3, 1000);
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().retryPolicy(retryPolicy)
//                .connectString(ZK_URL).sessionTimeoutMs(TIME_OUT)
//                .build();
//        curatorFramework.start();
//        return curatorFramework;
//    }

    public void deleteAll() throws Exception {
        Stat stat = isExists("/hicen");
        if (stat != null) {
            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/hicen");
        }
    }

    public Stat isExists(String path) throws Exception {
        return curatorFramework.checkExists().forPath(path);
    }


    public void crud() throws Exception {
            curatorFramework.create().creatingParentsIfNeeded().forPath("/hicen/country", "默认创建持久节点".getBytes());

            curatorFramework.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath("/hicen/n2", "创建临时有序节点1".getBytes());
        curatorFramework.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                .forPath("/hicen/n2", "创建临时有序节点2".getBytes());

            curatorFramework.create().creatingParentsIfNeeded()
//                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/hicen/n3/n31/n311", "同时创建父节点".getBytes());

        System.out.println("开始监听调用");
        monitor();
        System.out.println("结束监听调用");

        System.out.println("开始child监听调用");
        childMonitor();
        System.out.println("结束child监听调用");
            //读取节点信息
        byte[] country = curatorFramework.getData().forPath("/hicen/country");
        System.out.println("country => " + new String(country));

        //修改节点
        curatorFramework.setData().forPath("/hicen/country", "修改后的值".getBytes());

        //读取节点信息，并返回节点状态信息
        Stat stat = new Stat();
        byte[] result2 = curatorFramework.getData().storingStatIn(stat).forPath("/hicen/country");
        System.out.println("修改后：");
        System.out.println(stat);
        System.out.println(new String(result2));
    }

    public void transacton() throws Exception {
        //事务处理，一组CRUD操作同声同灭
        Collection<CuratorTransactionResult> results =
                curatorFramework.inTransaction().create().forPath("/hicen/node_xbq1").and()
                .create().forPath("/hicen/node_xbq2").and().commit();

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getResultStat() + "->" + result.getForPath() + "->" + result.getType());
        }
    }

    //监听当前节点
    public void monitor() throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework, "/hicen/country");
        nodeCache.start();
        nodeCache.getListenable().addListener(new ZkNodeCacheListener(nodeCache));
    }

    //监听子节点新增，修改，删除等
    public void childMonitor() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/", false);
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener(new ZkPathChildrenListner());
    }


    public void distrubuteLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
//                    while(true) {
//                        try {
//                            doWork();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }

                    try {
                        doWork();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

    private void doWork() throws Exception {
        //分布式锁的原理：
        //acquire 方法，会在给定路径下面，创建临时  时序  节点的 时序节点。然后和父节点下面的其他借钱进行比较时序
        //如果客户端创建的时序节点的数字后缀最小，就获得锁，函数成功返回
        //如果没有获得，也就是数字后缀不是最小，那么就启动一个watch，监听上一个（排在前面的一个节点）。
        // 主线程使用object.wait进行等待，等待watch触发线程的Notityall
        //一旦上一个节点有事件产生，立马再次触发时序最小节点的判断
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/hicen/distributelock");
        try {
            interProcessMutex.acquire();
            System.out.println("THREADID:" + Thread.currentThread().getId() + " acquire lock");
            Thread.sleep(1000);

        } finally {
            System.out.println("THREADID：" + Thread.currentThread().getId() + " relese lock");
            interProcessMutex.release();
        }
    }

    //leader elector
    //leaderlatch 方式，除非调用close方法，否则不会释放领导权
    public void leaderElectionLatch() throws Exception {
        List<LeaderLatch> leaderLatches =new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LeaderLatch latch = new LeaderLatch(curatorFramework, "/hicen/leader1", "Client #" + i);
            latch.start();
            leaderLatches.add(latch);
        }
        TimeUnit.SECONDS.sleep(2);

        LeaderLatch leader = null;

        for (LeaderLatch leaderLatch : leaderLatches) {
            if (leaderLatch.hasLeadership()) {
                leader = leaderLatch;
            }
            System.out.println(leaderLatch.getId() + "\t" +  leaderLatch.hasLeadership());
        }

        TimeUnit.SECONDS.sleep(2);

        leaderLatches.remove(leader);
        leader.close();

        TimeUnit.SECONDS.sleep(10);
        System.out.println("-------------------------");
        for (LeaderLatch leaderLatch : leaderLatches) {
            System.out.println(leaderLatch.getId() + "\t" + leaderLatch.hasLeadership());
        }

        for (LeaderLatch leaderLatch : leaderLatches) {
            leaderLatch.close();
        }
    }

    //leader选举
    public void leaderElectionListener() throws InterruptedException {
        //LeaderSelector 内部使用分布式锁  InterProcessMutex实现，并且添加一个linsener
        //当获取到锁的时候，回调函数takeLeaderShip 函数执行完后，就调用InterProcessMutex.release 释放锁
        //也就是释放leader

        List<MyLeaderSelectorListenerAdapter> list = new ArrayList<>();
        for (int i = 0;i < 5; i++) {
           MyLeaderSelectorListenerAdapter example = new MyLeaderSelectorListenerAdapter(curatorFramework, "/hicen/leader2", "client #" + i);
            list.add(example);
        }

        TimeUnit.SECONDS.sleep(20);

        for (MyLeaderSelectorListenerAdapter leaderSelectorListenerAdapter : list) {
            leaderSelectorListenerAdapter.close();
        }
    }

    class MyLeaderSelectorListenerAdapter extends LeaderSelectorListenerAdapter {

        private String name;
        private LeaderSelector leaderSelector;

        public MyLeaderSelectorListenerAdapter(CuratorFramework curatorFramework, String path, String name) {
            this.name = name;
            this.leaderSelector = new LeaderSelector(curatorFramework, path, this);
            //保证在释放领导权之后，重新选举
            this.leaderSelector.autoRequeue();
            this.leaderSelector.start();
        }

        public void close() {
            this.leaderSelector.close();
        }

        @Override
        public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
            System.out.println(name + "当选为leader");
            TimeUnit.SECONDS.sleep(5);
            //如果不想释放领导权,这里可以死循环
            System.out.println("relinquish the leader");
        }
    }




    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(
                new Thread((new Runnable() {

                    @Override
                    public void run() {
                        if (curatorFramework != null) {
                            System.out.println("<<<<<<<<<<<<<<<<关闭curator framework>>>>>>>>>>>>>>>>");
                            curatorFramework.close();
                        }
                    }
                }))
        );

        CuratorClient client = new CuratorClient();
        client.deleteAll();
//        client.crud();
//        client.transacton();
//        client.distrubuteLock();
//        client.leaderElectionLatch();
        client.leaderElectionListener();
    }

}
