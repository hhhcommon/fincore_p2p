/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.user.check;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.op.user.vo.UserRoleAddRequestVo;

/**
 * ClassName: UserRoleParameterChecker <br/>
 * Function: 用户角色请求参数校验. <br/>
 * Date: 2017年2月27日 上午10:03:02 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class UserRoleParameterChecker {

	/**
	 * checkUserAddRequest:用户管理_新增_请求参数校验. <br/>
	 *
	 * @param req
	 * 		UserAddRequestVo
	 * @throws Exception
	 */
	public void checkUserRoleAddRequest(UserRoleAddRequestVo req) throws Exception {
    
        if (req == null) {
            throw new BusinessException("请求对象不能为空");
        }
        
		if (req.getUserId() == null) {
			throw new BusinessException("用户Id不能为空");
		}

		if (CollectionUtils.isEmpty(req.getRoleIds())) {
			throw new BusinessException("角色Id列表不能为空");
		}  
    }
	
}
