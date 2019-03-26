package com.zillionfortune.boss.facade.operation.dto;

import java.util.Date;
import java.util.List;

public class OperationLoginUserResponse {

	private Integer id;
	
	private String name;

	private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    private String email;
    
    private String mobile;
    
    private String realName;
    
    private List<String> signLevels;
	    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public OperationLoginUserResponse(){
		super();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<String> getSignLevels() {
		return signLevels;
	}

	public void setSignLevels(List<String> signLevels) {
		this.signLevels = signLevels;
	}
	
}
