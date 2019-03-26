/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.dictionary.dto;

import java.util.Date;



/**
 * ClassName: DictionaryResponseDto <br/>
 * Function: 数据字典大类查询响应对象. <br/>
 * Date: 2017年2月22日 上午11:11:03 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public class DictionaryResponseDto {
	private Integer dictionaryId;

	private String name;

    private String code;

    private String remark;
    
    private Date createTime;
    
    private Date modifyTime;
    
	public int getDictionaryId() {
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
