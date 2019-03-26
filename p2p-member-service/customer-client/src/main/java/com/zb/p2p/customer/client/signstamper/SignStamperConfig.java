package com.zb.p2p.customer.client.signstamper;

import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 调用马上贷对外接口服务Client </p>
 *
 * @author Vinson
 * @version SignStamperConfig.java v1.0 2018/3/9 14:20 Zhengwenquan Exp $
 */
@Configuration
public class SignStamperConfig {

    @Value("${env.signStamperHost}")
    private String signStamperHost;

    @Bean
    public SignStamperClient newSignStamperClient() {
        return Feign.builder().encoder(new GsonEncoder()).retryer(Retryer.NEVER_RETRY)
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(SignStamperClient.class, signStamperHost);
    }
}
