package com.csonezp.function;

import java.util.function.Function;

public class FunctionDemo {
    public static void main(String[] args) {
        Function<Integer,Integer> test1=i->i+1;
        Function<Integer,Integer> test2=i->i*i;

        System.out.println(calculate(test1,5));
        System.out.println(calculate(test2,5));

    }
    public static Integer calculate(Function<Integer,Integer> test,Integer number){
        return test.apply(number);
    }
}
