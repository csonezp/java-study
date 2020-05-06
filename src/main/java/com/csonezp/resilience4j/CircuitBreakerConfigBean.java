package com.csonezp.resilience4j;

import java.time.Duration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.StateTransition;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zp226245
 * @date : 2020/5/2 18:05
 */
@Configuration
@Slf4j
public class CircuitBreakerConfigBean {

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(20)
            .slowCallRateThreshold(10)
            .slowCallDurationThreshold(Duration.ofMillis(1000))
            .permittedNumberOfCallsInHalfOpenState(10)
            .slidingWindowType(SlidingWindowType.TIME_BASED)
            .slidingWindowSize(100)
            .minimumNumberOfCalls(20)
            .waitDurationInOpenState(Duration.ofSeconds(10))
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .recordExceptions(BizException.class)
            //.ignoreExceptions(BusinessException.class, OtherBusinessException.class)
            .build();
        return circuitBreakerConfig;
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(CircuitBreakerConfig circuitBreakerConfig) {
        CircuitBreakerRegistry circuitBreakerRegistry =
            CircuitBreakerRegistry.of(circuitBreakerConfig);
        return circuitBreakerRegistry;
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("remoteService");
        // 分类监听事件
        circuitBreaker.getEventPublisher()
            //.onSuccess(event -> log.info("success!" + event.toString()))
            //.onError(event -> log.info("error!" + event.toString()))
            //.onIgnoredError(event -> log.info("ignored!" + event.toString()))
            //.onReset(event -> log.info("reset!" + event.toString()))
            .onStateTransition(event -> {
                log.info("state transition!" + event.toString());
                StateTransition stateTransition = event.getStateTransition();
                switch (stateTransition){
                    case CLOSED_TO_OPEN:log.error("断路器打开！开始降级！");break;
                    case OPEN_TO_HALF_OPEN:log.error("断路器半开！开始恢复！");break;
                    case HALF_OPEN_TO_CLOSED:log.error("断路器关闭！恢复正常！");break;
                    case HALF_OPEN_TO_OPEN:log.error("断路器打开！恢复失败！");break;
                    default:log.info(stateTransition.toString());
                }
            });
        return circuitBreaker;
    }

}
