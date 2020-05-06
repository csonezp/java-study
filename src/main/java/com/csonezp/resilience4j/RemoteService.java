package com.csonezp.resilience4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * @author : zp226245
 * @date : 2020/5/1 14:56
 */
@Slf4j
@Component
class RemoteService {
    private static final String CIRCUIT_BREAKER_NAME = "remoteService";

    AtomicInteger pass = new AtomicInteger(0);
    AtomicInteger fail = new AtomicInteger(0);

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "fallback")
    public Integer process(Boolean alwaysSuccess) throws BizException {

        log.info("real process");

        int time = 60;
        System.out.println("time:" + time);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int res = RandomUtils.nextInt(1, 11);
        if (alwaysSuccess) {
            res = 1;
        }
        System.out.println("real result:" + res);
        if (res > 20) {
            System.out.println("fail:" + fail.incrementAndGet());
            throw new BizException();
        }
        System.out.println("success:" + pass.incrementAndGet());
        return res;
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "fallback")
    public Integer process2(Boolean alwaysSuccess) throws BizException {

        log.info("real process");

        int time = 60;
        System.out.println("time:" + time);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int res = RandomUtils.nextInt(1, 11);
        if (alwaysSuccess) {
            res = 1;
        }
        System.out.println("real result:" + res);
        if (res > 20) {
            System.out.println("fail:" + fail.incrementAndGet());
            throw new BizException();
        }
        System.out.println("success:" + pass.incrementAndGet());
        return res;
    }

    /**
     * 兜底降级
     *
     * @param throwable
     * @return
     */
    public Integer fallback(Throwable throwable) {
        log.info("兜底降级");
        return -1;
    }

    /**
     * @return
     * @pa ram throwable
     */
    public Integer fallback(BizException throwable) {
        log.info("业务异常降级");
        return -1;
    }
}
