package com.csonezp.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HystrixController {

    @Autowired
    HystrixDemo hystrixDemo;

    @RequestMapping("/hystrix")
    public Object hystrix(){
        new Thread(() -> {
            for (int i = 0; i < 2000;i++){
                hystrixDemo.hey("asd");
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }).start();
        return "success";
    }
}
