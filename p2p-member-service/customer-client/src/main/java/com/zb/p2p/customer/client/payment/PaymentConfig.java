package com.zb.p2p.customer.client.payment;

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
 * <p> 调用支付服务Client配置 </p>
 *
 * @author Vinson
 * @version PaymentConfig.java v1.0 2018/3/9 14:20 Zhengwenquan Exp $
 */
@Configuration
public class PaymentConfig {

    @Value("${env.paymentHost}")
    private String paymentHost;

    @Bean
    public PaymentClient newPaymentClient() {
        return Feign.builder().encoder(new GsonEncoder()).retryer(Retryer.NEVER_RETRY)
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(PaymentClient.class, paymentHost);
    }
}
