package com.unisys.fp.lambda;

//interface to implement lambda
interface Greeter{
    //Abstract method
    String saySomething();
}

class GreeterImpl implements Greeter{
    public String saySomething() {
        return "Hello";
    }
}

public class BasicLambda {
    public static void main(String[] args) {
      //Implmentation for Greeter
      //way1 : via concrete class
        Greeter greeter = null;
        greeter = new GreeterImpl();
        System.out.println(greeter.saySomething());

        //way 2: via anonmous inner class
        greeter = new Greeter() {
            public String saySomething() {
                return "Hai";
            }
        };
        System.out.println(greeter.saySomething());

        //Way 3 : via lambda expression

        greeter =()-> {
                return "Hai Lambda";
        };
        System.out.println(greeter.saySomething());


    }
}
