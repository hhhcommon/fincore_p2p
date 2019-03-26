package com.zillionfortune.boss.web.controller.ams;

import org.junit.Test;

import com.zb.fincore.ams.facade.dto.p2p.req.ApprovalLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.QueryAssetRepayPlanListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryProductLoanRelationListRequest;
import com.zb.fincore.common.utils.HttpClientUtil;
import com.zb.fincore.common.utils.JsonUtils;

public class AssetForP2P2ControllerTest {
	
	private final static String QUERY_PRODUCT_LOAN_RELATION_LIST_REQUET_URl="http://192.168.43.162:8080/boss/p2passetservice/queryproductloanrelationlist.html";
	
	private final static String QUERY_LOAN_INFO_LIST_REQUET_URl="http://192.168.43.162:8080/boss/p2passetservice/queryloaninfolist.html";
	
	private final static String QUERY_LOAN_INFO_DETAIL_REQUET_URl="http://192.168.43.162:8080/boss/p2passetservice/queryloaninfodetail.html";
	
	private final static String APPROVAL_LOAN_INFO_REQUET_URl="http://192.168.43.162:8080/boss/p2passetservice/approvalloaninfo.html";
	
	private final static String QUERY_OVERDUE_LT30_LOANREPAYPLANLIST_REQUET_URl="http://192.168.43.162:8080/boss/p2passetservice/queryoverduedayslt30repayplanlist.html";
	
	private final static String QUERY_OVERDUE_GT30_LOANREPAYPLANLIST_REQUET_URl="http://192.168.43.162:8080/boss/p2passetservice/queryoverduedaysgt30repayplanlist.html";
	
	
	
	
	@Test
	public void queryProductLoanRelationList() {
		String sendData="";
		QueryProductLoanRelationListRequest req=new QueryProductLoanRelationListRequest();
		//req.setFinanceSubjectName("XT123");
		sendData=JsonUtils.object2Json(req);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(QUERY_PRODUCT_LOAN_RELATION_LIST_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryLoanInfoListTest() {
		String sendData="";
		QueryLoanInfoRequest req=new QueryLoanInfoRequest();
		req.setLoanStatus(Integer.valueOf(0));
		sendData=JsonUtils.object2Json(req);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(QUERY_LOAN_INFO_LIST_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryLoanInfoDetailTest() {
		String sendData="";
		QueryLoanInfoRequest req=new QueryLoanInfoRequest();
		req.setId(Long.valueOf(82));
		sendData=JsonUtils.object2Json(req);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(QUERY_LOAN_INFO_DETAIL_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void approvalLoanInfoTest() {
		String sendData="";
		ApprovalLoanInfoRequest req=new ApprovalLoanInfoRequest();
		req.setLoanOrderNo("201852051506410");
		req.setApprovalStatus(Integer.valueOf(1));
		req.setApprovalSuggestion("审核通过");
		req.setApprovalBy("admin");
		sendData=JsonUtils.object2Json(req);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(APPROVAL_LOAN_INFO_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void queryOverdueLT30AssetRepayPlanListTest() {
		String sendData="";
		QueryAssetRepayPlanListRequest req=new QueryAssetRepayPlanListRequest();
		req.setPageNo(1);
		req.setPageSize(10);
		sendData=JsonUtils.object2Json(req);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(QUERY_OVERDUE_LT30_LOANREPAYPLANLIST_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryOverdueGT30AssetRepayPlanListTest() {
		String sendData="";
		QueryAssetRepayPlanListRequest req=new QueryAssetRepayPlanListRequest();
		req.setPageNo(1);
		req.setPageSize(10);
		sendData=JsonUtils.object2Json(req);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(QUERY_OVERDUE_GT30_LOANREPAYPLANLIST_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
