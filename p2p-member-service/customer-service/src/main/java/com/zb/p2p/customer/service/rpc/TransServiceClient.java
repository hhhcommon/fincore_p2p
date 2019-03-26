package com.zb.p2p.customer.service.rpc;

import com.zb.p2p.customer.common.model.BaseRes;

import feign.slf4j.Slf4jLogger;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by laoguoliang on 2017/10/19 0013.
 */
@FeignClient(value = "qjs-trans-service",configuration = Slf4jLogger.class)
@RequestMapping(value = "/trans", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public interface TransServiceClient {
    /**
     * 查询是否在交易时间
     *
     * @param customerId
     * @return
     */
    @PostMapping(value = "/dealForbiddenTime")
    BaseRes<String> dealForbiddenTime();
}
