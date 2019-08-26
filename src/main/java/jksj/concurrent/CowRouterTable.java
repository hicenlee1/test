package jksj.concurrent;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CowRouterTable {
    private final ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> rt = new ConcurrentHashMap<>();

    /**
     * 添加路由
     * @param router
     */
    public void add(Router router) {
        Set<Router> set = rt.computeIfAbsent(router.iface, r ->  new CopyOnWriteArraySet());
        set.add(router);
    }

    public Set<Router> get(String iface) {
        return rt.get(iface);
    }

    public void remove(Router router) {
        Set<Router> set = rt.get(router.iface);
        if (set != null) {
            set.remove(router);
        }
    }
}

class Router {
    String iface;
    String ip;
    Integer port;
    public Router(String iface, String ip, Integer port) {
        this.iface = iface;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Router router = (Router) o;
        return Objects.equals(iface, router.iface) &&
                Objects.equals(ip, router.ip) &&
                Objects.equals(port, router.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iface, ip, port);
    }
}