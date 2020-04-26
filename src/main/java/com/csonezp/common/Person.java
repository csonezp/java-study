// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.common;

import lombok.Data;

/**
 * @author zhangpeng34
 * Created on 2019/3/8 上午11:11
**/
@Data
public class Person {
    private String name;
    Long age;
    Long value;

    public static void main(String[] args) {
        Person person = new Person();
        person.setName(null);
        Person person1 = new Person();
        person1.setName(person.getName());
        System.out.println(person1);
    }
    static class DD{
        public void test(){
            age = 11L;
        }
    }
}