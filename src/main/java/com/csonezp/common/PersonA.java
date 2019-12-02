// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.common;

import com.csonezp.utils.JacksonUtil;
import lombok.Data;

/**
 * @author zhangpeng34
 * Created on 2019/3/8 上午11:11
**/
@Data
public class PersonA extends Person {
    public static void main(String[] args) {
        Person person = new Person();
        person.setValue(1L);
        person.setAge(1L);
        person.setName("w2");
        PersonA a = (PersonA) person;
        System.out.println(JacksonUtil.toJson(a));
    }
}