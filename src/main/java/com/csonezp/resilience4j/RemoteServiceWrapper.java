package com.csonezp.resilience4j;

import java.io.IOException;
import java.util.function.Supplier;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : zp226245
 * @date : 2020/5/1 14:56
 */
@Slf4j
@Component
class RemoteServiceWrapper {

    @Autowired
    CircuitBreaker circuitBreaker;

    @Autowired
    RemoteService remoteService;

    public Integer process() {
        CheckedFunction0<Integer> decoratedSupplier = CircuitBreaker
            .decorateCheckedSupplier(circuitBreaker, remoteService::process);

        Try<Integer> result = Try.of(decoratedSupplier)
            .recover(throwable -> -3);
        return result.get();
    }

    /**
     * 兜底降级
     * @param throwable
     * @return
     */
    public Integer fallback(Throwable throwable){
        log.info("兜底降级");
        return -1;
    }

    /**
     * @pa ram throwable
     * @return
     */
    public Integer fallback(BizException throwable){
        log.info("业务异常降级");
        return -1;
    }
}
