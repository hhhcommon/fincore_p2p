package com.zb.p2p.customer.client.msdfinance;

import feign.Feign;
import feign.Logger;
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
 * @version MsdFinanceConfig.java v1.0 2018/3/9 14:20 Zhengwenquan Exp $
 */
@Configuration
public class MsdFinanceConfig {

    @Value("${env.msdHost}")
    private String msdHost;

    @Bean
    public MsdFinanceClient newMsdFinanceClient() {
        return Feign.builder().encoder(new GsonEncoder())
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(MsdFinanceClient.class, msdHost);
    }
}
