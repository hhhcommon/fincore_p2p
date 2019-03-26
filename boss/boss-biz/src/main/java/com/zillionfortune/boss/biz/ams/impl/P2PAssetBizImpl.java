package com.zillionfortune.boss.biz.ams.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.AssetRepayPlanForP2P3ServiceFacade;
import com.zb.fincore.ams.facade.DebtRightInfoFacade;
import com.zb.fincore.ams.facade.LoanRepayPlanServiceFacade;
import com.zb.fincore.ams.facade.dto.p2p.req.ApprovalLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.QueryAssetRepayPlanListRequest;
import com.zb.fincore.ams.facade.dto.req.CreateDebtRightInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryDebtRightInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryLoanRepayListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryProductLoanRelationListRequest;
import com.zb.fincore.ams.facade.model.DebtRightInfoModel;
import com.zb.fincore.ams.facade.model.LoanInfoModel;
import com.zillionfortune.boss.biz.ams.P2PAssetBiz;
import com.zillionfortune.boss.common.constants.Constants;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.utils.HttpClientUtil;

@Component
public class P2PAssetBizImpl implements P2PAssetBiz {

	private Logger logger = LoggerFactory.getLogger(P2PAssetBizImpl.class);

	/** 浜у搧鍏宠仈鍊熸璁㈠崟鍒楄〃 */
	@Autowired
	private LoanRepayPlanServiceFacade loanRepayPlanServiceFacade;
	
	@Autowired
	private AssetRepayPlanForP2P3ServiceFacade assetRepayPlanForP2P3ServiceFacade;

	@Autowired
	private DebtRightInfoFacade debtRightInfoFacade;

	@Value("${get_loan_agreement_url}")
	private String getLoanAgreemenUrl;

	@Override
	public BaseWebResponse queryProductLoanRelationList(QueryProductLoanRelationListRequest req) {
		logger.info("P2PAssetBizImpl.queryProductLoanRelationList.request:" + JSON.toJSONString(req));
		PageQueryResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = loanRepayPlanServiceFacade.queryProductLoanRelationList(req);
			logger.info("P2PAssetBizImpl.queryProductLoanRelationList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}
	
	@Override
	public BaseWebResponse queryLoanOutTimeList(QueryLoanRepayListRequest req) {
		logger.info("P2PAssetBizImpl.queryLoanOutTimeList.request:" + JSON.toJSONString(req));
		PageQueryResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = loanRepayPlanServiceFacade.queryLoanOutTimeList(req);
			logger.info("P2PAssetBizImpl.queryLoanOutTimeList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse createDebtRight(List<CreateDebtRightInfoRequest> req) {
		logger.info("P2PAssetBizImpl.createDebtRight.request:" + JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			//response = debtRightInfoFacade.createDebtRight(req);
			logger.info("P2PAssetBizImpl.createDebtRight.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse queryDebtRightList(QueryDebtRightInfoRequest req) {
		logger.info("P2PAssetBizImpl.queryDebtRightList.request:" + JSON.toJSONString(req));
		PageQueryResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = debtRightInfoFacade.queryDebtRightList(req);
			logger.info("P2PAssetBizImpl.queryDebtRightList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("P2PAssetBizImpl.queryDebtRightList",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse queryCashList(int pageSize, int pageNo) {
		logger.info("P2PAssetBizImpl.queryCashList.request:pageSize=" + pageSize + " pageNo=" + pageNo);
		PageQueryResponse response = null;
		BaseWebResponse resp = null;
		try {
			QueryDebtRightInfoRequest req = new QueryDebtRightInfoRequest();
			req.setPageNo(pageNo);
			req.setPageSize(pageSize);
			response = debtRightInfoFacade.queryCashList(req);
			logger.info("P2PAssetBizImpl.queryCashList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse queryCashDetailList(QueryDebtRightInfoRequest req) {
		logger.info("P2PAssetBizImpl.queryDebtRighDetailList.request:" + JSON.toJSONString(req));
		PageQueryResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = debtRightInfoFacade.queryCashDetailList(req.getProductCode());
			logger.info("P2PAssetBizImpl.queryCashDetailList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse queryDebtRighDetail(Long id) {
		logger.info("P2PAssetBizImpl.queryDebtRighDetail.request:" + JSON.toJSONString(id));
		QueryResponse<DebtRightInfoModel> response = null;
		BaseWebResponse resp = null;
		try {
			response = debtRightInfoFacade.queryDebtRighDetail(id);
			logger.info("P2PAssetBizImpl.queryDebtRighDetail.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("data", response.getData() != null ? response.getData() : null);
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse getLoanAgreement(String assetNo) {
		logger.info("P2PAssetBizImpl.getLoanAgreement.req:assetNo="+assetNo);
		BaseWebResponse resp = null;
		try {
			BaseResponse baseResponse = callMSDGetLoanAgreement(assetNo);
			if (ResultCode.SUCCESS.code().equals(baseResponse.getRespCode())) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
				resp.setData(baseResponse.getAddition());
			} else {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), baseResponse.getRespMsg());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		}

		logger.info("P2PAssetBizImpl.getLoanAgreement.resp:" + JSON.toJSONString(resp));
		return resp;
	}

	/**
	 * 璋冪敤鍞愬皬鍍ф煡璇細鍛樹俊鎭�
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callMSDGetLoanAgreement(String assetNo) throws Exception {
		BaseResponse resp = new BaseResponse();
		String requestUrl = getLoanAgreemenUrl + "&assetNo=" + assetNo;
		logger.info("璋冪敤椹笂璐疯幏鍙栧悎鍚岃姹傛姤鏂囷細" + requestUrl);
		String responseStr = HttpClientUtil.sendGetRequest(requestUrl, null);
		logger.info("璋冪敤椹笂璐疯幏鍙栧悎鍚屽搷搴旀姤鏂囷細" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			resp.setRespCode(Constants.SUCCESS_RESP_CODE);
			resp.setAddition(responseStr);
			return resp;
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("璋冪敤椹笂璐疯幏鍙栧悎鍚岀郴缁熷紓甯�");
		}

		return resp;
	}

	@Override
	public BaseWebResponse queryLoanInfoList(QueryLoanInfoRequest req) {
		logger.info("P2PAssetBizImpl.queryLoanInfoList.request:" + JSON.toJSONString(req));
		PageQueryResponse response = BaseResponse.build(PageQueryResponse.class);
		BaseWebResponse resp = null;
		try {
			response = (PageQueryResponse) loanRepayPlanServiceFacade.queryLoanInfoList(req);
			logger.info("P2PAssetBizImpl.queryLoanInfoList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;
	}

	@Override
	public BaseWebResponse queryLoanInfoDetail(QueryLoanInfoRequest req) {
		logger.info("P2PAssetBizImpl.queryLoanInfoDetail.request:" + JSON.toJSONString(req));
		QueryResponse<LoanInfoModel> response = null; response = BaseResponse.build(QueryResponse.class);
		BaseWebResponse resp = null;
		try {
			response = (QueryResponse<LoanInfoModel>) loanRepayPlanServiceFacade.queryLoanInfoDetail(req);
			logger.info("P2PAssetBizImpl.queryLoanInfoDetail.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("data", response.getData() != null ? response.getData() : null);
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;
	}

	@Override
	public BaseWebResponse approvalLoanInfo(ApprovalLoanInfoRequest req) {
		logger.info("P2PAssetBizImpl.approvalLoanInfo.request:" + JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = loanRepayPlanServiceFacade.approvalLoanInfo(req);
			logger.info("P2PAssetBizImpl.approvalLoanInfo.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse queryOverdueDaysLT30RepayPlanList(QueryAssetRepayPlanListRequest req) {
		logger.info("P2PAssetBizImpl.queryOverdueDaysLT30RepayPlanList.request:" + JSON.toJSONString(req));
		PageQueryResponse response = BaseResponse.build(PageQueryResponse.class);
		BaseWebResponse resp = null;
		try {
			response = (PageQueryResponse) assetRepayPlanForP2P3ServiceFacade.queryOverdueDaysLT30RepayPlanList(req);
			logger.info("P2PAssetBizImpl.queryOverdueDaysLT30RepayPlanList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;
	}

	@Override
	public BaseWebResponse queryOverdueDaysGT30RepayPlanList(QueryAssetRepayPlanListRequest req) {
		logger.info("P2PAssetBizImpl.queryOverdueDaysLT30RepayPlanList.request:" + JSON.toJSONString(req));
		PageQueryResponse response = BaseResponse.build(PageQueryResponse.class);
		BaseWebResponse resp = null;
		try {
			response = (PageQueryResponse) assetRepayPlanForP2P3ServiceFacade.queryOverdueDaysGT30RepayPlanList(req);
			logger.info("P2PAssetBizImpl.queryOverdueDaysLT30RepayPlanList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;
	}

}
