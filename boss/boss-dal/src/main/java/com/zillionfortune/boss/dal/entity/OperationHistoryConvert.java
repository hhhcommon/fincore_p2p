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
public class OperationHistoryConvert implements Serializable {
	
	private static final long serialVersionUID = -6532366534692890978L;
	
	/** 操作日志ID  */
	private String operationHistoryId;

	/** 用户ID  */
    private String userId;

    /** 操作类型  */
    private String operationType;

    /** 操作内容  */
    private String content;

    /** 创建时间  */
    private String createTime;

	public String getOperationHistoryId() {
		return operationHistoryId;
	}

	public void setOperationHistoryId(String operationHistoryId) {
		this.operationHistoryId = operationHistoryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


}