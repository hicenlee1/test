package com.meizu.zk.curator;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

public class ZkNodeCacheListener implements NodeCacheListener {

    private NodeCache nodeCache;

    public ZkNodeCacheListener(NodeCache nodeCache) {
        this.nodeCache = nodeCache;
    }
    @Override
    public void nodeChanged() throws Exception {
        System.out.println("................monitor........................");
        System.out.println("节点值:" + new String(nodeCache.getCurrentData().getData()));
        System.out.println(nodeCache.getCurrentData().getStat());
    }
}
