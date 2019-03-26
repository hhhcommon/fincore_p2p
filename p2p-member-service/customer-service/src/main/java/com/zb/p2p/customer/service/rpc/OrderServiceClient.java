package com.zb.p2p.customer.service.rpc;

import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.model.ExternalBaseRes;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zb.p2p.customer.service.bo.HoldTotalAssetsBO;
import com.zb.p2p.customer.service.bo.HoldTotalAssetsReq;
import com.zb.p2p.customer.service.bo.MyAssertEntity;

import feign.slf4j.Slf4jLogger;

/**
 * Created by laoguoliang on 2017/10/19 0013.
 */
@FeignClient(value = "p2p-trading",configuration = Slf4jLogger.class)
@RequestMapping(  produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public interface OrderServiceClient {
	/**
	 * 我的资产及收益
	 * @param customerId
	 * @return
	 */
   /* @RequestMapping(value = "getAssetsAmount", method = RequestMethod.POST)
	BaseRes<MyAssertEntity> getAssetsAmount(Long customerId);*/
    
	/**
	 * 查询支付中的订单数量
	 * @param customerId
	 * @return
	 */
   /* @RequestMapping(value = "selectPayingOrderCount", method = RequestMethod.POST)
    BaseRes<Object> selectPayingOrderCount(Long customerId);*/
	
	/**
	 *  获取持仓金额
	 *  param customerId
	 *  @param interestDate  当天日期YYYYMMDD
	 * @return
	 */
	@RequestMapping(value = "p2p/trading/investment/holdTotalAssets", method = RequestMethod.POST)
	ExternalBaseRes<HoldTotalAssetsBO> holdTotalAssets(HoldTotalAssetsReq holdTotalAssetsReq);
	
	
}
