package com.meizu.javacc.demo5;

public class Main {
    public static void main(String[] args) throws ParseException {
        Adder adder = new Adder("1 + 3 * 4 / 2  - 2");
        adder.start(System.out);
    }
}
