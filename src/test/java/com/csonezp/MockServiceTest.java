package com.csonezp;

import com.csonezp.mock.MockService;
import com.csonezp.mock.ThirdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : peng.zhang33
 * @date : 2019-10-21 18:59
 */
@ImportAutoConfiguration(TestConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class MockServiceTest {

    @Autowired
    MockService mockService;


    @Autowired
    ThirdService thirdService;

    @Test
    public void test() {
        Mockito.when(thirdService.test()).thenReturn("mock");
        String test = mockService.test();
        System.out.println(test);
    }
}
