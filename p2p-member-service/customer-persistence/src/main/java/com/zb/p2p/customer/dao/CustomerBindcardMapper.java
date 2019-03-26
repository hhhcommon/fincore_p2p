package com.zb.p2p.customer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
@Repository("customerBindcardMapper")
public interface CustomerBindcardMapper {
    int deleteByPrimaryKey(Long bindId);

    int insert(CustomerBindcard record);

    int insertSelective(CustomerBindcard record);

    CustomerBindcard selectByPrimaryKey(Long bindId);

    int updateByPrimaryKeySelective(CustomerBindcard record);

    int updateByPrimaryKey(CustomerBindcard record);
    
    CustomerBindcard selectCustUseCard(Long customerId);
    
    CustomerBindcard selectByPreBindCard(CustomerBindcard record);
    
    CustomerBindcard selectCardByCardNo(String  bankCardNo);
    
    int insertList(List<CustomerBindcard > customerBindcardList);
}