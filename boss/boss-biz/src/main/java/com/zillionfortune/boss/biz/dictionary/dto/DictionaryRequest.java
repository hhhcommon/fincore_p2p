/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.dictionary.dto;

import com.zillionfortune.boss.common.dto.BasePageRequest;

/**
 * ClassName: DictionaryRequestDto <br/>
 * Function: 数据字典操作请求对象. <br/>
 * Date: 2017年2月22日 上午11:11:03 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public class DictionaryRequest extends BasePageRequest {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer dictionaryId;

	private String name;

    private String code;

    private String key;

    private String value;

    private String remark;
    
    private Integer userId;

    /**
     * status:'启用状态：1：已启用（不可修改）；0：未启用（可修改）；'
     */
    private Integer status;

    /**
     * seq:顺序
     */
    private Integer seq;
    
	public Integer getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
