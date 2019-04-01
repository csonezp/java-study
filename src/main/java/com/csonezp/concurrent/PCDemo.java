// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangpeng34
 * Created on 2019/3/9 上午12:36
 **/
public class PCDemo {

    private static LinkedList list = new LinkedList();
    private static ReentrantLock lock = new ReentrantLock();
    private static Integer maxSize = 10;
    private static Condition prodCon = lock.newCondition();
    private static Condition consCon = lock.newCondition();


    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (list.size() < maxSize) {
                        list.add(new Object());

                        Thread.sleep(100);
                        System.out.println("add a obj,size:" + list.size());
                        //如果满了，await prod，notify cons
                    }
                    if (list.size() == maxSize) {
                        System.out.println("list fully");
                        consCon.signal();
                        prodCon.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }


        }
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (list.size() > 0) {
                        Thread.sleep(100);


                        list.remove();

                        System.out.println("remove a obj,size:" + list.size());
                        //如果满了，await prod，notify cons
                    }
                    if (list.size() == 0) {
                        System.out.println("list empty");
                        prodCon.signal();
                        consCon.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }


        }


    }
}