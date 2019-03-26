/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.menu;

import com.zillionfortune.boss.biz.menu.dto.MenuAddRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuDeleteRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuModifyRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuQueryByPageRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: MenuBiz <br/>
 * Function: 运营后台菜单管理biz层接口. <br/>
 * Date: 2017年2月22日 上午10:07:42 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface MenuBiz {
	
	/**
	 * add:新增菜单方法. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse add(MenuAddRequest req);
	
	/**
	 * modify:修改菜单方法. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse modify(MenuModifyRequest req);
	
	/**
	 * delete:删除菜单方法. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse delete(MenuDeleteRequest req);
	
	/**
	 * query:查询菜单. <br/>
	 *
	 * @param menuId
	 * @return
	 */
	public BaseWebResponse query(Integer menuId);
	
	/**
	 * queryByPage:查询菜单(分页). <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryByPage(MenuQueryByPageRequest req);
	
	/**
	 * queryMenuPower:查询菜单及权限. <br/>
	 *
	 * @return
	 */
	public BaseWebResponse queryMenuPower();
	
}
