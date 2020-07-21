package com.meizu.javacc.demo6;

public class Main {
    public static void main(String[] args) throws ParseException {
        Adder adder = new Adder("(1+2)*-3 # $/3 # 4*(-5+2)*3 #");
        adder.start(System.out);
    }
}
