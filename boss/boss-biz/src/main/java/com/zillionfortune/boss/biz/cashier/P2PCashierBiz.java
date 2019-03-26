package com.zillionfortune.boss.biz.cashier;

import com.zillionfortune.boss.biz.cashier.model.LoanWithdrawalRetryRequest;
import com.zillionfortune.boss.biz.cashier.model.QueryTradeDatasForManualRequest;
import com.zillionfortune.boss.biz.cashier.model.QueryTradeDatasRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

public interface P2PCashierBiz {

	BaseWebResponse queryTradeDatasForManual(QueryTradeDatasForManualRequest req);

	BaseWebResponse loanWithdrawalRetry(LoanWithdrawalRetryRequest req);

	BaseWebResponse queryTradeDatas(QueryTradeDatasRequest req);

}
