package com.meizu.javacc.demo3;

import java.io.ByteArrayInputStream;

public class Main {
    public static void main(String[] args) throws ParseException {
        Adder adder = new Adder("1 + 3.3 + 54 # 2.3 + 3.4#");
        adder.start(System.out);

    }
}
