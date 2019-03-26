package com.zb.p2p.customer.api.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 页面封装类型
 *
 */
@ApiModel
public class Page<T> implements Serializable{

	private static final long serialVersionUID = -7451966886443855345L;
	@ApiModelProperty(value="当前页面页数")
	private int pageNo;	//当前页面页数
	@ApiModelProperty(value="每页的行数")
	private int pageSize; 	//每页的行数
	@ApiModelProperty(value="总页数")
	private int totalPage;		//总页数
	@ApiModelProperty(value="所有记录的条数")
	private int totalRecordsCount;	//所有记录的条数
	//@ApiModelProperty(value="当前页面第一条记录的在总的记录中的顺序数")
	private int currentPageIndex;	//当前页面第一条记录的在总的记录中的顺序数（第n条记录的n）
	
	private List<T> rows;
	
	public Page(){
		this.pageSize = 10;
	}
	@SuppressWarnings("rawtypes")
	public Page(Page p){
		this.pageNo = p.getPageNo();
		this.pageSize = p.getPageSize();
		this.totalPage = p.getTotalPage();
		this.totalRecordsCount = p.totalRecordsCount;
		
	}

	public int getPageNo() {
		return pageNo==0?1:pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
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
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
