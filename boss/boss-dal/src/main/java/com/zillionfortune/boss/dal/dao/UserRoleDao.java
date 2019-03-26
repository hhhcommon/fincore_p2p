package com.zillionfortune.boss.dal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.UserRole;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface UserRoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    List<UserRole> selectBySelective(UserRole record);
    
    /**
     * 根据用户Id查用户角色. <br/>
     *
     * @param paraMap
     * @return
     */
    List<Role> selectRoleByUserId(Map<String,Object> paraMap);
    
    int deleteBySelective(UserRole record);
}