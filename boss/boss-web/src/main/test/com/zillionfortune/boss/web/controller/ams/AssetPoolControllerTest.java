package com.zillionfortune.boss.web.controller.ams;

import java.math.BigDecimal;

import org.junit.Test;

import com.zillionfortune.boss.common.utils.HttpClientUtil;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolQueryVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetQueryVO;

public class AssetPoolControllerTest {
	
	private final static String ADD_REQUET_URl="http://127.0.0.1:8080/boss/assetpoolservice/add.html";
	private final static String QUERY_LIST_REQUET_URl="http://127.0.0.1:8080/boss/assetpoolservice/querylist.html";
	private final static String QUERY_REQUET_URl="http://127.0.0.1:8080/boss/assetpoolservice/query.html";
	@Test
	public void addTest() {
		String sendData="";
		AssetPoolAddVO vo=new AssetPoolAddVO();
		vo.setLimitAmount(new BigDecimal(1000));
		vo.setPoolDesc("资产池描述1");
		vo.setPoolName("资产池名称1");
		vo.setPoolType(Integer.valueOf(1));
		vo.setTrusteeCode("TR17050001");
		vo.setFinanceSubjectCode("FS17050002");

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
		AssetPoolQueryListVO vo=new AssetPoolQueryListVO();
		vo.setPoolName("资产池名称1");
		vo.setPoolType(Integer.valueOf(1));
		vo.setFinanceSubjectCode("FS17050002");
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
	
	@Test
	public void queryTest() {
		String sendData="";
		AssetPoolQueryVO vo=new AssetPoolQueryVO();
		vo.setPoolCode("AMP170500009");
		sendData=JsonUtils.object2Json(vo);
		try {
			System.out.println("request:"+sendData);
			String respStr=HttpClientUtil.sendPostRequest(QUERY_REQUET_URl, sendData);
			System.out.println("response:"+respStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
