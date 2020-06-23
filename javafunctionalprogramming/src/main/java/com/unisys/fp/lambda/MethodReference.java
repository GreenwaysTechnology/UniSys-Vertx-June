package com.unisys.fp.lambda;

@FunctionalInterface
interface Printer {
    void print(String value);
}

class MicroTaskRunner {
    public void startMicroTaskInstance() {
        for (int i = 0; i < 5; i++) {
            System.out.println("MicroTaskRunner Instance " + Thread.currentThread().getName());
        }
    }

    public static void startMicroTaskStatic() {
        for (int i = 0; i < 5; i++) {
            System.out.println("MicroTaskRunner Static " + Thread.currentThread().getName());
        }
    }
}
//class Loop

class Loop {

    //instance method
    private void startMicroTask() {
        for (int i = 0; i < 5; i++) {
            System.out.println("MicroTask" + Thread.currentThread().getName());
        }
    }

    public void start() {
        Thread thread = null;
        thread = new Thread(() -> {
            System.out.println("My Thread " + Thread.currentThread().getName());
        });
        thread.start();
        //isolate thread runnable logic into a separte method
        thread = new Thread(() -> startMicroTask());
        thread.start();
        //isolate thread runnable logic into a separate method : Method reference syntax
        thread = new Thread(this::startMicroTask);
        thread.start();
        //passing other class instance method using method reference
        thread = new Thread(new MicroTaskRunner()::startMicroTaskInstance);
        thread.start();
        //passing other class static method using method reference
        thread = new Thread(MicroTaskRunner::startMicroTaskStatic);
        thread.start();

    }
}


public class MethodReference {
    public static void main(String[] args) {
        Loop loop = new Loop();
        loop.start();

        //Method  Reference using  Printer interface
        Printer printer = null;
        printer = value -> {
            System.out.println(value);
        };
        printer.print("Hello");

        printer = value -> System.out.println(value);
        printer.print("Hello");

        //use method reference using System.out.println : if method returns , pass inside sop, write below
        printer = System.out::println;
        printer.print("Hello");

        HttpServer httpServer = new HttpServer();
        HTTPGetHandler httpGetHandler = System.out::println;
        httpServer.fetch(httpGetHandler);
        httpServer.fetch(System.out::println);

    }
}
