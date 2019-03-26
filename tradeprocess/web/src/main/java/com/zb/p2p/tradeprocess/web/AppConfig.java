package com.zb.p2p.tradeprocess.web;

import com.zb.cloud.logcenter.http.filter.ZbHttpFilter;
import com.zb.fincore.common.code.AesUtil;
import com.zb.fincore.common.filter.FincoreHttpAesFilter;
import com.zb.fincore.common.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limingxin on 2017/8/26.
 */
@Configuration
public class AppConfig {

    @Autowired
    SpringUtils springUtils;
    @Value("${aes.decode:true}")
    String decode;
    @Value("${aes.key:9999}")
    String key;

    @Bean(name = "aesEncoder")
    @Order(1)
    public AesUtil newAesEncoder() {
        AesUtil aesEncoder = new AesUtil();
        aesEncoder.setKey(key);
        return aesEncoder;
    }

   /* @Bean
    @Order(20)
    public FilterRegistrationBean newFincoreHttpAesFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new FincoreHttpAesFilter());
        Map<String, String> initParam = new HashMap<String, String>();
        initParam.put("decode", decode);
        registrationBean.setInitParameters(initParam);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
        return registrationBean;
    }*/


    @Bean
    public FilterRegistrationBean newZbHttpFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ZbHttpFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
        return registrationBean;
    }
}
