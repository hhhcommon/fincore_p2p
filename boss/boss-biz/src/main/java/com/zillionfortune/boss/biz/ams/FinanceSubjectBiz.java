package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.CreateFinanceSubjectRequest;
import com.zb.fincore.ams.facade.dto.req.QueryFinanceSubjectListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryFinanceSubjectRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * 发行方业务处理
 * 
 * @author litaiping
 *
 */
public interface FinanceSubjectBiz {
	/** 添加发行方 */
	BaseWebResponse createFinanceSubject(CreateFinanceSubjectRequest req);
	/** 查询发行方 */
	BaseWebResponse queryFinanceSubject(QueryFinanceSubjectRequest req);
	/** 查询发行方列表 */
	BaseWebResponse queryFinanceSubjectList(QueryFinanceSubjectListRequest req);
}
