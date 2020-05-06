package com.csonezp.resilience4j;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Integer test(@RequestParam(defaultValue = "false") Boolean success) {
        return remoteServiceWrapper.process(success);
    }

    @GetMapping("/breaker2")
    public Integer test2(@RequestParam(defaultValue = "false") Boolean success) throws BizException {
        return remoteService.process(success);
    }
}
