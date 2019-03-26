package com.zb.txs.p2p.order.persistence.mapper;

import com.zb.txs.p2p.order.persistence.model.OperationRecord;
import com.zb.txs.p2p.order.persistence.model.OperationRecordExample;

import java.util.List;

public interface OperationRecordMapper {
    long countByExample(OperationRecordExample example);

    int insert(OperationRecord record);

    int insertSelective(OperationRecord record);

    List<OperationRecord> selectByExample(OperationRecordExample example);

    OperationRecord selectByPrimaryKey(Long id);
}