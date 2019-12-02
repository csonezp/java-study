package com.csonezp;

import com.csonezp.mock.ThirdService;
import com.csonezp.mock.ThirdServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * @author : peng.zhang33
 * @date : 2019-10-21 19:17
 */
@TestConfiguration
public class TestConfig {
    @Bean
    public ThirdService thirdService(){
        return Mockito.mock(ThirdService.class);
    }
}
