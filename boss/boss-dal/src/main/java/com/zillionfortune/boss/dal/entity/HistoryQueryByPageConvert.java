package com.zillionfortune.boss.dal.entity;

import java.util.Date;

import com.zillionfortune.boss.common.dto.BaseWebPageResponse;

/**
 * ClassName: HistoryQueryByPageConvert <br/>
 * Function: 查询日志（分页）转换对象. <br/>
 * Date: 2017年2月27日 下午4:51:39 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class HistoryQueryByPageConvert extends BaseWebPageResponse {

	 /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id:日志Id.
	 */
	private Integer id;
	 
    /**
     * userId:用户Id.
     */
    private Integer userId;

    /**
     * operationType:操作类型.
     */
    private String operationType;

    /**
     * content:操作描述.
     */
    private String content;

    /**
     * referId:业务Id.
     */
    private String referId;
    
    /**
     * createTime:创建时间.
     */
    private Date createTime;
    
    /**
     * name:用户名.
     */
    private String name;

    /**
     * password:登录密码.
     */
    private String password;

    /**
     * email:公司邮箱.
     */
    private String email;

    /**
     * mobile:手机号
     */
    private String mobile;
    
    /**
     * realName:真实姓名.
     */
    private String realName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}