package com.zb.p2p.trade.api;

import com.zb.p2p.trade.api.req.CashRecordReq;
import com.zb.p2p.trade.api.req.RepaymentReq;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.persistence.dto.CashRecordDto;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p> 借款兑付服务API </p>
 *
 * @author Vinson
 * @version CashManagerAPI.java v1.0 2018/4/26 14:20 Zhengwenquan Exp $
 */

@FeignClient(name = "p2p-trade",configuration = Slf4jLogger.class)
public interface CashManagerAPI {

	/**
	 * 查询兑付信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cash/queryCashPlan", method = RequestMethod.POST)
	CommonResp<List<CashRecordDto>> queryCashPlan(@RequestBody CashRecordReq req);

	/**
	 * 还款(3.0)
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cash/repayment", method = RequestMethod.POST)
	CommonResp repayment(@RequestBody RepaymentReq req);
}
