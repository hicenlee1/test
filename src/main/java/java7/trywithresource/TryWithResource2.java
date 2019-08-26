package java7.trywithresource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TryWithResource2 implements AutoCloseable{
    public static void main(String[] args) {
        try (InputStream is = new FileInputStream("c:/login.conf");
             BufferedInputStream buffer = new BufferedInputStream(is);
             TryWithResource2 t1 = new TryWithResource2();) {
            int data = buffer.read();
            while (data != -1) {
                System.out.print((char) data);
                data = buffer.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //会自动  逆序关闭  try  中所有实现了 AutoClosable 接口的对象
    }

    @Override
    public void close() throws Exception {
        System.out.println("\nclosing");
    }
}
