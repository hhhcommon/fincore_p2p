package com.zb.p2p.trade.api;

import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by liguoliang on 2018/04/24 0013.
 */
@FeignClient(name = "p2p-trade",configuration = Slf4jLogger.class)
public interface TradeAPI {

//	/**
//	 * 预充值
//	 */
//	@RequestMapping(value = "/trade/precharge", method = RequestMethod.POST)
//    CommonResp<FundRecordRsp> precharge(@RequestBody FundRecordReq req);
//
//	/**
//	 * 充值提现
//	 */
//	@RequestMapping(value = "/trade/fundRecord", method = RequestMethod.POST)
//	CommonResp<FundRecordRsp> fundRecord(@RequestBody FundRecordReq req);
//
//	/**
//	 * 投资人退出
//	 */
//	@RequestMapping(value = "/transfer/apply", method = RequestMethod.POST)
//	CommonResp<FundRecordRsp> transferApply(@RequestBody TransferApplyReq req);
	
}
