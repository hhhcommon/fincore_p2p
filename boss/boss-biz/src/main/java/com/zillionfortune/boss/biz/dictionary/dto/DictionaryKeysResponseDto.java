/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.dictionary.dto;



/**
 * ClassName: DictionaryKeysResponseDto <br/>
 * Function: 数据字典值查询响应对象. <br/>
 * Date: 2017年2月22日 上午11:11:03 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public class DictionaryKeysResponseDto extends DictionaryResponseDto {


    private String key;

    private String value;

    /**
     * status:'启用状态：1：已启用（不可修改）；0：未启用（可修改）；'
     */
    private Integer status;

    /**
     * seq:顺序
     */
    private Integer seq;

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
}
