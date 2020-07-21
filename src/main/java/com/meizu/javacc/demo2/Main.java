package com.meizu.javacc.demo2;

public class Main {
    public static void main(String[] args) throws ParseException {
        Adder adder = new Adder("1 + 3 + 4 + 5");
        System.out.println(adder.start());
    }
}
