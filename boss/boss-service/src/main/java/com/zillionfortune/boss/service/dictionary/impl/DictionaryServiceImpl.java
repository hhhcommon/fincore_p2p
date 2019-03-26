/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.service.dictionary.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.dao.DictionaryDao;
import com.zillionfortune.boss.dal.entity.Dictionary;
import com.zillionfortune.boss.service.dictionary.DictionaryService;


/**
 * ClassName: DictionaryServiceImpl <br/>
 * Function: 数据字典Service实现类. <br/>
 * Date: 2017年2月22日 上午11:04:57 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Component
public class DictionaryServiceImpl implements DictionaryService {
	
	@Autowired
	private DictionaryDao opDictionaryDao;

	/**
	 * @see com.zillionfortune.boss.service.dictionary.DictionaryService#queryList(com.zillionfortune.boss.dal.entity.Dictionary)
	 */
	@Override
	public List<Dictionary> queryList(Dictionary opDictionary) {
		return opDictionaryDao.selectBySelective(opDictionary);
	}
	
	
	@Override
	public Dictionary queryOpDictionary(Dictionary opDictionary) {
		List<Dictionary> list = queryList(opDictionary);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @see com.zillionfortune.boss.service.dictionary.DictionaryService#selectDictionaryByPage(com.zillionfortune.boss.dal.entity.Dictionary)
	 */
	@Override
	public List<Dictionary> selectDictionaryByPage(Dictionary record) {
		return opDictionaryDao.selectDictionaryByPage(record);
	}

	@Override
	public int selectDictionaryByPageCount(Dictionary record) {
		return opDictionaryDao.selectDictionaryByPageCount(record);
	}

	/**
	 * @see com.zillionfortune.boss.service.dictionary.DictionaryService#selectDictionaryKeysByPage(com.zillionfortune.boss.dal.entity.Dictionary)
	 */
	@Override
	public List<Dictionary> selectDictionaryKeysByPage(Dictionary record) {
		return opDictionaryDao.selectDictionaryKeysByPage(record);
	}

	@Override
	public int selectDictionaryKeysByPageCount(Dictionary record) {
		return opDictionaryDao.selectDictionaryKeysByPageCount(record);
	}
	
	@Override
	public void insert(Dictionary opDictionary) {
		opDictionaryDao.insertSelective(opDictionary);
	}
	
	@Override
	public void update(Dictionary opDictionary) {
		opDictionaryDao.updateBySelective(opDictionary);
	}
	
	@Override
	public Dictionary selectByPrimaryKey(Integer id) {
		return opDictionaryDao.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(Dictionary record) {
		return opDictionaryDao.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<Dictionary> selectDictionaryKeysByCodes(List<String> codes) {
		return opDictionaryDao.selectDictionaryKeysByCodes(codes);
	}
}