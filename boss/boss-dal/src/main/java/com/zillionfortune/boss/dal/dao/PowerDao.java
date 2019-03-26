package com.zillionfortune.boss.dal.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface PowerDao {
	
    int insert(Power record);

    int insertSelective(Power record);

    int deleteByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(Power record);

    int updateByPrimaryKey(Power record);
    
    Power selectByPrimaryKey(Integer id);
    
    List<Power> selectBySelective(Power record);
    
    int selectBySelectiveCount(Power record);
    
}