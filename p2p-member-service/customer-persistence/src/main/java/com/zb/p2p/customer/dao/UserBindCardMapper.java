package com.zb.p2p.customer.dao;

import org.springframework.stereotype.Repository;

import com.zb.p2p.customer.dao.domain.UserBindCard;

@Repository("userBindCardMapper")
public interface UserBindCardMapper {
    int deleteByPrimaryKey(Long cardBindId);

    int insert(UserBindCard record);

    int insertSelective(UserBindCard record);

    UserBindCard selectByPrimaryKey(Long cardBindId);

    int updateByPrimaryKeySelective(UserBindCard record);

    int updateByPrimaryKey(UserBindCard record);
    
    UserBindCard selectCustUseCard(Long customerId);
    
    UserBindCard selectCardByCardNo(String cardNo);
}