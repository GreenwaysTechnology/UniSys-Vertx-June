package com.unisys.fp.lambda;

interface Welcome {
    //abstract methods
    void saySomething();

    //void doSomething(); //error when you add extra abstract methods
    default void doSomething() {
        System.out.println("Do something default method");
    }
    static void doStuff() {
        System.out.println("Static method inside SAM");
    }
}

@FunctionalInterface
interface Hello {
    void sayHello();
    //void sayHai();
    default void doSomething() {
        System.out.println("Do something default method");
    }

    static void doStuff() {
        System.out.println("Static method inside SAM");
    }
}


public class LambdaExpressionRules {
    public static void main(String[] args) {
        Welcome welcome = null;
        //welcome.doSomething(); throws null pointer exeception

        //with single abstract method
        welcome = () -> {
            System.out.println("Welcome to Lambda");
        };
        welcome.saySomething();
        welcome.doSomething();

        //call static method
        Welcome.doStuff();

        Hello hello = null;
        hello = ()->{
            System.out.println("Hello");
        };
        hello.sayHello();
        hello.doSomething();
        Hello.doStuff();

    }
}
