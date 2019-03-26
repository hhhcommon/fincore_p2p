package com.zillionfortune.boss.web.controller.ams;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.zillionfortune.boss.common.utils.HttpClientUtil;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.web.controller.ams.vo.AssetAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetQueryVO;

public class AssetControllerTest {
	
	private final static String ADD_REQUET_URl="http://127.0.0.1:8080/boss/assetservice/add.html";
	private final static String QUERY_LIST_REQUET_URl="http://192.168.5.239:8080/boss/assetservice/querylist.html";
	private final static String QUERY_REQUET_URl="http://192.168.5.239/boss/assetservice/query.html";
	@Test
	public void addTest() {
		String sendData="";
		AssetAddVO vo=new AssetAddVO();
		vo.setAssetAmount(new BigDecimal(10000));
		vo.setAssetName("资产00012");
		vo.setAssetType(Integer.valueOf(1));
		vo.setCreditMode("资产0007增信措施");
		vo.setEstablishTime(new Date());
		vo.setExpireTime(new Date());
		vo.setFinanceSubjectCode("FS17050001");
		vo.setProjectDesc("资产0007项目描述");
		vo.setRepayGuarenteeMode("资产0007还款保障措施");
		vo.setValueDays(Integer.valueOf(28));
		vo.setValueEndTime(new Date());
		vo.setValueStartTime(new Date());
		vo.setYieldRate(new BigDecimal("0.11"));
		vo.setRepaymentType(Integer.valueOf(1));
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
		AssetQueryListVO vo=new AssetQueryListVO();
		//vo.setAssetCode(assetCode);
		vo.setAssetName("资产0001112");
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
	
	@Test
	public void queryTest() {
		String sendData="";
		AssetQueryVO vo=new AssetQueryVO();
		vo.setAssetCode("AMA170500012");
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
