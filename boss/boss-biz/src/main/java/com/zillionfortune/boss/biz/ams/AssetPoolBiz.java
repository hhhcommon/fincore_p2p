package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.CreatePoolRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolLeftAssetRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * 资产池业务处理
 * 
 * @author litaiping
 *
 */
public interface AssetPoolBiz {
	/** 添加资产池 */
	BaseWebResponse createAssetPool(CreatePoolRequest req);
	/** 查询资产列表 */
	BaseWebResponse queryAssetPoolList(QueryPoolListRequest req);
	/** 查询资产详情 */
	BaseWebResponse queryAssetPool(QueryPoolRequest req);
    /** 剩余资产列表 */
    BaseWebResponse queryLeftAssetAmountList(QueryPoolLeftAssetRequest req);
}
