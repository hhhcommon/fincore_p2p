/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.menu;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.op.menu.check.MenuParameterChecker;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuAddRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuDeleteRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuModifyRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuQueryByPageRequestVo;
import com.zillionfortune.boss.web.controller.op.menu.vo.MenuQueryRequestVo;
import com.zillionfortune.boss.biz.menu.MenuBiz;
import com.zillionfortune.boss.biz.menu.dto.MenuAddRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuDeleteRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuModifyRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuQueryByPageRequest;

/**
 * ClassName: MenuController <br/>
 * Function: 运营平台菜单管理Controller. <br/>
 * Date: 2017年2月22日 下午2:21:24 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/menuservice")
public class MenuController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private MenuParameterChecker parameterChecker;
	
	@Resource
	private MenuBiz menuBiz; 
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * add:新增菜单. <br/>
	 *
	 * @param vo
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseWebResponse add(@RequestBody MenuAddRequestVo vo){
		log.info("MenuController.add.req:" + JSON.toJSONString(vo));
		
		MenuAddRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//step1: 参数校验
			parameterChecker.checkMenuAddRequest(vo);
		
			//step2: 参数对象封装
			req = new MenuAddRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setCreateBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//step3: 调用新增方法
			resp = menuBiz.add(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("MenuController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * modify:修改菜单. <br/>
	 *
	 * @param vo
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public BaseWebResponse modify(@RequestBody MenuModifyRequestVo vo){
		log.info("MenuController.modify.req:" + JSON.toJSONString(vo));
		
		MenuModifyRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//step1: 参数校验
			parameterChecker.checkMenuModifyRequest(vo);
		
			//step2: 参数对象封装
			req = new MenuModifyRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//step3: 调用新增方法
			resp = menuBiz.modify(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("MenuController.modify.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * delete:删除菜单. <br/>
	 *
	 * @param vo
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public BaseWebResponse delete(@RequestBody MenuDeleteRequestVo vo){
		log.info("MenuController.delete.req:" + JSON.toJSONString(vo));
		
		MenuDeleteRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//step1: 参数校验
			parameterChecker.checkMenuDeleteRequest(vo);
		
			//step2: 参数对象封装
			req = new MenuDeleteRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setDeleteBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//step3: 调用删除方法
			resp = menuBiz.delete(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("MenuController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * query:查询菜单. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse query(@RequestBody MenuQueryRequestVo vo){
		
		log.info("MenuController.query.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			
			//step1: 参数校验
			parameterChecker.checkMenuQueryRequest(vo);
			
			//step2: 调用查询方法
			resp = menuBiz.query(vo.getMenuId());
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} 
		
		log.info("MenuController.query.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
	/**
	 * queryByPage:查询菜单（分页）. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querybypage",method=RequestMethod.POST)
	public BaseWebResponse queryByPage(@RequestBody MenuQueryByPageRequestVo vo){
		
		log.info("MenuController.queryByPage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		MenuQueryByPageRequest req = null;
		
		try {
			
			//step1: 参数对象封装
			req = new MenuQueryByPageRequest();
			PropertyUtils.copyProperties(req, vo);
		
			//step2: 调用查询方法
			resp = menuBiz.queryByPage(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} 
		
		log.info("MenuController.queryByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
	/**
	 * queryMenuPower:查询菜单及权限. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryMenuPower",method=RequestMethod.POST)
	public BaseWebResponse queryMenuPower(){
		
		log.info("MenuController.queryMenuPower.req:" + JSON.toJSONString(null));
		
		BaseWebResponse resp = null; 
		
		try {
			
			//step1: 调用查询方法
			resp = menuBiz.queryMenuPower();
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} 
		
		log.info("MenuController.queryMenuPower.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
}
