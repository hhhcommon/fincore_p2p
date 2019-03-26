package com.zb.p2p.paychannel.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"log.base=${P2P_CUSTOMER_SERVICE_CONF/log", "spring.config.location=${P2P_CUSTOMER_SERVICE_CONF}/config"})
@AutoConfigureMockMvc
public class BaseControllerTest {
    @Autowired
    protected MockMvc mvc;

    @Before
    public void init() {
        System.out.println("####### initial ############" );
    }


}