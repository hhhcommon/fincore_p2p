package com.zb.p2p.customer.service.rpc;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zb.p2p.customer.service.bo.UserInfoRequest;
//import com.zb.p2p.customer.service.bo.UserInfoResponse;

@FeignClient(value = "p2p-open-api")
@RequestMapping(value = "/out", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public interface OpenApiClient {
//	    @RequestMapping(value = "/dispatch", method = RequestMethod.POST)
//	    UserInfoResponse dispatch(@RequestBody UserInfoRequest req);
}
