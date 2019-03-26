/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.power.check;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.op.power.vo.PowerAddRequestVo;
import com.zillionfortune.boss.web.controller.op.power.vo.PowerModifyRequestVo;
import com.zillionfortune.boss.web.controller.op.power.vo.PowerQueryByPageRequestVo;

/**
 * ClassName: PowerParameterChecker <br/>
 * Function: 权限请求参数校验. <br/>
 * Date: 2017年2月20日 下午3:31:39 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class PowerParameterChecker {

	/**
	 * 检查新增权限请求参数
	 * @param
	 * @return
	 */
	public void checkPowerAddRequest(PowerAddRequestVo req) throws Exception {
    
        if (req == null) {
            throw new BusinessException("请求对象不能为空");
        }
        
        if (StringUtils.isBlank(req.getName())) {
        	throw new BusinessException("权限名称不能为空");
        }
    
    }
	
	/**
	 * 检查修改权限请求参数
	 * @param
	 * @return
	 */
	public void checkPowerModifyRequest(PowerModifyRequestVo req) throws Exception {
    
        if (req == null) {
            throw new BusinessException("请求对象不能为空");
        }
        
        if (req.getPowerId() == null) {
        	throw new BusinessException("权限Id不能为空");
        }
        
        if (StringUtils.isEmpty(req.getName()) && StringUtils.isEmpty(req.getOperationCode())
        		&& StringUtils.isEmpty(req.getRequestUrl()) && req.getMenuId() == null
        		&& StringUtils.isEmpty(req.getRemark())) {
        	
        	throw new BusinessException("没有要修改的栏位");
        	
        }
        
    }
	
	public void checkPowerQueryByPageRequest(PowerQueryByPageRequestVo req) throws Exception {
	        
	}
	
}
