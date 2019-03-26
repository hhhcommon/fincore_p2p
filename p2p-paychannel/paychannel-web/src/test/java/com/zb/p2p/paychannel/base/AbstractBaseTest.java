package com.zb.p2p.paychannel.base;

import com.zb.p2p.paychannel.common.model.BaseRes;
import com.zb.p2p.paychannel.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"log.base=${P2P_CUSTOMER_SERVICE_CONF/log", "spring.config.location=${P2P_CUSTOMER_SERVICE_CONF}/config"})
@Slf4j
public class AbstractBaseTest {

    /**
     * Spring RestTemplate的便利替代
     */
    @Autowired
    protected TestRestTemplate testRestTemplate;

    private final String memberHost = "http://127.0.0.1:8762/";

    @Before
    public void before() {
        System.out.printf("### before ## config" );
    }


    protected <T> BaseRes<T> executeHttp(String mappingUrl, Object req) throws Exception {

        BaseRes<T> res;

        String url = this.memberHost + mappingUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String reqJson = JsonUtil.convertObjToStr(req);
        org.springframework.http.HttpEntity<String> strEntity = new org.springframework.http.HttpEntity<String>(reqJson, headers);

        log.info("call http test >>> url:{}，request：{}", url, reqJson);

        ParameterizedTypeReference<BaseRes<T>> ptr = new ParameterizedTypeReference<BaseRes<T>>() {
        };
        res = testRestTemplate.exchange(url, HttpMethod.POST, strEntity, ptr).getBody();

        log.info("call http test <<< code:{},msg:{},Data:{}", res.getCode(), res.getMessage(), res.getData());

        return res;
    }

}
