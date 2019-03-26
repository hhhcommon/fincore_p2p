package com.zillionfortune.boss.facade.operation;

import com.zillionfortune.boss.facade.operation.dto.OperationLoginRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationLoginResponse;
import com.zillionfortune.boss.facade.operation.dto.OperationRegisterRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationRegisterResponse;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuResponse;

public interface OperationServiceFacade {
	
	/**
	 * 用户注册
	 * @param req
	 * @return
	 */
	public OperationRegisterResponse operationRegister(OperationRegisterRequest req);
	
	
	/**
	 * 用户登录
	 * @param req
	 * @return
	 */
	public OperationLoginResponse operationLogin(OperationLoginRequest req);
	
	
	/**
	 * 获取用户菜单
	 * @param req
	 * @return
	 */
	public OperationUserMenuResponse operationUserMenu(OperationUserMenuRequest req);
}
