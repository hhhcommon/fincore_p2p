package com.zb.p2p.trade.client.config;

import com.zb.fincore.common.code.AesUtil;
import com.zb.p2p.trade.client.ams.AssetManagerClient;
import com.zb.p2p.trade.client.member.TxsMemberClient;
import com.zb.p2p.trade.client.order.TxsOrderClient;
import com.zb.p2p.trade.client.signstamper.SignStamperClient;
import feign.Feign;
import feign.Logger;
import feign.ResponseMapper;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p> 所有FeignClient配置Bean </p>
 *
 * @author Vinson
 * @version AllClientConfig.java v1.0 2018/5/23 9:50 Zhengwenquan Exp $
 */
@Configuration
public class AllClientConfig {

    @Value("${env.amsHost}")
    private String amsHost;

    @Bean
    public AssetManagerClient newAssetManagerClient() {
        return Feign.builder().client(new feign.okhttp.OkHttpClient()).encoder(new GsonEncoder()).retryer(Retryer.NEVER_RETRY)
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(AssetManagerClient.class, amsHost);
    }

    @Value("${env.txsOrder.host:http://127.0.0.1:8080}")
    private String txsOrder;
    @Value("${env.member.host:http://127.0.0.1:8080}")
    private String memberHost;
    @Value("${env.signStamper.host}")
    private String signStamperHost;

    @Value("${env.aes.decode:true}")
    private boolean decode;
    @Value("${env.aes.key:9999}")
    private String key;
    private AesRequestIntercept aesRequestIntercept;
    private ResponseMapper responseMapper;
    private AesUtil aesEncoder;

    @PostConstruct
    public void initAesEncryptAndDecrypt() {
        aesEncoder = new AesUtil();
        aesEncoder.setKey(key);
        aesRequestIntercept = new AesRequestIntercept(decode, aesEncoder);
        responseMapper = new AesResponseHandler(decode, aesEncoder);
    }

    @Bean
    public TxsMemberClient newTxsMemberClient() {
        return Feign.builder().requestInterceptor(aesRequestIntercept).encoder(new GsonEncoder())
                .mapAndDecode(responseMapper, new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(TxsMemberClient.class, memberHost);
    }

    @Bean
    public TxsOrderClient newTxsOrderClient() {
        return Feign.builder().requestInterceptor(aesRequestIntercept).encoder(new GsonEncoder())
                .mapAndDecode(responseMapper, new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(TxsOrderClient.class, txsOrder);
    }

    @Bean
    public SignStamperClient newSignStamperClient() {
        return Feign.builder().client(new feign.okhttp.OkHttpClient()).encoder(new GsonEncoder()).retryer(Retryer.NEVER_RETRY)
                .decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .target(SignStamperClient.class, signStamperHost);
    }
}
