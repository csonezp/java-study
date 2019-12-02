package com.csonezp.aop;

import com.csonezp.mock.ThirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @Autowired
    EchoService echoService;

    @Autowired
    ThirdService thirdService;

    @ParamPrint
    @RequestMapping("/echo")
    public String echo(String name){
        return thirdService.test();
    }
}
