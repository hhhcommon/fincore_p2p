package com.zillionfortune.boss.biz.ams;

import java.util.List;

import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.facade.dto.p2p.req.ApprovalLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.QueryAssetRepayPlanListRequest;
import com.zb.fincore.ams.facade.dto.req.CreateDebtRightInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryDebtRightInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryLoanRepayListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryProductLoanRelationListRequest;
import com.zb.fincore.ams.facade.model.p2p.AssetRepayPlanModel;
import com.zillionfortune.boss.common.dto.BaseWebResponse;


/**
 * P2P资产务处理
 * 
 * @author litaiping
 *
 */
public interface P2PAssetBiz {

	/**
	 * 产品关联借款订单列表
	 * 
	 * @param req
	 * @return
	 */
	BaseWebResponse queryProductLoanRelationList(QueryProductLoanRelationListRequest req);


	BaseWebResponse queryDebtRighDetail(Long id);


	BaseWebResponse createDebtRight(List<CreateDebtRightInfoRequest> req); 


	BaseWebResponse queryDebtRightList(QueryDebtRightInfoRequest req);


	BaseWebResponse queryCashList(int pageSize, int pageNo);


	BaseWebResponse queryCashDetailList(QueryDebtRightInfoRequest req);


	BaseWebResponse getLoanAgreement(String assetNo);


	BaseWebResponse queryLoanInfoList(QueryLoanInfoRequest req);
	
	BaseWebResponse queryLoanOutTimeList(QueryLoanRepayListRequest req);
	
	BaseWebResponse queryLoanInfoDetail(QueryLoanInfoRequest req);
	
	BaseWebResponse approvalLoanInfo(ApprovalLoanInfoRequest req); 
	
	BaseWebResponse queryOverdueDaysLT30RepayPlanList(QueryAssetRepayPlanListRequest req);

	BaseWebResponse queryOverdueDaysGT30RepayPlanList(QueryAssetRepayPlanListRequest req);

}
