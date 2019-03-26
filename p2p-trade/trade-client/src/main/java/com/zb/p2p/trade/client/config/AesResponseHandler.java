package com.zb.p2p.trade.client.config;

import com.zb.fincore.common.code.AesUtil;
import feign.Response;
import feign.ResponseMapper;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * Created by limingxin on 2017/9/4.
 */
@Slf4j
public class AesResponseHandler implements ResponseMapper {
    boolean enableAesEncrypt;
    AesUtil aesEncoder;

    public AesResponseHandler(boolean enableAesEncrypt, AesUtil aesEncoder) {
        this.enableAesEncrypt = enableAesEncrypt;
        this.aesEncoder = aesEncoder;
    }

    @Override
    public Response map(Response response, Type type) {
        if (enableAesEncrypt) {
            try {
                String resp = Util.toString(response.body().asReader());
                return response
                        .toBuilder()
                        .body(aesEncoder.decrypt(resp).getBytes())
                        .build();
            } catch (Exception e) {
                log.error("", e);
                return null;
            }
        } else {
            return response;
        }
    }
}
