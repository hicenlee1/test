package juc.action.futuretask;

public class Client {

    public static void main(String[] args) {
        Computable<Integer, Integer> comput =  new ComputImpl();
        MemorizedCompute<Integer, Integer> cache = new MemorizedCompute<Integer, Integer>(comput);

        long time1 = System.currentTimeMillis();
        Integer v1 = cache.compute(new Integer(100));
        long time2 = System.currentTimeMillis();
        System.out.println("compute1 cost" + (time2 - time1) + "ms");

        Integer v2 = cache.compute(new Integer(100));
        long time3 = System.currentTimeMillis();
        System.out.println("compute2 cost" + (time3 - time2) + "ms");


        Integer v3 = cache.compute(new Integer(300));
        long time4 = System.currentTimeMillis();
        System.out.println("compute3 cost" + (time4 - time3) + "ms");


        System.out.println("v1:" + v1);
        System.out.println("v2:" + v2);
        System.out.println("v3:" + v3);


    }
}
