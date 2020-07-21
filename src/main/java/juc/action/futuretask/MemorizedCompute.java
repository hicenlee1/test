package juc.action.futuretask;

import java.util.Map;
import java.util.concurrent.*;

public class MemorizedCompute<K, V> implements Computable<K, V> {
    Computable<K, V> computable;
    private final Map<K, FutureTask<V>> cache = new ConcurrentHashMap<K, FutureTask<V>>();;

    public MemorizedCompute(Computable computable) {
        this.computable = computable;
    }

    @Override
    public V compute(K k) {
        //use  while-loop in case  CancellationException, then retry
        while (true) {
            FutureTask<V> ftv = cache.get(k);
            if (ftv == null) {
                FutureTask<V> ft = new FutureTask(new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computable.compute(k);
                    }
                });
                ftv = cache.putIfAbsent(k, ft);
                if (ftv == null) {
                    ftv = ft;
                    ft.run();
                }
            }

            try {
                return ftv.get();
            } catch (CancellationException e) {
                //cancel  remove from map and retry
                cache.remove(k, ftv);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

//        FutureTask<V> ftv = cache.get(k);
//        if (ftv == null) {
//            FutureTask<V> ft = new FutureTask(new Callable<V>() {
//                @Override
//                public V call() throws Exception {
//                    return computable.compute(k);
//                }
//            });
//            ftv = cache.putIfAbsent(k, ft);
//            if (ftv == null) {
//                ftv = ft;
//                ft.run();
//            }
//        }
//
//        try {
//            return ftv.get();
//        } catch (CancellationException e) {
//            cache.remove(k, ftv);
//            return null;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return null;
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }



    }

}
