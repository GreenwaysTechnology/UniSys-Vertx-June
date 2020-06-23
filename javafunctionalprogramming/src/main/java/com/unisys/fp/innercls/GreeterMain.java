package com.unisys.fp.innercls;

interface Greeter{
    String saySomething();
}
//implemnetation classes
//class HelloImpl implements Greeter {
//    public String saySomething() {
//        return "hello";
//    }
//}
//class HaiImpl implements Greeter {
//    public String saySomething() {
//        return "Hai";
//    }
//}

public class GreeterMain {
    public static void main(String[] args) {
        //annonous inner classes
        Greeter greeter = null;
        //provide implementation without extra class

        //Hello impl
        greeter = new Greeter() {
            public String saySomething() {
                return "Hello";
            }
        };
        System.out.println(greeter.saySomething());
       //Hai impl
        greeter = new Greeter() {
            public String saySomething() {
                return "Hai";
            }
        };
        System.out.println(greeter.saySomething());



    }
}
