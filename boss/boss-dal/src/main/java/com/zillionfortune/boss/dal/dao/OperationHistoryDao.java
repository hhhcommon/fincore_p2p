package com.zillionfortune.boss.dal.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.HistoryQueryByPageConvert;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface OperationHistoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OperationHistory record);

    int insertSelective(OperationHistory record);

    OperationHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationHistory record);

    int updateByPrimaryKey(OperationHistory record);
    
    int selectBySelectiveCount(OperationHistory history);
    
    List<HistoryQueryByPageConvert> queryByPage(OperationHistory history);
    
    List<OperationHistory> selectBySelective(OperationHistory history);
}
