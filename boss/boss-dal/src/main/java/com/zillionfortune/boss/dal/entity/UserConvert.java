package com.zillionfortune.boss.dal.entity;

import java.io.Serializable;

/**
 * ClassName: OpUserConvert <br/>
 * Function: OpUser转换对象. <br/>
 * Date: 2017年2月22日 下午4:05:38 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class UserConvert implements Serializable {
	
	private static final long serialVersionUID = -6532366534692890978L;
	
	/** 用户ID  */
	private String userId;

	/** 用户名  */
    private String userName;

    /** 删除状态  */
    private String deleteFlag;

    /** 真实姓名  */
    private String realName;

    /** 邮箱  */
    private String email;
    
    /** 手机号  */
    private String mobile;
    
    /** 创建时间  */
    private String createTime;
    
    /** 修改时间  */
    private String modifyTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

}