package juc.action.futuretask;

public interface Computable<K, V> {

    V compute(K k);
}
