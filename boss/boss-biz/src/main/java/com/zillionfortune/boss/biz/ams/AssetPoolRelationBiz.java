package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.CreateAssetPoolRelationRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolAssetListRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * 资产资产池关系业务处理
 * 
 * @author litaiping
 *
 */
public interface AssetPoolRelationBiz {
	/** 添加资产资产池关系 */
	BaseWebResponse createAssetPoolRelation(CreateAssetPoolRelationRequest req);
	/** 查询资产池可匹配资产列表 */
	BaseWebResponse queryPoolAssetList(QueryPoolAssetListRequest req);
}
