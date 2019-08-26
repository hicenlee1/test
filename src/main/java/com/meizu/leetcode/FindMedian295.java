package com.meizu.leetcode;

import java.util.Collections;
import java.util.PriorityQueue;

public class FindMedian295 {
    
    PriorityQueue<Integer> maxHeap;
    PriorityQueue<Integer> minHeap;
    
    public FindMedian295() {
        maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
        minHeap = new PriorityQueue<Integer>();
    }
    
    public void add(int num) {
     // 如果最大堆为空，或者该数小于最大堆堆顶，则加入最大堆
        if (maxHeap.size() == 0 || num < maxHeap.peek()) {
         // 如果最大堆大小超过最小堆，则要平衡一下
            if (maxHeap.size() > minHeap.size()) {
                minHeap.offer(maxHeap.poll());
            }
            maxHeap.offer(num);
        } else if (minHeap.size() == 0 || num > minHeap.peek()) {
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
            minHeap.offer(num);
        } else {
            // 数字在两个堆顶之间的情况
            if (maxHeap.size() <= minHeap.size()) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }
        }   
    }
    
    public double findMedian() {
     // 返回大小较大的那个堆堆顶，如果大小一样说明是偶数个，则返回堆顶均值
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else if (maxHeap.size() < minHeap.size()) {
            return minHeap.peek();
        } else if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return 0;
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2;
        }
    }
    
    
    public static void main(String[] args) {
        FindMedian295 medianFinder = new FindMedian295();
        medianFinder.add(1);
        medianFinder.add(10);
        medianFinder.add(100);
        medianFinder.add(20);
        medianFinder.add(30);
        System.out.println(medianFinder.findMedian());
    }

}
