package com.zillionfortune.boss.web.controller.ams;

import org.junit.Test;

import com.zillionfortune.boss.common.utils.HttpClientUtil;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.web.controller.ams.vo.AssetApprovaVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetQueryVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryAssetApprovalListVO;

public class AssetApprovalControllerTest {
	
	private final static String ADD_REQUET_URl="http://127.0.0.1:8080/boss/assetapprovalservice/approveasset.html";
	private final static String QUERY_LIST_REQUET_URl="http://127.0.0.1:8080/boss/assetapprovalservice/queryassetapprovallist.html";
	@Test
	public void addTest() {
		String sendData="";
		AssetApprovaVO vo=new AssetApprovaVO();
		vo.setApprovalBy("SYS");
		vo.setApprovalStatus(Integer.valueOf(3));
		vo.setApprovalSuggestion("");
		vo.setAssetCode("AMA170500012");
		vo.setSign("A");
		
		sendData=JsonUtils.object2Json(vo);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(ADD_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryListTest() {
		String sendData="";
		QueryAssetApprovalListVO vo=new QueryAssetApprovalListVO();
		//vo.setAssetCode(assetCode);
		vo.setAssetName("资产00012");
		vo.setAssetType(Integer.valueOf(1));
		//vo.setCollectStatus(collectStatus);
		vo.setFinanceSubjectCode("FS17050001");
		vo.setPageNo(0);
		vo.setPageSize(10);
		sendData=JsonUtils.object2Json(vo);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(QUERY_LIST_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
