package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.ApproveAssetRequest;
import com.zb.fincore.ams.facade.dto.req.QueryAssetApprovalListRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * 资产审核业务处理
 * 
 * @author litaiping
 *
 */
public interface AssetApprovalBiz {
	/** 资产审核 */
	BaseWebResponse approveAsset(ApproveAssetRequest req);
	/** 查询资产审核记录列表 */
	BaseWebResponse queryAssetApprovalList(QueryAssetApprovalListRequest req);
}
