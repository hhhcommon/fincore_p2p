/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user;

import com.zillionfortune.boss.biz.user.dto.UserAddRequest;
import com.zillionfortune.boss.biz.user.dto.UserDeleteRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyPasswordRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListByPageRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListRequest;
import com.zillionfortune.boss.biz.user.dto.UserResetPasswordRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: UserBiz <br/>
 * Function: 运营后台_用户管理_biz层接口定义. <br/>
 * Date: 2017年2月21日 下午4:39:22 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface UserBiz {
	
	/**
	 * 用户管理_新增. <br/>
	 *
	 * @param req
	 * 		UserAddRequest
	 * @return
	 * 		BaseWebResponse
	 */
	public BaseWebResponse insert(UserAddRequest req);
	
	/**
	 * 用户管理_修改. <br/>
	 *
	 * @param req
	 * 		UserModifyRequest
	 * @return
	 * 		BaseWebResponse
	 */
	public BaseWebResponse update(UserModifyRequest req);
	
	/**
	 * 重置登录密码. <br/>
	 *
	 * @param req
	 * 		UserResetPasswordRequest
	 * @return
	 * 		BaseWebResponse
	 */
	public BaseWebResponse resetPassword(UserResetPasswordRequest req);
	
	/**
	 * 修改登录密码. <br/>
	 *
	 * @param UserModifyPasswordRequest
	 * @return
	 * 		BaseWebResponse
	 */
	public BaseWebResponse modifyPassword(UserModifyPasswordRequest req);
	
	/**
	 * 用户管理_删除_逻辑. <br/>
	 *
	 * @param req
	 * 		UserDeleteRequest
	 * @return
	 * 		BaseWebResponse
	 */
	public BaseWebResponse deleteByPrimaryKey(UserDeleteRequest req);
	
	/**
	 * 用户管理_删除_物理. <br/>
	 *
	 * @param id
	 * 		userIs
	 * @return
	 * 		BaseWebResponse
	 */
	public BaseWebResponse deleteByPrimaryKey(Integer id);
	
	/**
     * 用户列表（不分页）. <br/>
     *
     * @param user
     * 		UserQueryListRequest
     * @return
     * 		List<OpUser>
     */
	public BaseWebResponse queryList (UserQueryListRequest user);
	
	/**
     * 用户列表（分页）. <br/>
     *
     * @param user
     * 		OpUser
     * @return
     * 		List<OpUser>
     */
	public BaseWebResponse queryListByPage (UserQueryListByPageRequest req);
	
}
