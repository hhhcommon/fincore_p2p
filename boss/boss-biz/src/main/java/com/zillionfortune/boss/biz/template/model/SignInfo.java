package com.zillionfortune.boss.biz.template.model;


public class SignInfo {
	/** 角色Id */
	private Long roleId;
	
	/** 印章id */
	private String 	sealId;
	
	/** 关键字 */
	private String keyword1;
	
	/** 关键字 */
	private String keyword2;
	
	private String templateCode1;
	
	private String templateCode2;
	
	/** 关键字索引 */
	private int keywordIndex;
	
	/** 当前用户为第个次签章 */
	private int currentUserSignNo=1;
	
	/** 印章所在页码 */
	private int pageNo=1;
	
	/** 印章位置x */
	private Float offsetX;
	
	/** 印章位置y */
	private Float offsetY;
	
	/** 骑缝章位置 */
	private Double acrossPagePosition;


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getSealId() {
		return sealId;
	}

	public void setSealId(String sealId) {
		this.sealId = sealId;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public int getKeywordIndex() {
		return keywordIndex;
	}

	public void setKeywordIndex(int keywordIndex) {
		this.keywordIndex = keywordIndex;
	}

	public int getCurrentUserSignNo() {
		return currentUserSignNo;
	}

	public void setCurrentUserSignNo(int currentUserSignNo) {
		this.currentUserSignNo = currentUserSignNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(Float offsetX) {
		this.offsetX = offsetX;
	}

	public Float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(Float offsetY) {
		this.offsetY = offsetY;
	}

	public Double getAcrossPagePosition() {
		return acrossPagePosition;
	}

	public void setAcrossPagePosition(Double acrossPagePosition) {
		this.acrossPagePosition = acrossPagePosition;
	}

	public String getTemplateCode1() {
		return templateCode1;
	}

	public void setTemplateCode1(String templateCode1) {
		this.templateCode1 = templateCode1;
	}

	public String getTemplateCode2() {
		return templateCode2;
	}

	public void setTemplateCode2(String templateCode2) {
		this.templateCode2 = templateCode2;
	}
	
}
