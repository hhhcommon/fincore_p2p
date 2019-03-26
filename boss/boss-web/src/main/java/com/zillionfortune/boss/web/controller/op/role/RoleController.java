/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.web.controller.op.role;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.zillionfortune.boss.web.controller.op.role.vo.RoleRequestVo;
import com.zillionfortune.boss.biz.role.RoleBiz;
import com.zillionfortune.boss.biz.role.dto.RoleRequest;

/**
 * ClassName: RoleController <br/>
 * Function: 运营台角色管理Controller. <br/>
 * Date: 2017年2月22日 上午9:27:29 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/roleservice")
public class RoleController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleBiz roleBiz;
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * add:添加角色. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseWebResponse add(@RequestBody RoleRequestVo vo,HttpSession session) {
		log.info("add.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if (StringUtils.isBlank(vo.getName())) {
				throw new BusinessException("角色名称不能为空");
			}
		
			//2.===参数对象封装
			RoleRequest req = new RoleRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setCreateBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			
			//3.===调用新增方法
			resp = roleBiz.add(req);
			
		} catch (BusinessException e){
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	
	/**
	 * modify:修改角色信息. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public BaseWebResponse modify(@RequestBody RoleRequestVo vo) {
		log.info("modify.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			if (vo.getRoleId() == null) {
				throw new BusinessException("角色Id不能为空");
			}
			if (StringUtils.isBlank(vo.getName())) {
				throw new BusinessException("角色名称不能为空");
			}
			
			//.===参数对象封装
			RoleRequest req = new RoleRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setId(vo.getRoleId());
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//.===调用更新方法
			resp = roleBiz.update(req);
			
		} catch (BusinessException e){
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("modify.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * delete:删除角色信息. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public BaseWebResponse delete(@RequestBody RoleRequestVo vo) {
		log.info("delete.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			if (vo.getRoleId() == null) {
				throw new BusinessException("角色Id不能为空");
			}
			RoleRequest req = new RoleRequest();
			req.setId(vo.getRoleId());
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//调用删除方法
			resp = roleBiz.delete(req);
			
		} catch (BusinessException e){
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("delete.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * queryByPage:角色信息查询（分页）. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querybypage",method=RequestMethod.POST)
	public BaseWebResponse queryByPage(@RequestBody RoleRequest vo) {
		log.info("queryByPage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			resp = roleBiz.queryByPage(vo);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("queryByPage.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * queryList:角色信息查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querylist",method=RequestMethod.POST)
	public BaseWebResponse queryList(@RequestBody RoleRequest vo) {
		log.info("RoleController.queryList.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null; 
		try {
			resp = roleBiz.queryList(vo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("RoleController.queryByPage.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * queryByPage:角色信息查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse query(@RequestBody RoleRequestVo vo) {
		log.info("query.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null;
		
		try {
			if (vo.getRoleId() == null) {
				throw new BusinessException("角色Id不能为空");
			}
			
			resp = roleBiz.query(vo.getRoleId());
			
		} catch (BusinessException e){
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
}
