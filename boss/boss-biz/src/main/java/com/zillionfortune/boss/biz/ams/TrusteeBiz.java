package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.CreateTrusteeRequest;
import com.zb.fincore.ams.facade.dto.req.QueryTrusteeListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryTrusteeRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * 受托方业务处理
 * 
 * @author litaiping
 *
 */
public interface TrusteeBiz {
	/** 添加受托方 */
	BaseWebResponse createTrustee(CreateTrusteeRequest req);
	/** 查询受托方 */
	BaseWebResponse queryTrustee(QueryTrusteeRequest req);
	/** 查询受托方列表 */
	BaseWebResponse queryTrusteeList(QueryTrusteeListRequest req);
}
