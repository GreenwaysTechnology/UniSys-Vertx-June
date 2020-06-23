package com.unisys.fp.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;


class HttpServerV2 {
    public void fetch(Consumer<Object> consumer) {
        //fake response
        List<String> response = Arrays.asList("Subramanian", "Geetha", "Srisha", "DhivayaSree");
        //return the response
        consumer.accept(response);
    }

}


public class BuiltInFunctionalInterfaces {
    public static void main(String[] args) {
        //Supplier: which returns some thing, no input
        Supplier<String> supplier = null;
        supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        };
        System.out.println(supplier.get());
        supplier = () -> "Hello";
        System.out.println(supplier.get());

        //Consumer : takes input dont return
        Consumer<String> consumer = null;
        consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("Subramanian");
        //consumer inside java apis : collections : iterators : forEach
        List<String> names = Arrays.asList("Subramanian", "Geetha", "Divya Sree");

        names.forEach(name -> System.out.println(name));
        //method reference
        names.forEach(System.out::println);
        names.forEach(name -> {
            String someVar = name.toLowerCase();
            System.out.println(someVar);
        });

        Predicate<Integer> predicate = null;
        predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer.intValue() == 10; // true or false
            }
        };
        String result = "";
        result = predicate.test(10) ? "True" : "False";
        System.out.println(result);

        predicate = value -> value.intValue() == 10;
        result = predicate.test(10) ? "True" : "False";
        System.out.println(result);

        //Single input, return the same
        UnaryOperator<String> unaryOperator = null;
        unaryOperator = new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        };
        System.out.println(unaryOperator.apply("Hello i am unary"));
        unaryOperator = s -> s.toLowerCase();
        System.out.println(unaryOperator.apply("Hello i am unary"));

        //Binary : two input, return single output
        BinaryOperator<Integer> binaryOperator = null;
        binaryOperator = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer v1, Integer v2) {
                return v1.intValue() * v2.intValue();
            }
        };
        System.out.println(binaryOperator.apply(10, 10));
        binaryOperator = (v1, v2) -> v1.intValue() * v2.intValue();
        System.out.println(binaryOperator.apply(10, 10));
        ///////////////////////////////////////////////////

        Function<Integer, String> function = null;
        function = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return "Integer String Value " + integer.toString();
            }
        };
        System.out.println(function.apply(1000));
        function = value -> "Integer String Value " + value.toString();
        System.out.println(function.apply(1000));
        //*****************************************************************************************
        HttpServerV2 httpServerV2 = new HttpServerV2();
        httpServerV2.fetch(res -> System.out.println(res));
        httpServerV2.fetch(System.out::println);

        ///Higher order function

        //Function 1
        Function<Integer, Integer> multiply = value -> value.intValue() * 2;

        //Function 2
        Function<Integer, Integer> add = value -> value.intValue() + 3;

        //Function result
        Function<Integer,Integer>  resultFunction=  multiply.compose(add);
       // Function<Integer,Integer>  resultFunction=  add.compose(multiply);
        Integer result1 =resultFunction.apply(3);
        //result = (3+3) * 2 =>AddfirstthenMultiply
        //compose function starts composing from right to left
        System.out.println(result1);

        //andThen method works opposite of the compose() Method
        //left to right
        Function<Integer, Integer> andThenResult = multiply.andThen(add);
        System.out.println("And then Result");
        System.out.println(andThenResult.apply(10));
    }
}
