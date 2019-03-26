package com.zb.p2p.trade.persistence.dao;

import com.zb.p2p.trade.persistence.entity.TransferRequestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("transferRequestMapper")
public interface TransferRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransferRequestEntity record);

    int insertSelective(TransferRequestEntity record);

    TransferRequestEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransferRequestEntity record);

    int updateByPrimaryKey(TransferRequestEntity record);
}