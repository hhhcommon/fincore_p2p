package com.zb.p2p.customer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.p2p.customer.dao.domain.OrgBankCard;
import com.zb.p2p.customer.dao.domain.OrgBankCardKey;
@Repository("orgBankCardMapper")
public interface OrgBankCardMapper {
    int deleteByPrimaryKey(OrgBankCardKey key);

    int insert(OrgBankCard record);

    int insertSelective(OrgBankCard record);

    OrgBankCard selectByPrimaryKey(OrgBankCardKey key);

    int updateByPrimaryKeySelective(OrgBankCard record);

    int updateByPrimaryKey(OrgBankCard record);
    
    List<OrgBankCard> selectByOrgId(Long orgId);
}