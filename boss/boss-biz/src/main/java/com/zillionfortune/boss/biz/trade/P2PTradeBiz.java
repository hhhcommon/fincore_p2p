package com.zillionfortune.boss.biz.trade;

import com.zillionfortune.boss.biz.trade.model.QueryTradeRecodeRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
/**
 * 交易接口
 * 
 * @author Administrator
 *
 */
public interface P2PTradeBiz {

	BaseWebResponse cash(String productCode);

	BaseWebResponse queryTradeCashErrorList(String productCode);

	BaseWebResponse queryTradeRecord(QueryTradeRecodeRequest req);

	BaseWebResponse queryLoanOrderInfo(String loanNo);

}
