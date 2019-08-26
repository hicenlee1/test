package com.meizu.bitoperator;

import java.util.Arrays;
import java.util.BitSet;

public class BitOperator {
    
    public void addTest() {
        int a = 10;
        int b = 300;
        System.out.println(a & b);
    }
    
    public void huoTest() {
        int a = 10;
        int b = 300;
        System.out.println(a | b);
    }
    public void noTest() {
        int a = 300;
        System.out.println(~a);
    }
    
    public void xorTest() {
        int a = 10;
        int b = 1000;
        System.out.println(a ^ b ^ a ^ b);
    }
    
    public static void main(String[] args) {
//        BitOperator op = new BitOperator();
//        op.addTest();   
//        op.huoTest();
//        op.noTest();
//        op.xorTest();
//        System.out.println(Integer.toBinaryString(1));
//        System.out.println(Integer.toBinaryString(1 << 6));
//        System.out.println(1 << 6);
//        System.out.println("size:" + new BitSet().size());
//        System.out.println("size:" + new BitSet(130).size());
//        System.out.println(Long.toBinaryString(0x7FFFFFFF));
        
        BitSet bitset = new BitSet();
        bitset.set(10);
        bitset.set(20);
        bitset.set(30);
        System.out.println(Arrays.toString(bitset.toByteArray()));
    }
    
    

}
