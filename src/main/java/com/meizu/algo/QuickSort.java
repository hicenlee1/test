package com.meizu.algo;

public class QuickSort {
    
    static int[] array = new int[] {2, 3, 5, 30 ,60 ,90, 100, 11232323, 25, 100, 200, 1, 4, 6, 9, 10};
    //static int[] array = new int[] {10, 9, 8, 7, 6,5,4,3,2,1};
    
    public static void main(String[] args) {
        QuickSort sort = new QuickSort();
        sort.sort(0, array.length - 1);
        for (int e : array) {
            System.out.println(e);
        }
    }
    
    public void sort(int left, int right) {
        if (left > right) {
            return;
        }
        
        int i = left, j = right;
        int tmp = array[left];
        int change;
        
        while(i != j) {
            while(array[j] >= tmp && i < j) {
                j--;
            }
            
            while(array[i] <= tmp && i < j) {
                i++;
            }
            
            if (i < j) {
                change = array[i];
                array[i] = array[j];
                array[j] = change;
            }
        }
        
        if (left != i) {            
            array[left] = array[i];
            array[i] = tmp;
        }
        
        sort(left, i - 1);
        sort(i + 1, right);
    }
}
