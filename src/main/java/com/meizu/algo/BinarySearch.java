package com.meizu.algo;

public class BinarySearch {
    
    public int serach(int[] array, int x) {
        //array is sort asc
        int begin = 0;
        int end = array.length - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            if (array[mid] == x) {
                return mid;
            } else if (array[mid] < x) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }
    
    public int firstEqual(int[] array, int x) {
        int begin = 0;
        int end = array.length - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            if (array[mid] >= x) {
                end = mid - 1;
            } else {
                begin = mid + 1;
            }
        }
        if (begin < array.length && array[begin] == x) {
            return begin;
        }
        return -1;
    }
    
    
    
    
    public static void main(String[] args) {
        int[] array = new int[] {1,2,3,4,5,6,7,90, 100};
        BinarySearch s = new BinarySearch();
        int index = s.serach(array, 100);
        System.out.println(index);
        
        //array = new int[] {1,2,3,3,3,4,4,4,5,6,7,90, 100};
        //index = s.firstEqual(array, 4);
        array = new int[] {1,2,3};
        index = s.firstEqual(array, 2);
        System.out.println(index);
    }
}
