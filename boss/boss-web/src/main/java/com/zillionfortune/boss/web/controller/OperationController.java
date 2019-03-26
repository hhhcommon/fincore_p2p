package com.zillionfortune.boss.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.facade.operation.OperationServiceFacade;
import com.zillionfortune.boss.facade.operation.dto.OperationLoginRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationLoginResponse;
import com.zillionfortune.boss.facade.operation.dto.OperationRegisterRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationRegisterResponse;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuResponse;
import com.zillionfortune.boss.service.redis.TokenManager;
import com.zillionfortune.boss.service.redis.UserRedisService;
import com.zillionfortune.boss.service.redis.model.RedisModel;


/**
 * 运营系统 http接口
 * @author fangyang
 *
 */
@Controller
@RequestMapping(value="/operationService")
public class OperationController {
	
	@Autowired
	OperationServiceFacade operationServiceFacade;
	
	/**
	 * 用户注册接口
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/operationRegister", method = RequestMethod.POST)
	@ResponseBody
	public OperationRegisterResponse operationRegister(@RequestBody OperationRegisterRequest req){
		OperationRegisterResponse response=operationServiceFacade.operationRegister(req);
		return response;
	}
	
	
	/**
	 * 用户登录接口
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/operationLogin", method = RequestMethod.POST)
	@ResponseBody
	public OperationLoginResponse operationLogin(@RequestBody OperationLoginRequest req,HttpSession session){
		OperationLoginResponse response=operationServiceFacade.operationLogin(req);
		return response;
	}
	
	
	/**
	 * 用户获取菜单接口
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/operationUserMenu", method = RequestMethod.POST)
	@ResponseBody
	public OperationUserMenuResponse operationUserMenu(@RequestBody OperationUserMenuRequest req){
		OperationUserMenuResponse response=operationServiceFacade.operationUserMenu(req);
		return response;
	}
	
}
