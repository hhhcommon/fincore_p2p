package com.zb.p2p.customer.dao;

import org.springframework.stereotype.Repository;

import com.zb.p2p.customer.dao.domain.UserBindCardLog;

@Repository("userBindCardLogMapper")
public interface UserBindCardLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBindCardLog record);

    int insertSelective(UserBindCardLog record);

    UserBindCardLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBindCardLog record);

    int updateByPrimaryKey(UserBindCardLog record);
    
    int updateByOutOrderNo(UserBindCardLog record);
    
    UserBindCardLog selectCardLogByOutOrderNo(UserBindCardLog record);
}