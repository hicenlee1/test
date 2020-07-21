package com.meizu.juc.threadlocal;

public class ThreadLocalUsage extends  Thread{

    public User user = new User();

    public User getUser() {
        return user;
    }
    @Override
    public void run() {
        this.user.set("var1");
        while(true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {

            }
            System.out.println(this.user.get());
        }
    }

    public static void main(String[] args) {
        ThreadLocalUsage thread = new ThreadLocalUsage();
        thread.start();

        try {
            sleep(4000);
        } catch (InterruptedException e) {

        }
        thread.user.set("var2");
        //会一直打印var1,因为是不同的线程
    }


}
