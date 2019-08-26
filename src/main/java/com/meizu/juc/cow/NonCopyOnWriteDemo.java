package com.meizu.juc.cow;

import java.util.ArrayList;
import java.util.List;

public class NonCopyOnWriteDemo {
    List<String> list = new ArrayList<String>();
    
    public void addElement(String e) {
        synchronized (list) {            
            list.add(e);
        }
    }
    
    public void retrive() {
        synchronized (list) {            
            for(String e : list) {
                System.out.println(e);
            }
        } 
    }
}
