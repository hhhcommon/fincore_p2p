/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.common.dto;

import java.io.Serializable;

/**
 * ClassName: BaseWebResponse <br/>
 * Function: 相应对象基础类封装. <br/>
 * Date: 2016年11月8日 上午9:48:40 <br/>
 *
 * @author Administrators
 * @version 
 * @since JDK 1.7
 */
public class BaseWebPageResponse extends BaseWebResponse implements Serializable {

    private static final long serialVersionUID = 92562941371458897L;
 
    /** 结果集总数 */
    private Integer totalCount;

    /** 总页数 */
    private Integer totalPage;
    
    /** 每页条数 */
    private Integer pageSize;
    
    /** 当前页 */
    private Integer pageNo;

    public BaseWebPageResponse() {
		
	}

	public BaseWebPageResponse(String respCode) {
		super(respCode);
	}


	public BaseWebPageResponse(String respCode, String resultMsg) {
		super(respCode,resultMsg);
	}

	public BaseWebPageResponse(String respCode, String resultCode,String resultMsg) {
		super(respCode,resultCode,resultMsg);
	}

	public BaseWebPageResponse(String respCode, String resultCode,String resultMsg,
			Integer totalCount, Integer totalPage,
			Integer pageSize, Integer pageNo) {
		super(respCode,resultCode,resultMsg);
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		if (totalCount != null && pageSize != null && pageSize > 0) {
			totalPage = totalCount%pageSize > 0 ? totalCount/pageSize + 1 :totalCount/pageSize;
		}
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}	

}