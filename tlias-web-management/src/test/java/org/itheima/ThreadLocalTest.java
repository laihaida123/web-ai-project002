package org.itheima;

public class ThreadLocalTest {
    private static ThreadLocal<String> local = new ThreadLocal<String>();

    public  static void main(String[] args) {
        local.set("main message");

        //创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("sub message");
                System.out.println(Thread.currentThread().getName() + " : " + local.get());
            }
        }).start();
        System.out.println(Thread.currentThread().getName() + " : " + local.get());

        local.remove();

        System.out.println(Thread.currentThread().getName() + " : " + local.get());


    }
}
