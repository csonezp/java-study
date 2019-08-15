package com.csonezp.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @Autowired
    EchoService echoService;

    @ParamPrint
    @RequestMapping("/echo")
    public String echo(String name){
        return echoService.echo(name,name);
    }
}
