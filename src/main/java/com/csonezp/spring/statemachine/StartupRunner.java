package com.csonezp.spring.statemachine;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

/**
 * @author : zp226245
 * @date : 2020/4/26 11:50
 */
@Component
public class StartupRunner implements CommandLineRunner {

    @Resource
    StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
    }
}
