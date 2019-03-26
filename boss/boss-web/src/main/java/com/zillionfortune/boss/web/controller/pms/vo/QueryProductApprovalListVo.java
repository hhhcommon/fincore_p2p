/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.vo;

/**
 * ClassName: QueryProductApprovalListVo <br/>
 * Function: 产品审核信息列表查询用Vo. <br/>
 * Date: 2017年5月10日 下午2:16:29 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class QueryProductApprovalListVo {
	/** productCode:产品编码 **/
	private String productCode;
	
	/** pageNo:页码(当前页) **/
	private int pageNo;
	
	/** pageSize:分页大小(每页数量) **/
	private int pageSize;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getPageNo() {
		return pageNo;
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
}
