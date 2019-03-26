package com.zb.p2p.customer.dao.domain;

import java.util.Date;

public class NotifyInfo {
	
	private Long id;
	private String notifyKey;
	private int notifyCategory; 

    private Date createTime;

    private Date updateTime;
    private Long customerId;
    private int notifyResult;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNotifyKey() {
		return notifyKey;
	}
	public void setNotifyKey(String notifyKey) {
		this.notifyKey = notifyKey;
	}
	public int getNotifyCategory() {
		return notifyCategory;
	}
	public void setNotifyCategory(int notifyCategory) {
		this.notifyCategory = notifyCategory;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public int getNotifyResult() {
		return notifyResult;
	}
	public void setNotifyResult(int notifyResult) {
		this.notifyResult = notifyResult;
	}


}
