package com.zb.p2p.trade.persistence.dao;


import com.zb.p2p.trade.persistence.entity.OperationRecordEntity;
import org.springframework.stereotype.Repository;

@Repository("operationRecordMapper")
public interface OperationRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OperationRecordEntity record);

    int insertSelective(OperationRecordEntity record);

    OperationRecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperationRecordEntity record);

    int updateByPrimaryKey(OperationRecordEntity record);
}