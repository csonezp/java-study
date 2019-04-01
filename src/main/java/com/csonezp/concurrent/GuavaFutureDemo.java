// Copyright (C) 2018 Meituan
// All rights reserved
package com.csonezp.concurrent;

import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.RandomUtils;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @author zhangpeng34
 * Created on 2018/12/25 上午10:38
**/ 
public class GuavaFutureDemo {

    final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) throws Exception{
        ListenableFuture<String> future = service.submit(() -> RandomUtils.nextInt()+"");

        future.addListener(() -> {
            try {
                Thread.sleep(600);
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        },service);

    }
}