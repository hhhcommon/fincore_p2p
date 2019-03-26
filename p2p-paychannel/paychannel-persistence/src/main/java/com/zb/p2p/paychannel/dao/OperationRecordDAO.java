package com.zb.p2p.paychannel.dao;


import com.zb.p2p.paychannel.dao.domain.OperationRecordEntity;

public interface OperationRecordDAO {
    int deleteByPrimaryKey(Long id);

    int insert(OperationRecordEntity record);

    int insertSelective(OperationRecordEntity record);

    OperationRecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperationRecordEntity record);

    int updateByPrimaryKey(OperationRecordEntity record);
}