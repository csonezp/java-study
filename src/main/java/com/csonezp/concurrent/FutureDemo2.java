// Copyright (C) 2018 Meituan
// All rights reserved
package com.csonezp.concurrent;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhangpeng34
 * Created on 2018/10/16 下午1:13
**/ 
public class FutureDemo2 {
    static ExecutorService executor = Executors.newFixedThreadPool(10);
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(()->{
            Thread.sleep(4000);
            System.out.println("sleep 4000");
           return 2;
        });
        tasks.add(()->{
            Thread.sleep(1000);
            System.out.println("sleep 1000");
            return 1;
        });

        List<Future<Integer>> futures = executor.invokeAll(tasks);
        for (Future<Integer> future : futures) {
            Integer a = future.get();
            System.out.println(a);
        }
        System.out.println("all done");
        executor.shutdownNow();
    }
}