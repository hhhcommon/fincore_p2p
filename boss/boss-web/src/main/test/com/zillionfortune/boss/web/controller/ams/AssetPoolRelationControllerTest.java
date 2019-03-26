package com.zillionfortune.boss.web.controller.ams;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.zillionfortune.boss.common.utils.HttpClientUtil;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolQueryVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolRelationAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolRelationQueryVO;

public class AssetPoolRelationControllerTest {
	private final static String ADD_REQUET_URl="http://127.0.0.1:8080/boss/assetpoolrelationservice/add.html";
	private final static String QUERY_LIST_REQUET_URl="http://127.0.0.1:8080/boss/assetpoolrelationservice/querylist.html";
	private final static String QUERY_REQUET_URl="http://127.0.0.1:8080/boss/assetpoolrelationservice/query.html";
	@Test
	public void addTest() {
		String sendData="";
		
		AssetPoolRelationAddVO vo=new AssetPoolRelationAddVO();
		List<String> assetCodeList=new ArrayList<String>();
		assetCodeList.add("AMA170500012");
		vo.setAssetCodeList(assetCodeList);
		vo.setPoolCode("AMP170500009");

		sendData=JsonUtils.object2Json(vo);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(ADD_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryListTest() {
		String sendData="";
		AssetPoolRelationQueryVO vo=new AssetPoolRelationQueryVO();
		vo.setPoolCode("AMP170500009");
		//vo.setPageNo(0);
		//vo.setPageSize(10);
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
