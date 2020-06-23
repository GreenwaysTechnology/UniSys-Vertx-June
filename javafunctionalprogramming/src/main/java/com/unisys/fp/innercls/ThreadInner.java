package com.unisys.fp.innercls;

public class ThreadInner {
    public static void main(String[] args) {
        //Threads
        Runnable runnable = null;
        runnable=new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        //syntax 1
        Thread t1 = new Thread(runnable);
        t1.start();

        //syntax 2
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        t2.start();
    }
}
