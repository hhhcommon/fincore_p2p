package com.zillionfortune.boss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.bcel.generic.ANEWARRAY;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.constants.CommonConstants;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.enums.SignLevelEnum;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.BeanUtilsWrapper;
import com.zillionfortune.boss.common.utils.Endecrypt;
import com.zillionfortune.boss.common.utils.PassUtil;
import com.zillionfortune.boss.dal.dao.MenuDao;
import com.zillionfortune.boss.dal.dao.UserDao;
import com.zillionfortune.boss.dal.entity.Menu;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.User;
import com.zillionfortune.boss.facade.operation.OperationServiceFacade;
import com.zillionfortune.boss.facade.operation.dto.OperationLoginRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationLoginResponse;
import com.zillionfortune.boss.facade.operation.dto.OperationLoginUserResponse;
import com.zillionfortune.boss.facade.operation.dto.OperationRegisterRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationRegisterResponse;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuRequest;
import com.zillionfortune.boss.facade.operation.dto.OperationUserMenuResponse;
import com.zillionfortune.boss.service.check.OperationUserParameterChecker;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.redis.TokenManager;
import com.zillionfortune.boss.service.redis.UserRedisService;
import com.zillionfortune.boss.service.redis.model.RedisModel;
import com.zillionfortune.boss.service.role.RoleService;

@Component
public class OperationServiceFacadeImpl implements OperationServiceFacade {

	private static Logger log = LoggerFactory.getLogger(OperationServiceFacadeImpl.class);

	@Autowired
	UserDao opUserDao;

	@Autowired
	MenuDao opMenuDao;

	@Autowired
	OperationUserParameterChecker operationUserParameterChecker;

	@Autowired
	OperationHistoryService operationHistoryService;

	@Autowired
	UserRedisService userRedisService;

	@Autowired
	RoleService roleService;

	@Autowired
	TokenManager tokenManager;

	@Override
	public OperationRegisterResponse operationRegister(OperationRegisterRequest req) {
		OperationRegisterResponse rep = null;
		try {

			// 验证参数
			operationUserParameterChecker.checkOperataionRegisterChecker(req);

			// 判断用户是否存在
			User opCheck = new User();
			opCheck.setName(req.getEmail());
			User result = opUserDao.selectOpUser(opCheck);
			if (result != null) {
				rep = new OperationRegisterResponse(RespCode.SUCCESS.code(), ResultCode.USER_IS_EXIST.code(), ResultCode.USER_IS_EXIST.desc());
				return rep;
			}

			// 注册用户
			User op = new User();
			BeanUtilsWrapper.copyProperties(op, req);
			op.setPassword(PassUtil.getPassword(op.getPassword()));
			op.setCreateTime(new Date());
			op.setModifyTime(new Date());
			// op.setCreateBy(CommonConstants.TRADE_OPERATE_SYSTEM);
			// op.setModifyBy(CommonConstants.TRADE_OPERATE_SYSTEM);
			op.setCreateBy(op.getName());
			op.setModifyBy(op.getName());
			op.setName(req.getEmail().substring(0, req.getEmail().lastIndexOf("@")));// 用户名默认为邮箱的前缀

			opUserDao.insert(op);
			rep = new OperationRegisterResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

		} catch (BusinessException e) {
			rep = new OperationRegisterResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rep = new OperationRegisterResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), e.getMessage());
		}
		return rep;
	}

	@Override
	public OperationLoginResponse operationLogin(OperationLoginRequest req) {
		OperationLoginResponse rep = null;

		try {

			// 验证参数
			operationUserParameterChecker.checkOperataionUserLoginChecker(req);

			User op = new User();
			BeanUtilsWrapper.copyProperties(op, req);
			op.setPassword(PassUtil.getPassword(op.getPassword()));
			op.setDeleteFlag(DeleteFlag.EXISTS.code());
			User opUser = opUserDao.selectOpUser(op);
			if (opUser == null) {
				rep = new OperationLoginResponse(RespCode.SUCCESS.code(), ResultCode.NAME_OR_PASS_ERROR.code(), ResultCode.NAME_OR_PASS_ERROR.desc());
			} else {
				// 获取用户拥有的授权级别
				List<String> tempSignLevels = getSignLevels(opUser.getId());

				// 将信息存到redis中
				RedisModel redisModel = new RedisModel();
				BeanUtilsWrapper.copyProperties(redisModel, opUser);
				redisModel.setSignLevels(tempSignLevels);
				// 判断redis中key是否存在
				if (userRedisService.get(redisModel.getId().toString()) == null) {
					userRedisService.add(redisModel);
				} else {
					userRedisService.update(redisModel);
				}

				// 插入日志
				OperationHistory dto = new OperationHistory();
				dto.setUserId(opUser.getId());
				dto.setOperationType("userLogin");
				dto.setContent(opUser.getName() + "登录");
				dto.setCreateBy(opUser.getName());
				dto.setCreateTime(new Date());
				operationHistoryService.insertOperationHistory(dto);

				// 封装响应对象
				rep = new OperationLoginResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
				OperationLoginUserResponse or = new OperationLoginUserResponse();
				BeanUtilsWrapper.copyProperties(or, opUser);
				or.setSignLevels(tempSignLevels);
				rep.setData(or);
				rep.setSessionId(new Endecrypt().get3DESEncrypt(opUser.getId() + "", CommonConstants.ENDECRYPT_KEY));

				return rep;
			}
			rep = new OperationLoginResponse(RespCode.SUCCESS.code(), ResultCode.USER_ALREADY_DEL.code(), ResultCode.USER_ALREADY_DEL.desc());
		} catch (BusinessException e) {
			rep = new OperationLoginResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rep = new OperationLoginResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), e.getMessage());
		}

		return rep;
	}

	@Override
	public OperationUserMenuResponse operationUserMenu(OperationUserMenuRequest req) {
		OperationUserMenuResponse rep = null;
		try {
			// 验证参数
			operationUserParameterChecker.checkOperataionUserMenuChecker(req);

			// 根据用户名查询用户，判断是否存在
			User result = opUserDao.selectByPrimaryKey(req.getId());
			if (null == result) {
				rep = new OperationUserMenuResponse(RespCode.SUCCESS.code(), ResultCode.USER_ISNOT_EXIST.code(), ResultCode.USER_ISNOT_EXIST.desc());
				return rep;
			}

			List<Menu> resultList = new ArrayList<Menu>();
			// 查询用户拥有的菜单
			List<Menu> list = opMenuDao.selectMenuListByUserId(result.getId());
			List<Menu> tempList = new ArrayList<Menu>();
			if (list != null && list.size() > 0) {

				for (Menu omt : list) {
					if (omt.getParentId() == null || omt.getParentId() == 0) {
						tempList.add(omt);
					}
				}

				// 为一级菜单设置子菜单，getChild是递归调用的
				for (Menu menu : tempList) {
					menu.setSubList(getChild(menu, list));
					resultList.add(menu);
				}
			}

			rep = new OperationUserMenuResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			rep.setData(resultList);
		} catch (BusinessException e) {
			rep = new OperationUserMenuResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rep = new OperationUserMenuResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), e.getMessage());
		}

		return rep;
	}

	private List<Menu> getChild(Menu omt, List<Menu> list) {
		// 子菜单
		List<Menu> childList = new ArrayList<Menu>();
		for (Menu menu : list) {
			// 遍历所有节点，将父菜单id与子菜单的parentId比较
			if (menu.getParentId() != null && omt.getId() != null && menu.getParentId().intValue() == omt.getId().intValue()) {
				childList.add(menu);
			}
		}

		// 把子菜单的子菜单再循环一遍，判断子菜单下是否还有菜单
		for (Menu menu : childList) {
			// 递归
			menu.setSubList(getChild(menu, list));
		}
		// 递归退出条件
		if (childList.size() == 0) {
			return null;
		}

		return childList;
	}

	/**
	 * getSignLevels:获取用户拥有的授权级别. <br/>
	 * 
	 * @param userId
	 * @return
	 */
	private List<String> getSignLevels(Integer userId) {
		// 获取用户拥有的授权级别
		List<Role> roles = roleService.selectByUserId(userId);
		List<String> signLevels = new ArrayList<String>();
		if (roles != null && roles.size() > 0) {
			for (Role tempRole : roles) {
				if (StringUtils.isNotBlank(tempRole.getSignLevel()) && !signLevels.contains(tempRole.getSignLevel())) {
					signLevels.add(tempRole.getSignLevel());
				}
			}
		}
		return signLevels;
	}

}
