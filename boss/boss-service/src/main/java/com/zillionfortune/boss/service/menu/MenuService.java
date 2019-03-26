/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.menu;

import java.util.List;
import java.util.Map;

import com.zillionfortune.boss.dal.entity.Menu;

/**
 * ClassName: MenuService <br/>
 * Function: 运营后台菜单管理service层接口. <br/>
 * Date: 2017年2月22日 下午2:51:44 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface MenuService {

	/**
	 * add:新增菜单. <br/>
	 *
	 * @param menu
	 * @return
	 */
	public void add(Menu menu);
	
	/**
	 * delete:删除菜单. <br/>
	 *
	 * @param id
	 * @return
	 */
	public void delete(Menu menu);
	
	/**
	 * update:更新菜单. <br/>
	 *
	 * @param menu
	 * @return
	 */
	public void update(Menu menu);
	
	
	/**
	 * selectByPrimaryKey:根据主键查询菜单. <br/>
	 *
	 * @param id
	 * @return
	 */
	public Menu selectByPrimaryKey(Integer id);
	
	/**
	 * selectBySelective:根据条件查询 <br/>
	 *
	 * @param menu
	 * @return
	 */
	public List<Menu> selectBySelective(Menu menu);
	
	/**
     * queryByPage:分页查询菜单. <br/>
     *
     * @param recode
     * @return
     */
    List<Menu> queryByPage(Map paramMap);
    
}
