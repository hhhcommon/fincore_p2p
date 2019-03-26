package com.zb.p2p.dao.master;

import org.apache.ibatis.annotations.Param;

import com.zb.p2p.entity.ContractEntity;

public interface ContractDAO {
    int deleteByPrimaryKey(Long id);

    int insert(ContractEntity record);

    int insertSelective(ContractEntity record);

    ContractEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractEntity record);

    int updateByPrimaryKey(ContractEntity record);
    
    ContractEntity selectByCreditorNo(@Param("creditorNo") String creditorNo);
    
    
    
}