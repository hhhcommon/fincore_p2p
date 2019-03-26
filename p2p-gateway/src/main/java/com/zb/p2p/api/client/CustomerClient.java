package com.zb.p2p.api.client;

import com.zb.p2p.customer.api.entity.CustomerReq;
import com.zb.p2p.customer.common.model.BaseRes;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("p2p-customer-service" )
public interface CustomerClient {

    /**
     * 根据loginToken获取会员ID
     *
     * @param CustomerReq
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login/getCustomerId" )
    BaseRes<String> getCustomerId(@RequestBody CustomerReq req);
}
