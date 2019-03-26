/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.user;

import java.util.List;

import com.zillionfortune.boss.dal.entity.User;

/**
 * ClassName: UserService <br/>
 * Function: 运营后台_用户管理_service层接口定义. <br/>
 * Date: 2017年2月21日 下午4:37:40 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface UserService {
	
	/**
	 * 用户管理_删除. <br/>
	 *
	 * @param id
	 * 		userId
	 * @return
	 */
	public boolean deleteByPrimaryKey(Integer id); 
	
	/**
	 * 用户管理_新增. <br/>
	 *
	 * @param user
	 * 		OpUser
	 * @return
	 * 		boolean
	 */
	public boolean insert(User user);
	
	/**
	 * 用户管理_修改. <br/>
	 *
	 * @param user
	 * 		OpUser
	 * @return
	 * 		int
	 */
	public boolean update(User user);
	
	/**
	 * 用户管理_新增or修改. <br/>
	 *
	 * @param user
	 * 		OpUser
	 * @return
	 * 		boolean
	 */
	public boolean save(User user);
	
	/**
	 * 重置登录密码. <br/>
	 *
	 * @param user
	 * 		OpUser
	 * @return
	 * 		int
	 */
	public boolean resetPassword(User user);
	
	/**
	 * 查询单个用户. <br/>
	 *
	 * @param user
	 * 		OpUser
	 * @return
	 */
	public User selectOpUser(User user);
	
	/**
	 * 根据用户ID，查询单个用户. <br/>
	 *
	 * @param id
	 * 		userId
	 * @return
	 */
	public User selectByPrimaryKey(Integer id);
	
	/**
     * 根据用户名查询单个用户. <br/>
     *
     * @param userName
     * @return
     */
    User selectByUserName(String userName);
	
	/**
     * 用户列表（不分页）. <br/>
     *
     * @param user
     * 		OpUser
     * @return
     * 		List<OpUser>
     */
    List<User> queryList (User user);
    
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
