package com.zillionfortune.boss.web.controller.ams.vo;
/**
 * 查询资产池列表请求VO
 * 
 * @author litaiping
 *
 */
public class AssetPoolQueryListVO {
	/** 资产池名称 */
	private String poolName;
	
	/** 资产池类型(交易结构)1:定向委托 */
	private Integer poolType;
	
	/** 发行方编码 */
	private String financeSubjectCode;
	
	/** 哪一页 */
	private int pageNo;
	
	/** 每页多少条 */
	private int pageSize;

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public Integer getPoolType() {
		return poolType;
	}

	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}

	public String getFinanceSubjectCode() {
		return financeSubjectCode;
	}

	public void setFinanceSubjectCode(String financeSubjectCode) {
		this.financeSubjectCode = financeSubjectCode;
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
