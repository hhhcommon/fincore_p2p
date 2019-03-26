package com.zb.p2p.paychannel.client.intercept;

import com.zb.fincore.common.code.AesUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * Created by limingxin on 2017/8/29.
 */
@Slf4j
public class AesRequestIntercept implements RequestInterceptor {
    boolean enableAesEncrypt;
    AesUtil aesEncoder;

    public AesRequestIntercept(boolean enableAesEncrypt, AesUtil aesEncoder) {
        this.enableAesEncrypt = enableAesEncrypt;
        this.aesEncoder = aesEncoder;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (enableAesEncrypt) {
            byte[] body = requestTemplate.body();
            try {
                requestTemplate.body(aesEncoder.encrypt(new String(body)).getBytes(), Charset.forName("utf8"));
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
