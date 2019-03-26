/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.menu.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.dal.dao.MenuDao;
import com.zillionfortune.boss.dal.entity.Menu;
import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.service.menu.MenuService;

/**
 * ClassName: MenuServiceImpl <br/>
 * Function: TODO (描述这个类的作用). <br/>
 * Date: 2017年2月22日 下午3:32:51 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	/**
	 * 新增菜单.
	 * @see com.zillionfortune.boss.service.menu.MenuService#add(com.zillionfortune.boss.dal.entity.Menu)
	 */
	@Override
	public void add(Menu menu) {
		menuDao.insertSelective(menu);
	}

	/**
	 * 逻辑删除菜单.
	 * @see com.zillionfortune.boss.service.menu.MenuService#delete(java.lang.Integer)
	 */
	@Override
	public void delete(Menu menu) {
		menuDao.updateByPrimaryKeySelective(menu);
	}

	/**
	 * 更新菜单.
	 * @see com.zillionfortune.boss.service.menu.MenuService#update(com.zillionfortune.boss.dal.entity.Menu)
	 */
	@Override
	public void update(Menu menu) {
		menuDao.updateByPrimaryKeySelective(menu);
	}

	/**
	 * 根据Id查询菜单.
	 * @see com.zillionfortune.boss.service.menu.MenuService#selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public Menu selectByPrimaryKey(Integer id) {
		return menuDao.selectByPrimaryKey(id);
	}

	/**
	 * 根据条件选择查询 .
	 * @see com.zillionfortune.boss.service.menu.MenuService#selectBySelective(com.zillionfortune.boss.dal.entity.Menu)
	 */
	@Override
	public List<Menu> selectBySelective(Menu menu) {
		return menuDao.selectBySelective(menu);
	}

	/**
	 * 分页查询菜单.
	 * @see com.zillionfortune.boss.service.menu.MenuService#queryByPage(java.util.Map)
	 */
	@Override
	public List<Menu> queryByPage(Map paramMap) {
		return menuDao.queryByPage(paramMap);
	}

}