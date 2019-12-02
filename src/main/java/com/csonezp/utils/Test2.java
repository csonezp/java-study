package com.csonezp.utils;

import com.csonezp.common.Person;

import java.util.List;

public class Test2 {
    public  void test(List<Person> list){
        list.get(0).setName("changed");
    }
}
