package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.QueryContractSignListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryContractSignRequest;
import com.zb.fincore.ams.facade.dto.req.UpdateContractSignStatusRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;


/**
 * 签章业务处理
 * 
 * @author litaiping
 *
 */
public interface SignBiz {

	BaseWebResponse queryContractSignList(QueryContractSignListRequest req);

	BaseWebResponse queryContractSignDetail(QueryContractSignRequest req);

	BaseWebResponse signContract(UpdateContractSignStatusRequest req);

	BaseWebResponse cancelSignContract(UpdateContractSignStatusRequest req);
	
}
