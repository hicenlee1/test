package com.meizu.leetcode;
/**
 * 计算一个字符串中给定字符能够拼出的最大回文数的长度
 * 
 * @author haiyang1
 *
 */
public class LongestPalindrome {
   public static int getLength(String s) {
       //思路：如果一个字符出现偶数次数，则可以全部添加(每次添加 n-0)
       //    如果出现奇数次数，则可以添加每个(n-1)次数 。最后再+1（奇数的某一个在最中间）
       char[] chars = s.toCharArray();
       int[] frequency = new int[128];
       for (char c :chars) {
           frequency[c]++;
       }
       
       int count = 0;
       int jishu = 0;
       for (int i : frequency) {
           int yushu = i & 1; //   求模操作
           jishu = jishu | yushu; // 是否有奇数存在
           
           count += (i - yushu);
       }
       return count + jishu;
   }
   public static void main(String[] args) {
    
//    System.out.println((int) 'A');
//    System.out.println((int) 'Z');
//    System.out.println((int) 'a');
//    System.out.println((int) 'z');
       String s = "abcccdba";
       System.out.println(getLength(s));
   }
}
