package com.csonezp.resilience4j;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zp226245
 * @date : 2020/5/2 15:46
 */
@RestController
public class CircuitBreakerController {
    @Autowired
    RemoteService remoteService;

    @Autowired
    RemoteServiceWrapper remoteServiceWrapper;

    @GetMapping("/breaker")
    public Integer test() {
        return remoteServiceWrapper.process();
    }
}
