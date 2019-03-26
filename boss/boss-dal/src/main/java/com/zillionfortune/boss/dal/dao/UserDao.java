package com.zillionfortune.boss.dal.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.User;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface UserDao {

	/**
	 * 根据主键删除用户. <br/>
	 *
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增用户. <br/>
     *
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 新增用户（选择性的）. <br/>
     *
     * @param record
     * @return
     */
    int insertSelective(User record);
    
    /**
     * 更新用户信息（选择性的）. <br/>
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新用户信息. <br/>
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);
    
    /**
     * 重置登录密码. <br/>
     *
     * @param record
     * @return
     */
    int resetPassword(User record);
    
    /**
     * 根据主键查询单个用户. <br/>
     *
     * @param id
     * @return
     */
    User selectByPrimaryKey(Integer id);
    
    /**
     * 根据用户名查询单个用户. <br/>
     *
     * @param userName
     * @return
     */
    User selectByUserName(String userName);
    
    /**
     * 查询单个用户
     * 
     * @param user
     * @return
     */
    User selectOpUser(User user);
    
    /**
     * 用户列表（不分页）. <br/>
     *
     * @param user
     * 		OpUser
     * @return
     * 		List<OpUser>
     */
    List<User> queryList(User user);
    
    /**
     * 用户列表（分页）. <br/>
     *
     * @param user
     * 		OpUser
     * @return
     * 		List<OpUser>
     */
    List<User> queryListByPage (User user);
    int queryListByPageCount (User user);
    
}