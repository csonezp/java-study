package com.csonezp.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : peng.zhang33
 * @date : 2019-10-21 18:56
 */
@Service
public class SecServiceImpl implements SecService {

    @Autowired
    ThirdService thirdService;
    @Override
    public String test() {
        return thirdService.test() + ",sec service";
    }
}
