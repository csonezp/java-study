package com.csonezp.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HystrixDemo {

    @HystrixCommand(
            groupKey = "groupKey",commandKey = "commandKey",fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),//方法执行超时时间
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),//当并发错误个数达到此阀值时(在时间窗口内)，触发隔断器
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "200"),//滚动窗口时间长度
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),//滚动窗口的桶数
                    @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds",value = "100"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "200"),//隔断器被触发后，睡眠多长时间开始重试请求
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "100"),
            }
    )
    public String hey(String name){
        log.info("hey");
        int a = RandomUtils.nextInt(1,50);
        if(a>10){
            throw new RuntimeException("asdas");
        }
        return "Hi!"+name;
    }

    public String fallback(String name){
        log.info("fallback");
        return "fallback";
    }
}
