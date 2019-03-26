package com.zb.p2p.customer.client.txs;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 调用 服务Client配置 </p>
 * 
 */
@Configuration
public class TxsConfig {

    @Value("${env.txsHost}")
    private String txsHost;

    @Bean
    public TxsClient newTxsClient() {
        return Feign.builder().encoder(new GsonEncoder())
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(TxsClient.class, txsHost);
    }
}
