package com.unisys.fp.lambda;

@FunctionalInterface
interface  MyFunction{
    void sayHello();
}
//parameters and args : more than one parameters and args
@FunctionalInterface
interface Details{
    //value1 and value2 are args
    void setDetails(String value1,String value2);
}
@FunctionalInterface
interface Single{
    void setName(String name);
}
//return
@FunctionalInterface
interface Stock{
    int getStock();
}

//more than one parameter
@FunctionalInterface
interface  Calculator{
    int calculate(int a, int b);
}

@FunctionalInterface
interface MyFunction2{
    String getValue(String value);
}

public class LambdaExpressionDeep {
    public static void main(String[] args) {
        MyFunction myFunction = null;

        myFunction = ()->{
            //function body
            System.out.println("Hello!");
        };
        myFunction.sayHello();
        //code refactoring with lambda
        /**
         * if lambda body has only one line of code, we can remove {}
         */
        myFunction = ()->System.out.println("Hello!");
        myFunction.sayHello();
        ////////////////////////////////////////////////////////////////////////
        //Args and parameters : more than one args
        Details details = null;

        details = (String value1,String value2)->{
            System.out.println(value1 + " " + value2);
        };
        details.setDetails("Hello" , "Hai");
        //code refeactoring
        details = (String value1,String value2)-> System.out.println(value1 + " " + value2);
        //Hello, Hai are parameters
        details.setDetails("Hello" , "Hai");

        //code refactoring : type inference, in lambda args type need not be specified
        details = (value1,value2)-> System.out.println(value1 + " " + value2);
        //Hello, Hai are parameters
        details.setDetails("Hello" , "Hai");
        /////////////////////////////////////////////////////////////////////////////////////////
        Single single = null;
        single = (String name)-> System.out.println(name);
        single.setName("Subramanian");
        //With type inference
        single = (name)-> System.out.println(name);
        single.setName("Subramanian");
        //if single arg and parameter , remove ()
        single = name-> System.out.println(name);
        single.setName("Subramanian");

        //************Return ****************************************
        Stock stock = null;
        stock = ()->{
            return 100;
        };
        System.out.println(stock.getStock());
        //if only return statement inside function body, remove {} and remove retun statement
        stock = ()->100;
        System.out.println(stock.getStock());
        //**********************Parameter/Args and Return*****************************************
        Calculator calculator = null;

        calculator = (a,b)->{
            return a + b;
        };
        System.out.println(calculator.calculate(12,23));
        //refactoring
        calculator = (a,b)->a + b;
        System.out.println(calculator.calculate(12,23));
        //Single Parameter
        MyFunction2 myFunction2 = null;
        //
        myFunction2 = (value)->{
            return value;
        };
        System.out.println(myFunction2.getValue("Welcome"));
        //code refactoring
        myFunction2 = value->value;
        System.out.println(myFunction2.getValue("Welcome"));



    }
}
