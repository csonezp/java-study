package com.csonezp.spring.statemachine;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zp226245
 * @date : 2020/4/26 11:48
 */
@RestController
public class SSMController {
    @Resource
    StateMachine<States, Events> stateMachine;
    @GetMapping("/state")
    public String changeState(Events events){
        stateMachine.sendEvent(events);
        return "success";
    }

    @GetMapping("/online")
    public String online(){
        stateMachine.sendEvent(Events.ONLINE);
        return stateMachine.getState().toString();
    }

    @GetMapping("/publish")
    public String publish(){
        stateMachine.sendEvent(Events.PUBLISH);
        return stateMachine.getState().toString();
    }


    @GetMapping("/rollback")
    public String rollback(){
        stateMachine.sendEvent(Events.ROLLBACK);
        return stateMachine.getState().toString();
    }
}
