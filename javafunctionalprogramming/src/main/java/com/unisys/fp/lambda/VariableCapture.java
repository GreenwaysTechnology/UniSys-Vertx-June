package com.unisys.fp.lambda;


import java.util.Calendar;

@FunctionalInterface
interface Variable {
    void doIt();
}

//class
class Capture {

    //instance variable
    int counter = 10;
    public void doStuff() {
        Variable variable = () -> {
            //capaturing instance variable inside lambda
            System.out.println(counter);
        };
        variable.doIt();
    }
}

public class VariableCapture {
    public static void main(String[] args) {
        Capture capture = new Capture();
        capture.doStuff();
        //local variable
        String name="Hello , I am local Variable";
        Variable variable = () -> {
            //capaturing local variable inside lambda
            System.out.println(name);
        };
        variable.doIt();


    }
}
