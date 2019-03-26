package com.zb.txs.p2p.business.asset.feign;

import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.asset.request.AssetRequest;
import com.zb.txs.p2p.business.asset.response.AssetResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Function: 通过feign调用Eureka的p2p-trading服务产品相关的接口
 * Date: 2017年10月16  21:38
 *
 * @author: huangmian@zillionfortune.com
 * @version:版本
 * @since: JDK1.8
 */

@FeignClient(name = "p2p-trading") //,url = "http://192.168.60.149:8999"
public interface AssetClient {

    /**
     * 查询网贷总资产
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/p2p/msd/order/getassetsamount", method = RequestMethod.POST)
    Result<AssetResponse> getTotalAssets(@RequestBody AssetRequest request);

    /**
     * 验证用户是否可以解绑卡
     *
     * @param memberId
     * @return
     */
    @PostMapping(value = "/p2p/msd/order/iscanunbindcard/{memberId}")
    Result<Boolean> isCanUnBindCard(@PathVariable("memberId") String memberId);
}
