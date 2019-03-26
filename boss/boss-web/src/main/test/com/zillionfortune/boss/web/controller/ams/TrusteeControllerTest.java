package com.zillionfortune.boss.web.controller.ams;

import java.math.BigDecimal;

import org.junit.Test;

import com.zillionfortune.boss.common.utils.HttpClientUtil;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.web.controller.ams.vo.TrusteeAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.TrusteeQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.TrusteeQueryVO;

public class TrusteeControllerTest {
	
	private final static String ADD_REQUET_URl="http://192.168.5.219:8080/boss/trusteeservice/add.html";
	private final static String QUERY_LIST_REQUET_URl="http://192.168.5.227:8080/boss/trusteeservice/querylist.html";
	private final static String QUERY_REQUET_URl="http://192.168.5.219:8080/boss/trusteeservice/query.html";
	@Test
	public void addTest() {
		String sendData="";
		TrusteeAddVO vo=new TrusteeAddVO();
		vo.setBusinessScope("123");
		vo.setCertNo("123456789012345678");
		vo.setCertType(Integer.valueOf(2));
		vo.setIntroduction("");
		vo.setLegalPersonCertNo("123456789012345678");
		vo.setLegalPersonName("SYS");
		vo.setRegisteredCapital(BigDecimal.valueOf(100001));
		vo.setTrusteeAddress("T10001");
		vo.setTrusteeName("承销方00088");
		
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
		TrusteeQueryListVO vo=new TrusteeQueryListVO();
		vo.setTrusteeName("承销方");
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
		TrusteeQueryVO vo=new TrusteeQueryVO();
		vo.setTrusteeCode("TR17050003");
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
