package com.csonezp;

import com.csonezp.common.Person;
import com.csonezp.utils.Test2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test1 {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("raw");
        Map map = Maps.newHashMap();
        map.put("1",person);
        List<Person> list = Lists.newArrayList();
        list = list.stream().filter(item -> item!=null && item.getAge()!=null).collect(Collectors.toList());
        System.out.println(list);

    }

    private static void changeValue(List<Person> list) {
        Person p = list.get(0);
        p.setName("changed");
    }
}
