package com.zb.p2p.trade.persistence.dao;


import com.zb.p2p.trade.common.model.Page;
import com.zb.p2p.trade.persistence.entity.ContractEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contractMapper")
public interface ContractMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ContractEntity record);

    int insertSelective(ContractEntity record);

    ContractEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractEntity record);

    int updateByPrimaryKey(ContractEntity record);
    
    ContractEntity selectByCreditorNo(@Param("creditorNo") String creditorNo);

    ContractEntity selectByExtOrderNo(@Param("extOrderNo") String extOrderNo);

    int updateContractDocumentIdByContractNo(ContractEntity record);

    List<ContractEntity> queryContractByLoanNo(@Param("loanNo") String loanNo);

    List<ContractEntity> selectByStatus(String status);

    int selectCount(ContractEntity record);

    List<ContractEntity> queryContractByLoanNoWithPagination(ContractEntity record, Page page);

}