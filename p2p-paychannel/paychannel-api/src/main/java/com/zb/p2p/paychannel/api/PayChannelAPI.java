package com.zb.p2p.paychannel.api;

import com.zb.p2p.paychannel.api.req.OrderReq;
import com.zb.p2p.paychannel.api.common.CommonResp;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 查询会员信息API
 *
 */
@FeignClient(name = "p2p-paychannel", configuration = Slf4jLogger.class)
public interface PayChannelAPI {

    @RequestMapping(value = "/match/invest", method = RequestMethod.POST)
    CommonResp invest(@RequestBody OrderReq orderReq);
}
