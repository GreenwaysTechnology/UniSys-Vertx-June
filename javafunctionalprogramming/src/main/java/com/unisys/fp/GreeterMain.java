package com.unisys.fp;
//interface as generic declaration for many implementation
interface Greeter{
  String saySomething();
}

class HelloImpl implements Greeter{
    public String saySomething() {
        return "hello";
    }
}
class HaiImpl implements  Greeter {
    public String saySomething() {
        return "Hai";
    }
}

public  class GreeterMain {
    public static void main(String[] args) {
        //interface
        Greeter greeter = null;
        greeter = new HelloImpl();
        System.out.println(greeter.saySomething());
        greeter = new HaiImpl();
        System.out.println(greeter.saySomething());
    }
}
