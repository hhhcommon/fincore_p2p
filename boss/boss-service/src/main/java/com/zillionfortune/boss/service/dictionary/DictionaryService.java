/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.service.dictionary;

import java.util.List;

import com.zillionfortune.boss.dal.entity.Dictionary;

/**
 * ClassName: DictionaryService <br/>
 * Function: 数据字典Service. <br/>
 * Date: 2017年2月22日 上午11:02:03 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public interface DictionaryService {
	/**
	 * queryList:查询数据字典列表. <br/>
	 *
	 * @param opDictionary
	 * @return
	 */
	public List<Dictionary> queryList(Dictionary opDictionary);
	
	public Dictionary queryOpDictionary(Dictionary opDictionary);
	
	 /**
     * selectDictionaryByPage:数据字典大类分页查询. <br/>
     *
     * @param record
     * @return
     */
    List<Dictionary> selectDictionaryByPage(Dictionary record);
    int selectDictionaryByPageCount(Dictionary record);
    
    /**
     * selectDictionaryKeysByPage:数据字典值分页查询. <br/>
     *
     * @param record
     * @return
     */
    List<Dictionary> selectDictionaryKeysByPage(Dictionary record);
    int selectDictionaryKeysByPageCount(Dictionary record);
    
    /**
     * insert:新增数据字典. <br/>
     *
     * @param opDictionary
     */
    void insert(Dictionary opDictionary);
    /**
     * update:更新数据字典. <br/>
     *
     * @param opDictionary
     */
    void update(Dictionary opDictionary);
    
    Dictionary selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(Dictionary record);
    
    List<Dictionary> selectDictionaryKeysByCodes(List<String> codes);
    
}