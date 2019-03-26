package com.zillionfortune.boss.web.controller.ams.vo;

import java.util.List;

/**
 * 添加资产资产池关系VO
 * 
 * @author litaiping
 *
 */
public class AssetPoolRelationAddVO {
	/** 资产池编码 */
	private String poolCode;
	
	private String createBy;
	
	/** 资产编码列表 */
	private List<String> assetCodeList;

	public String getPoolCode() {
		return poolCode;
	}

	public void setPoolCode(String poolCode) {
		this.poolCode = poolCode;
	}

	public List<String> getAssetCodeList() {
		return assetCodeList;
	}

	public void setAssetCodeList(List<String> assetCodeList) {
		this.assetCodeList = assetCodeList;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
}
