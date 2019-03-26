package com.zillionfortune.boss.web.controller.ams;

import java.math.BigDecimal;

import org.junit.Test;

import com.zillionfortune.boss.common.utils.HttpClientUtil;
import com.zillionfortune.boss.common.utils.JsonUtils;
import com.zillionfortune.boss.web.controller.ams.vo.FinanceSubjectAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.FinanceSubjectQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.FinanceSubjectQueryVO;

public class FinanceSubjectControllerTest {
	
	private final static String ADD_REQUET_URl="http://127.0.0.1:8080/boss/financesubjectservice/add.html";
	private final static String QUERY_LIST_REQUET_URl="http://127.0.0.1:8080/boss/financesubjectservice/querylist.html";
	private final static String QUERY_REQUET_URl="http://127.0.0.1:8080/boss/financesubjectservice/query.html";
	@Test
	public void addTest() {
		String sendData="";
		FinanceSubjectAddVO vo=new FinanceSubjectAddVO();
		vo.setBusinessScope("123");
		vo.setCertNo("123456789012345678");
		vo.setCertType(Integer.valueOf(2));
		vo.setIntroduction("");
		vo.setLegalPersonCertNo("123456789012345678");
		vo.setLegalPersonName("SYS");
		vo.setRegisteredCapital(BigDecimal.valueOf(100001));
		vo.setSubjectAddress("T10001");
		vo.setSubjectName("发行方00088");
		
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
		FinanceSubjectQueryListVO vo=new FinanceSubjectQueryListVO();
		vo.setSubjectName("发行方");
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
		FinanceSubjectQueryVO vo=new FinanceSubjectQueryVO();
		vo.setSubjectCode("FS17050001");
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
