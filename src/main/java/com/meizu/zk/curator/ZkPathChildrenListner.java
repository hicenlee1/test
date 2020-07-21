package com.meizu.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

public class ZkPathChildrenListner implements PathChildrenCacheListener {

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
        System.out.println("=======子节点监听事件=========");
        System.out.println("事件类型：" + pathChildrenCacheEvent.getType());
        System.out.println(pathChildrenCacheEvent.getData());
    }
}
