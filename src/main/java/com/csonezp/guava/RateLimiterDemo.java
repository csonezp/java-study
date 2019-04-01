package com.csonezp.guava;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;

/**
 * Created by zp on 2018/4/24.
 */
public class RateLimiterDemo {

    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    private static void testLimiter() {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(10);
        for (int i = 0; i < 10; i++) {
            limiter.acquire();
            System.out.println("run task "+i);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    private static void testNoLimiter() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println("run task "+i);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    private static void test2() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(2);
        Thread.sleep(3000);
        acquire(limiter,1);
        acquire(limiter,1);
        acquire(limiter,1);
        acquire(limiter,1);
    }

    public static void acquire(RateLimiter r,int num){
        double time =r.acquire(num);
        System.out.println("wait time="+time);
    }

}
