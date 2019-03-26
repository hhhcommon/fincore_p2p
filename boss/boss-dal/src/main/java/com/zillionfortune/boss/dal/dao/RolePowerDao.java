package com.zillionfortune.boss.dal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.dal.entity.RolePower;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface RolePowerDao {
	int deleteByPrimaryKey(Integer id);
	
	int deleteBySelective(RolePower record);

    int insert(RolePower record);

    int insertSelective(RolePower record);

    RolePower selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePower record);

    int updateByPrimaryKey(RolePower record);
    
    List<RolePower> selectBySelective(RolePower record);
    
    List<Power> selectRolePowerByRoleId(Map<String,Object> paraMap);
    
    List<Power> selectRolePowerByUserMenuId(Map<String,Object> paraMap);
}