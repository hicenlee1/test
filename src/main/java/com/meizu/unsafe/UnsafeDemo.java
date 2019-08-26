package com.meizu.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeDemo {
    
    private int age = 20;
    
    private String name = "tom";
    
    private static int staticint = 10;
    
    private static String staticstring = "static-string";
    
    public UnsafeDemo(int age, String name) {
        this.age = age;
        this.name = name;
    }
    
    
    public UnsafeDemo() {
        
    }
    /**
     * Unsafe类本身是单例的(据代码 Unsafe.getUnsafe())，需要通过静态方法获取唯一实例。不过编译器、运行时都会报错。
     * 
     * 知道应该是通过类加载器限制。一般我们写的类都是由Application ClassLoader（sun.misc.Launcher$AppClassLoader）进行加载的，层级比较低，
     * 这里的SystemDomainLoader就是BootstarpClassLoader（C++写的），也就是加载rt.jar里面的类的加载器，所以java.*用就不会有事，我们用就会有事。
     * 
     * 想要使用Unsafe有两种方式。一种是用反射，比较简单 (本方法)
     * 另外一种是通过虚拟机启动参数-Xbootclasspath，把你的classpath变为启动路径之一，这样就是BootstarpClassLoader加载你的类，跟java.*一个待遇了，就不会报错了
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Unsafe getUnsafe() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }
    
    
    public void testStaticField1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Unsafe unsafe = UnsafeDemo.getUnsafe();
        long intoffset = unsafe.staticFieldOffset(UnsafeDemo.class.getDeclaredField("staticint"));
        long stringoffset = unsafe.staticFieldOffset(UnsafeDemo.class.getDeclaredField("staticstring"));
        Object o = unsafe.staticFieldBase(UnsafeDemo.class);
        //静态变量从方法区获取数据
        int staticint = unsafe.getInt(o, intoffset);
        String staticstring = (String) unsafe.getObject(o, stringoffset);
        
        System.out.println("static.int =>" + staticint);
        System.out.println("static.string =>" + staticstring);
    }
    
    public void testStaticField2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        /**
         * 
         staticFieldBase: 获取静态变量所属的类在方法区的首地址。可以看到，返回的对象就是Test.class。
         staticFieldOffset: 获取静态变量地址偏移值。
         */
        Unsafe unsafe = UnsafeDemo.getUnsafe();
        Object intObject = unsafe.staticFieldBase(UnsafeDemo.class.getDeclaredField("staticint"));
        Object stringObject = unsafe.staticFieldBase(UnsafeDemo.class.getDeclaredField("staticstring"));
        
        long intoffset = unsafe.staticFieldOffset(UnsafeDemo.class.getDeclaredField("staticint"));
        long stringoffset = unsafe.staticFieldOffset(UnsafeDemo.class.getDeclaredField("staticstring"));
        
        int staticint = unsafe.getInt(intObject, intoffset);
        String staticstring = (String) unsafe.getObject(stringObject, stringoffset);
        System.out.println("static.int2 =>" + staticint);
        System.out.println("static.string2 =>" + staticstring);
    }
    
    public void testInstanceField() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
      //报错
        //Unsafe unsafe = Unsafe.getUnsafe();
        Unsafe unsafe = UnsafeDemo.getUnsafe();
        long ageFieldOffset = unsafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("age"));
        long nameFieldOffset = unsafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("name"));
        
        UnsafeDemo tom = new UnsafeDemo();
        UnsafeDemo jim = new UnsafeDemo(30, "jim");
        
        //实例变量从示例获取数据
        int tomage = unsafe.getInt(tom, ageFieldOffset);
        String tomname = (String) unsafe.getObject(tom, nameFieldOffset);
        
        int jimage = unsafe.getInt(jim, ageFieldOffset);
        String jimname = (String) unsafe.getObject(jim, nameFieldOffset);
        
        System.out.println("tom.age=>" + tomage);
        System.out.println("tom.name=>" + tomname);
        
        System.out.println("jim.age=>" + jimage);
        System.out.println("jim.name=>" + jimname);
    }
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        
        UnsafeDemo demo = new UnsafeDemo();
        demo.testInstanceField();
        demo.testStaticField1();
        demo.testStaticField2();
    }


}
