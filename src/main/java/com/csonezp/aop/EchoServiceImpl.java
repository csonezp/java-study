package com.csonezp.aop;

import org.springframework.stereotype.Service;

@Service
public class EchoServiceImpl implements EchoService {
    @Override
    @ParamPrint
    public String echo(String name,String name2) {
        return "Hello!"+name;
    }
}
