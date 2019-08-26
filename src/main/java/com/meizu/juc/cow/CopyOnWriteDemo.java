package com.meizu.juc.cow;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * copyonwrite 可以显著提高读取数据的性能，不用并发
 */
public class CopyOnWriteDemo {
    
    List<String> list = new CopyOnWriteArrayList<String>();
    
    public void addElement(String e) {
        list.add(e);
    }
    
    public void retrive() {
        for(String e : list) {
            System.out.println(e);
        }
    }
    
    
}
