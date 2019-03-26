/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zillionfortune.boss.dal.dao.UserDao;
import com.zillionfortune.boss.dal.entity.User;
import com.zillionfortune.boss.service.user.UserService;

/**
 * ClassName: UserServiceImpl <br/>
 * Function: 运营后台_用户管理_service层实现. <br/>
 * Date: 2017年2月22日 上午9:43:28 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao opUserDao;


	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		int res = 0;
		res = opUserDao.deleteByPrimaryKey(id);
		return res > 0; 
	}
	
	@Override
	public boolean insert(User user) {
		int res = 0;
		if(user!=null){
			res = opUserDao.insertSelective(user);
		}
		return res > 0;
	}


	@Override
	public boolean update(User user) {
		int res = 0;
		if(user!=null){
			res = opUserDao.updateByPrimaryKeySelective(user);
		}
		return res > 0; 
	}
	
	@Override
	public boolean save(User user) {
		int res = 0;
		if(user.getId()==0 || user.getId()==null){
			res = opUserDao.insertSelective(user);
		}else{
			res = opUserDao.updateByPrimaryKeySelective(user);
		}
		return res > 0;
	}

	@Override
	public boolean resetPassword(User user) {
		int res = 0;
		if(user!=null){
			res = opUserDao.resetPassword(user);
		}
		return res > 0; 
	}


	@Override
	public User selectOpUser(User user) {
		return opUserDao.selectOpUser(user);
	}


	@Override
	public User selectByPrimaryKey(Integer id) {
		return opUserDao.selectByPrimaryKey(id);
	}
	
	@Override
	public User selectByUserName(String userName) {
		return opUserDao.selectByUserName(userName);
	}
	
	@Override
	public List<User> queryList(User user) {
		return opUserDao.queryList(user);
	}

	@Override
	public List<User> queryListByPage(User user) {
		return opUserDao.queryListByPage(user);
	}

	@Override
	public int queryListByPageCount(User user) {
		return opUserDao.queryListByPageCount(user);
	}


}
