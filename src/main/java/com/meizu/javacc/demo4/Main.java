package com.meizu.javacc.demo4;

public class Main {
    public static void main(String[] args) throws ParseException {
        Adder adder = new Adder("1. + 2.3 + 3.4 - 4.5");
        adder.start(System.out);
    }
}
