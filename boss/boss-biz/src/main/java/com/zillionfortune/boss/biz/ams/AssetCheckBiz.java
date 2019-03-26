package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.QueryAssetTransactionRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

public interface AssetCheckBiz {
	public BaseWebResponse queryInProcessAsset();
	
	public BaseWebResponse queryNotInProcessAsset(String date);
	
	public BaseWebResponse queryAssetTransaction(QueryAssetTransactionRequest req);
}
