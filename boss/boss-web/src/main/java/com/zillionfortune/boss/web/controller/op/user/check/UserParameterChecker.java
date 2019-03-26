/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.user.check;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.op.user.vo.UserAddRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserDeleteRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserModifyPasswordRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserModifyRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserQueryListByPageRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserQueryListRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserResetPasswordRequestVo;

/**
 * ClassName: UserParameterChecker <br/>
 * Function: 用户管理_请求参数校验. <br/>
 * Date: 2017年2月21日 下午4:46:13 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class UserParameterChecker {

	/**
	 * checkUserAddRequest:用户管理_新增_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserAddRequestVo
	 * @throws Exception
	 */
	public void checkUserAddRequest(UserAddRequestVo req) throws Exception {
    
        if (req == null) {
            throw new BusinessException("请求对象UserAddRequestVo不能为空");
        }
        
        if (StringUtils.isBlank(req.getEmail())) {
        	throw new BusinessException("邮箱不能为空");
        }
        if (StringUtils.isBlank(req.getPassword())) {
        	throw new BusinessException("登录密码不能为空");
        }
        if (StringUtils.isBlank(req.getRepeatPwd())) {
        	throw new BusinessException("确认登录密码不能为空");
        }
        if (!req.getPassword().equalsIgnoreCase(req.getRepeatPwd())) {
        	throw new BusinessException("两次输入密码不正确");
        }
        if (StringUtils.isBlank(req.getUserName())) {
        	throw new BusinessException("用户名不能为空");
        }
        
    }
	
	/**
	 * checkUserModifyRequest:用户管理_修改_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserModifyRequestVo
	 * @throws Exception
	 */
	public void checkUserModifyRequest(UserModifyRequestVo req) throws Exception {
	    
        if (req == null) {
            throw new BusinessException("请求对象UserModifyRequestVo不能为空");
        }
        
        if (StringUtils.isBlank(req.getUserId())) {
        	throw new BusinessException("用户ID不能为空");
        }
        
    }
	
	/**
	 * checkUserResetPasswordRequest:重置登录密码_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserResetPasswordRequestVo
	 * @throws Exception
	 */
	public void checkUserResetPasswordRequest(UserResetPasswordRequestVo req) throws Exception {
	    
        if (req == null) {
            throw new BusinessException("请求对象UserResetPasswordRequestVo不能为空");
        }
        
        if (StringUtils.isBlank(req.getUserId())) {
        	throw new BusinessException("用户ID不能为空");
        }
        
    }
	
	/**
	 * checkUserModifyPasswordRequest:修改登录密码_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserResetPasswordRequestVo
	 * @throws Exception
	 */
	public void checkUserModifyPasswordRequest(UserModifyPasswordRequestVo req) throws Exception {
	    
        if (req == null) {
            throw new BusinessException("请求对象UserResetPasswordRequestVo不能为空");
        }
        
        if (StringUtils.isBlank(req.getUserId())) {
        	throw new BusinessException("用户ID不能为空");
        }
        
        if (StringUtils.isBlank(req.getOriginalPwd())) {
        	throw new BusinessException("原始登录密码不能为空");
        }
        
        if (StringUtils.isBlank(req.getNewPwd())) {
        	throw new BusinessException("新登录密码不能为空");
        }
        
        /*if (StringUtils.isBlank(req.getRepeatPwd())) {
        	throw new BusinessException("确认登录密码不能为空");
        }
        
        if (!req.getNewPwd().equalsIgnoreCase(req.getRepeatPwd())) {
        	throw new BusinessException("两次密码不一致");
        }*/
        
    }
	
	/**
	 * checkUserDeleteRequest:用户管理_删除用户_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserDeleteRequestVo
	 * @throws Exception
	 */
	public void checkUserDeleteRequest(UserDeleteRequestVo req) throws Exception {
	    
        if (req == null) {
            throw new BusinessException("请求对象UserDeleteRequestVo不能为空");
        }
        
        if (StringUtils.isBlank(req.getUserId())) {
        	throw new BusinessException("用户ID不能为空");
        }
        
    }
	
	/**
	 * checkUserQueryListRequest:用户列表（无分页）_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserDeleteRequestVo
	 * @throws Exception
	 */
	public void checkUserQueryListRequest(UserQueryListRequestVo req) throws Exception {
	    
        if (req == null) {
            throw new BusinessException("请求对象UserDeleteRequestVo不能为空");
        }
        
    }
	
	/**
	 * checkUserQueryListByPageRequest:用户列表（有分页）_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserDeleteRequestVo
	 * @throws Exception
	 */
	public void checkUserQueryListByPageRequest(UserQueryListByPageRequestVo req) throws Exception {
	    
        if (req == null) {
            throw new BusinessException("请求对象UserQueryListByPageRequestVo不能为空");
        }
        
    }
	
}
