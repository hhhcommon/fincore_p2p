/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.dictionary;

import java.util.List;

import com.zillionfortune.boss.biz.dictionary.dto.DictionaryRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: DictionaryBiz <br/>
 * Function: 数据字典Biz接口. <br/>
 * Date: 2017年2月22日 上午11:09:07 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public interface DictionaryBiz {

	/**
	 * query:数据字典查询. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse query(DictionaryRequest req);
	
	/**
	 * queryByPage:查询数据字典大类列表(分页). <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryByPage(DictionaryRequest req);
	
	
	/**
	 * queryKeysByPage:查询数据字典值列表（分页）. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryKeysByPage(DictionaryRequest req);
	
	public BaseWebResponse add(DictionaryRequest req);
	
	public BaseWebResponse modify(DictionaryRequest req);
	
	public BaseWebResponse delete(DictionaryRequest req);
	
	/**
	 * queryKeysByCodes:根据数据字典Code数组查询对应的字典列表. <br/>
	 *
	 * @param codes
	 * @return
	 */
	public BaseWebResponse queryKeysByCodes(List<String> codes);
	
}
