// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.jedis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author zhangpeng34
 * Created on 2019/2/12 上午11:45
 **/
public class RedisDecrDemo {

  // 请求总数
  public static int clientTotal = 5000;

  // 同时并发执行的线程数
  public static int threadTotal = 200;

  public static int count = 0;

  private static final String STORE_KEY = "store_key";


  public static void main(String[] args) {

    RedisClient.set(STORE_KEY,10);

    ExecutorService executorService = Executors.newCachedThreadPool();
    //信号量，此处用于控制并发的线程数
    final Semaphore semaphore = new Semaphore(threadTotal);
    //闭锁，可实现计数器递减
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

    for (int i = 0; i < clientTotal ; i++) {
      executorService.execute(() -> {
        try {

          //执行此方法用于获取执行许可，当总计未释放的许可数不超过200时，
          //允许通行，否则线程阻塞等待，直到获取到许可。
          semaphore.acquire();

          Integer val = RedisClient.get(STORE_KEY);
          if(val>0){
            Long stored = RedisClient.decr(STORE_KEY);
            if(stored >= 0 ){
              count++;
              System.out.println("购买成功,售出总数："+count);
            }
          }

          //释放许可
          semaphore.release();
        } catch (Exception e) {
          e.printStackTrace();
        }
        //闭锁减一
        countDownLatch.countDown();
      });
    }


  }
}