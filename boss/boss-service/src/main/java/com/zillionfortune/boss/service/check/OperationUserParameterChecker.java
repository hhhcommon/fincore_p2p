package com.zillionfortune.boss.service.check;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.MatchUtils;
import com.zillionfortune.boss.facade.operation.dto.OperationLoginRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationRegisterRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuRequest;

/**
 * 用户相关接口参数验证
 * @author fangyang
 *
 */
@Component
public class OperationUserParameterChecker {
	
	/**
	 * 验证登录参数
	 * @param req
	 * @throws BusinessException
	 */
	public void checkOperataionUserLoginChecker(OperationLoginRequest req) throws BusinessException{
		
		if(null==req){
			throw new BusinessException("请求对象不能为空");
		}
		
		if(req.getName()==null||req.getName().trim().equals("")||req.getPassword()==null||req.getPassword().trim().equals("")){
			throw new BusinessException("用户名或密码不能为空");
		}
		
	}
	
	/**
	 * 验证获取菜单参数
	 * @param req
	 * @throws BusinessException
	 */
	public void checkOperataionUserMenuChecker(OperationUserMenuRequest req) throws BusinessException{
		
		if(null==req){
			throw new BusinessException("请求对象不能为空");
		}
		
		if(req.getId()==null){
			throw new BusinessException("用户ID为空");
		}
		
	}

	/**
	 * 注册用户验证参数
	 * @param req
	 * @throws BusinessException
	 */
	public void checkOperataionRegisterChecker(OperationRegisterRequest req) throws BusinessException{
		
		if(null==req){
			throw new BusinessException("请求对象不能为空");
		}
		
		if(req.getEmail()==null||req.getEmail().trim().equals("")){
			throw new BusinessException("邮箱不能为空");
		}
		
		if(req.getMobile()==null||req.getMobile().trim().equals("")){
			throw new BusinessException("手机号码不能为空");
		}
		
		if(req.getPassword()==null||req.getPassword().trim().equals("")){
			throw new BusinessException("密码不能为空");
		}
		
		if(!MatchUtils.isMobile(req.getMobile())){
			throw new BusinessException("手机号码格式不正确");
		}
		
		if(!MatchUtils.isEmail(req.getEmail())){
			throw new BusinessException("邮箱格式不正确");
		}
		
	}
}
