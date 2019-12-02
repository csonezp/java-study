package com.csonezp.mock;

import org.springframework.stereotype.Service;

/**
 * @author : peng.zhang33
 * @date : 2019-10-21 18:57
 */
@Service
public class ThirdServiceImpl implements ThirdService {
    @Override
    public String test() {
        return "third service";
    }
}
