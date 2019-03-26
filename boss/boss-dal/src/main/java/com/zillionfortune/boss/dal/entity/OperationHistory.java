package com.zillionfortune.boss.dal.entity;

public class OperationHistory  extends BaseEntity {

    private Integer userId;

    private String operationType;

    private String content;

    private String referId;
    
    public OperationHistory() {
		super();
	}

	public OperationHistory(Integer userId, String operationType,
			String content, String referId) {
		super();
		this.userId = userId;
		this.operationType = operationType;
		this.content = content;
		this.referId = referId;
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
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId == null ? null : referId.trim();
    }
}