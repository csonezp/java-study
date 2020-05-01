package com.csonezp.concurrent.AQS;

import com.csonezp.common.Person;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : peng.zhang33
 * @date : 2019-09-10 16:02
 */
public class ReenterLimitedLock extends ReentrantLock {
    @Override
    public boolean tryLock() {
        return super.tryLock();
    }

    @Override
    public void lock() {
        super.lock();
    }



    public static void main(String[] args) {
        List<Person> list = Lists.newArrayList();
        Long l1 = 21312323L;
        Long l2 = 21312323L;
        Long l3 = l1 | l2;


        Person p = new Person();
        p.setAge(11L);
        p.setValue(11L);

        list.add(p);

        Person p2 = new Person();

        p2.setAge(12L);
        p2.setValue(12L);
        list.add(p2);

        Long sum = list.stream().map(item -> item.getAge()+item.getValue()).reduce(0L,Long::sum);
        System.out.println(sum);
    }
}
