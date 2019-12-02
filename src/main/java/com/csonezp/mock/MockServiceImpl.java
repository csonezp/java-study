package com.csonezp.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : peng.zhang33
 * @date : 2019-10-21 18:53
 */
@Service
public class MockServiceImpl implements MockService {

    @Autowired
    SecService secService;
    @Override
    public String test() {
        return secService.test() + ",mock service";
    }
}
