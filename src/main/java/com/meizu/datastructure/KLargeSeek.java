package com.meizu.datastructure;

import java.util.PriorityQueue;

/**
 * 查找元素中第k 大的元素
 * @author haiyang1
 *
 */
public class KLargeSeek {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        int[] orignal = new int[] {1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,80,90,100,11,12,13,14,15,16,17};
        KLargeSeek seek = new KLargeSeek();
        int klargest = 13;
        int k = seek.seekByHeapDataStructure(orignal, klargest);
        System.out.println(k);
    }
    
    public int seekByHeapDataStructure(int[] array, int k) {
        PriorityQueue<Integer> maxElementQueue = new PriorityQueue<Integer>(k);
        for (int num : array) {
            if (maxElementQueue.size() < k || maxElementQueue.peek() < num) {
                maxElementQueue.offer(num);
            }
            if (maxElementQueue.size() > k) {
                maxElementQueue.poll();
            }
        }
        return maxElementQueue.peek();
    }
}
