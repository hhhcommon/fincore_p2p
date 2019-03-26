package com.zb.p2p.trade.service.message;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by limingxin on 2018/1/15.
 */
@Configuration
public class MessageConfig {
    @Value("${message.host:http://127.0.0.1:8080}")
    private String messageHost;

    @Bean
    public MessageSender newMessageSender() {
        return Feign.builder().encoder(new GsonEncoder())
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(MessageSender.class, messageHost);
    }
}
