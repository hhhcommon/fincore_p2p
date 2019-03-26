package com.zillionfortune.boss.dal.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.Dictionary;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface DictionaryDao {
	int deleteByPrimaryKey(Integer id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Integer id);
    
    List<Dictionary> selectBySelective(Dictionary record);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
    
    int updateBySelective(Dictionary record);
    
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
    List<Dictionary> selectDictionaryKeysByCodes(List<String> codes);
}