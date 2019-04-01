package com.csonezp.utils;

import java.util.function.Function;

/**
 * Created by zp on 2018/5/15.
 */
public class FunctionDemo {
    private final String name;
    public FunctionDemo(String name ){
        this.name =name;
    }
    public static void main(String[]args){
        FunctionDemo demo =new FunctionDemo("123");
    }
}
