/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.menu.check;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuAddRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuDeleteRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuModifyRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuQueryByPageRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuQueryRequestVo;

/**
 * ClassName: MenuParameterChecker <br/>
 * Function: 运营平台菜单请求参数校验. <br/>
 * Date: 2017年2月22日 下午2:25:00 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class MenuParameterChecker {

	/**
	 * checkMenuAddRequest:校验新增菜单请求参数. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkMenuAddRequest(MenuAddRequestVo req) throws Exception {
    
        if (req == null) {
            throw new BusinessException("请求对象不能为空");
        }
        
        if (StringUtils.isBlank(req.getName())) {
        	throw new BusinessException("菜单名称不能为空");
        }
    
    }
	
	/**
	 * checkMenuModifyRequest:校验修改菜单请求参数. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkMenuModifyRequest(MenuModifyRequestVo req) throws Exception {
    
        if (req == null) {
            throw new BusinessException("请求对象不能为空");
        }
        
        if (req.getMenuId() == null) {
        	throw new BusinessException("菜单Id不能为空");
        }
        
        if (StringUtils.isBlank(req.getName())
        	&& req.getIsValid() == null
        	&& req.getDisplayOrder() == null
        	&& StringUtils.isBlank(req.getUrl())
        	&& req.getParentId() == null
        	&& StringUtils.isBlank(req.getIcon())
        	&& StringUtils.isBlank(req.getRemark())) {
        	throw new BusinessException("没有内容要修改");
        }
        
    }
	
	/**
	 * checkMenuDeleteRequest:校验删除菜单请求参数. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkMenuDeleteRequest(MenuDeleteRequestVo req) throws Exception {
    
        if (req == null) {
            throw new BusinessException("请求对象不能为空");
        }
        
        if (req.getMenuId() == null) {
        	throw new BusinessException("菜单Id不能为空");
        }
        
    }
	
	/**
	 * checkMenuQueryRequest:校验查询菜单请求参数. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkMenuQueryRequest(MenuQueryRequestVo req) throws Exception {
		
		if (req == null) {
            throw new BusinessException("请求对象不能为空");
        }
		
		if (req.getMenuId() == null) {
        	throw new BusinessException("菜单Id不能为空");
        }
	        
	}
	
}
