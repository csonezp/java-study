// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.concurrent;

import com.csonezp.AQS.MutexLock;

/**
 * @author zhangpeng34
 * Created on 2019/2/22 下午3:40
**/ 
public class AQSDemo {

    public static void main(String[] args) {
        TestTask test = new TestTask();
        Thread thread1 = new Thread(() -> test.method1());
        thread1.setName("Thread1");
        thread1.start();
        Thread thread2 = new Thread(() -> test.method2());
        thread2.setName("Thread2");
        thread2.start();
    }

    static class TestTask {
        private MutexLock lock = new MutexLock();
        public void method1() {
            lock.lock();
            try {
                Thread.sleep(500);
                System.out.println("method1() execute!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        public void method2() {
            lock.lock();
            try {
                System.out.println("method2() execute!");
            } finally {
                lock.unlock();
            }
        }
    }
}