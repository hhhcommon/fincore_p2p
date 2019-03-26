package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.BatchCreateAssetRequest;
import com.zb.fincore.ams.facade.dto.req.CreateAssetRequest;
import com.zb.fincore.ams.facade.dto.req.QueryAssetListForManageRequest;
import com.zb.fincore.ams.facade.dto.req.QueryAssetRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolListRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * 资产务处理
 * 
 * @author litaiping
 *
 */
public interface AssetBiz {
	/** 添加资产池 */
	BaseWebResponse createAsset(CreateAssetRequest req);
	/** 批量添加资产池 */
	BaseWebResponse batchCreateAsset(BatchCreateAssetRequest req);
	/** 查询资产列表 */
	BaseWebResponse queryAssetList(QueryAssetListForManageRequest req);
	/** 查询资产详情 */
	BaseWebResponse queryAsset(QueryAssetRequest req);
	/** 查询资产审核列表 */
	BaseWebResponse queryAssetListForApproval(QueryAssetListForManageRequest req);

    public BaseWebResponse updateAssetPublishInfo(String assetCode, String publishInfo);
}
