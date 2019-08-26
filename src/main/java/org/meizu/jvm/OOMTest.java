package org.meizu.jvm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OOMTest {
    /**
     * -Xms16m -Xmx32m
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Thread(() -> {
            List<byte[]> list = new ArrayList<byte[]>();
            while(true) {
                System.out.println(new Date().toString() + Thread.currentThread() + "=");
                byte[] b = new byte[1024 * 1024];
                list.add(b);
                try {                    
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        }).start();
        
        
        new Thread(() -> {
            while(true) {
                System.out.println(new Date().toString() + Thread.currentThread()+ "=");
                try {                    
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    

}
