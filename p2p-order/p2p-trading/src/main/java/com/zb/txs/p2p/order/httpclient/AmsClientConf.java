package com.zb.txs.p2p.order.httpclient;

import com.zb.txs.p2p.business.invest.repose.ProductRelationResp;
import com.zb.txs.p2p.business.invest.request.ProductLoanRelationReq;
import com.zb.txs.p2p.business.order.response.ProxyResp;
import feign.Feign;
import feign.Headers;
import feign.Logger;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by limingxin on 2018/1/19.
 */
@Configuration
public class AmsClientConf {
    @Value("${financialAms.domain}")
    private String financialAmsDomain;

    @Bean
    public AmsClient newMessageSender() {
        return Feign.builder().encoder(new GsonEncoder())
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(AmsClient.class, financialAmsDomain);
    }

    public interface AmsClient {
        @RequestLine("POST /internal/queryProductLoanRelationList.json")
        @Headers("Content-Type: application/json")
        ProxyResp<ProductRelationResp> queryProductLoanRelationList(ProductLoanRelationReq req);
    }
}
