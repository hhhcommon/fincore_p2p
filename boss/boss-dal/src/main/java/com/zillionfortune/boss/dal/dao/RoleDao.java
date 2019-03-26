package com.zillionfortune.boss.dal.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface RoleDao {
	int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);
    
    List<Role> selectBySelective(Role record);
    int selectBySelectiveCount(Role record);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    List<Role> selectByUserId(Integer userId);
}