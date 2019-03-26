/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.dictionary.vo;

import java.util.List;

/**
 * ClassName: DictionaryQueryKeysByCodesVO <br/>
 * Function: 根据字典code列表查询对应的字典列表. <br/>
 * Date: 2017年7月27日 上午10:53:39 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class DictionaryQueryKeysByCodesVO {

	/** 字典Code列表  */
	private List<String> codes;

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
}
