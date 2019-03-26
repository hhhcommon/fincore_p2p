package com.zillionfortune.boss.web.controller.ams.vo;

public class PoolAssetQueryListVO {

	/** 资产池编码 */
	private String poolCode;
	
	/** 哪一页 */
	private int pageNo;
	
	/** 每页多少条 */
	private int pageSize;


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

	public String getPoolCode() {
		return poolCode;
	}

	public void setPoolCode(String poolCode) {
		this.poolCode = poolCode;
	}
	
}
