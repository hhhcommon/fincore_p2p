package com.zillionfortune.boss.service.redis.model;

import java.util.Date;
import java.util.List;

/**
 * ClassName: EnterpriseModel <br/>
 * Function: 保存在redis中的企业用户信息. <br/>
 * Date: 2016年11月22日 下午12:53:58 <br/>
 *
 * @author kaiyun
 * @version 
 * @since JDK 1.7
 */
public class RedisModel {
	
	
	private Integer id;
	
	private String name;
	
    private String email;

    private String mobile;
    
    private String realName;
    
    private Date createTime;

    private Integer createBy;
    
    private Date modifyTime;

    private Integer modifyBy;
    
    private Integer deleteFlag;
    
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public List<String> getSignLevels() {
		return signLevels;
	}

	public void setSignLevels(List<String> signLevels) {
		this.signLevels = signLevels;
	}
    
}
