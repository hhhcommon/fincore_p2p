package com.zb.p2p.trade.persistence.page;


import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 页面封装类型
 * @author luiz
 *
 */
public class Page implements Serializable{

	private static final long serialVersionUID = 5576599530855010333L;
	private int pageNo;	//当前页面页数
	private int pageSize; 	//每页的行数
	private int totalPage;		//总页数
	private int totalRecordsCount;	//所有记录的条数
	private int currentPageIndex;	//当前页面第一条记录的在总的记录中的顺序数（第n条记录的n）
	private String id;//记录编号
	private String oper;//操作类型 增删改查
	private String sord;//排序类型
	private String sidx;//排序字段
	private String filters;
	@SuppressWarnings("rawtypes")
	private Map paramMap;


	@SuppressWarnings("rawtypes")
	public Page(){
		this.pageSize = 10;
		paramMap=new HashMap();
	}

	public int getPageNo() {
		return this.pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = (pageNo==0?1:pageNo);
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {

		if(totalRecordsCount%pageSize==0)
			totalPage = totalRecordsCount/pageSize;
		else
			totalPage = totalRecordsCount/pageSize+1;
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

	public int getCurrentPageIndex() {
		currentPageIndex=(pageNo-1)*pageSize;
		if(currentPageIndex<0)
			currentPageIndex = 0;
		return currentPageIndex;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getParamMap() {
		if(filters!=null){
			JSONObject filterObject= JSONObject.parseObject(this.filters);
			JSONArray filterRules=filterObject.getJSONArray("rules");
			for(int i=0;i<filterRules.size();i++){
				JSONObject paramData=filterRules.getJSONObject(i);
				//{"field":"dictType","op":"true","data":"2"}
				this.paramMap.put(paramData.get("field"),paramData.get("data"));
			}
		}
		if(StringUtils.isNotEmpty(sidx)){
			String sidxStr= com.zb.p2p.trade.common.util.StringUtils.convertStringAddChar(sidx,"_");
			this.sidx=sidxStr;
			//this.paramMap.put("sidx",sidxStr);
		}

		return paramMap;
	}

	@SuppressWarnings("rawtypes")
	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
}
