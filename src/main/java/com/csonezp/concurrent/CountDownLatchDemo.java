// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.concurrent;

import com.csonezp.jedis.RedisClient;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhangpeng34
 * Created on 2019/2/12 下午12:35
**/
public class CountDownLatchDemo {

    static ExecutorService executorService = Executors.newFixedThreadPool(100);
    //闭锁，可实现计数器递减
    static final CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String STORE_KEY = "store_key";

    private static final String USER_KEY="user_key";
    private static final Integer USER_COUNT= 2;
    private static final Integer THREAD_COUNT = 1000;
    private static final long SLEEP_TIME=200L;

    private static final  boolean USE_REDIS = false;

    //秒杀中的用户列表
    private List<Integer> users = Lists.newArrayList();
    //库存
    public static Integer store = 100;

    private static Integer localStore;
    ThreadLocal<Integer> userId;

    int memCount = 0;
    int redisCount = 0;

    List<Future> futures = Lists.newArrayList();


    public void run() throws InterruptedException {
        RedisClient.set(STORE_KEY, store);
        localStore = store;
        RedisClient.del(USER_KEY);
        for (int i = 0; i < store +THREAD_COUNT ; i++) {
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    if (localStore <= 0) {
                        return;
                    }
                    userId = new ThreadLocal<>();
                    userId.set(RandomUtils.nextInt(1, 1 + USER_COUNT));
                    if (userId == null) {
                        return;
                    }
                    //fixme 秒杀动作
                    if (USE_REDIS) {
                        redisBuy(userId.get());
                    } else {
                        memBuy(userId.get());
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        countDownLatch.countDown();
        Thread.sleep(SLEEP_TIME);
        System.out.println(redisCount);
        System.out.println(memCount);
        users = users.stream().sorted(Integer::compareTo).collect(Collectors.toList());

        for (int i = 0; i < users.size()-1; i++) {
            Integer a = users.get(i);
            Integer b = users.get(i+1);
            if(a.compareTo(b)==0){
                System.out.println("重复！"+a);
            }
        }

        System.out.println(users.size());
        System.out.println(users.toString());

    }

    private void redisBuy(Integer userId) {
        //如果用户hset返回1说明该用户没有秒杀记录，如果返回0说明已经有秒杀记录
        if(RedisClient.hset(USER_KEY,userId.toString(),userId.toString()).intValue() != 1){
            return;
        }

        Long sotred = RedisClient.decr(STORE_KEY);
        if(sotred >=0){
            redisCount++;
            users.add(userId);
            localStore = sotred.intValue();
        }
    }

    private void memBuy(Integer userId) {
        if(userId==null){
            return;
        }
        //如果该用户已经秒中，则直接返回
        if(users.contains(userId)){
            return;
        }
        //执行扣减库存操作
        if(store >0){
            if(userId==null){
                return;
            }
            store = store - 1;
            if(store >=0){
                memCount++;
                users.add(userId);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        try {
            CountDownLatchDemo demo =new CountDownLatchDemo();
            demo.run();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.exit(0);
            executorService.shutdownNow();
        }


    }

    class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            try {
                countDownLatch.await();
                if (localStore <= 0) {
                    return -1;
                }
                userId = new ThreadLocal<>();
                userId.set(RandomUtils.nextInt(1, 1 + USER_COUNT));
                if (userId == null) {
                    return -1;
                }
                //fixme 秒杀动作
                if (USE_REDIS) {
                    redisBuy(userId.get());
                } else {
                    memBuy(userId.get());
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;

        }
    }



}