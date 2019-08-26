package com.meizu.datastructure;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class LinkedListDemo {
    
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        ListIterator<Integer> it = list.listIterator();
        it.next();
        System.out.println(it.hasPrevious());
    }
}
