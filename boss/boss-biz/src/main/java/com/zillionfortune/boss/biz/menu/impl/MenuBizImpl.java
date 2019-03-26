/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.menu.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.menu.MenuBiz;
import com.zillionfortune.boss.biz.menu.dto.MenuAddRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuDeleteRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuModifyRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuQueryByPageRequest;
import com.zillionfortune.boss.biz.menu.dto.MenuQueryByPageResponse;
import com.zillionfortune.boss.biz.menu.dto.QueryMenuPowerResponse;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.IsValid;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.StringUtils;
import com.zillionfortune.boss.dal.entity.Menu;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.menu.MenuService;
import com.zillionfortune.boss.service.power.PowerService;

/**
 * ClassName: MenuBizImpl <br/>
 * Function: 运营后台菜单管理biz层接口实现. <br/>
 * Date: 2017年2月22日 下午2:44:54 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class MenuBizImpl implements MenuBiz {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PowerService powerService;
	
	@Autowired
	private OperationHistoryService operationHistoryService;
	
	/**
	 * 新增菜单.
	 * @see com.zillionfortune.boss.biz.menu.MenuBiz#add(com.zillionfortune.boss.biz.menu.dto.MenuAddRequest)
	 */
	@Override
	public BaseWebResponse add(MenuAddRequest req) {
		log.info("MenuBizImpl.add.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			//step1: 验证菜单名称是否已经存在
			Menu currentOpMenu = new Menu();
			currentOpMenu.setName(req.getName());
			currentOpMenu.setDeleteFlag(DeleteFlag.EXISTS.code());
			List<Menu> opMenuList = menuService.selectBySelective(currentOpMenu);
			if (opMenuList != null && opMenuList.size() > 0) {
				boolean nameExistFlag = checkNameRepeat(req.getParentId(), opMenuList);
				if (nameExistFlag) {
					// 同一级别中，菜单名称存在的场合，返回菜单已存在
					resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.MENU_IS_EXIST.code(),
							ResultCode.MENU_IS_EXIST.desc());

					log.info("MenuBizImpl.add.resp:" + JSON.toJSONString(resp));

					return resp;
				}
			}
			
			//step2: 创建菜单对象
			Menu opMenu = new Menu();
			opMenu.setName(req.getName()); // 菜单名称
			opMenu.setCreateBy(req.getCreateBy()); // 创建者
			opMenu.setCreateTime(new Date()); // 创建时间
			opMenu.setDeleteFlag(DeleteFlag.EXISTS.code()); // 删除标识
			opMenu.setDisplayOrder(req.getDisplayOrder()); // 显示顺序
			opMenu.setIcon(req.getIcon()); // 图标
			opMenu.setIsValid(req.getIsValid()); // 是否有效
			opMenu.setMemo(req.getRemark());  // 备注
			opMenu.setReferUrl(req.getUrl()); // 菜单链接
			opMenu.setParentId(req.getParentId()); // 父菜单Id
			
			//step3: 执行新增操作
			menuService.add(opMenu);
			
			//step4: 日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("add");
			history.setCreateBy(req.getCreateBy());
			history.setContent("菜单管理->新增菜单");
			
			operationHistoryService.insertOperationHistory(history);
			
			//step5: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("menuId", opMenu.getId());
			respMap.put("name", opMenu.getName());
			
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		}
		
		log.info("MenuBizImpl.add.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}
	
	/**
	 * 修改菜单.
	 * @see com.zillionfortune.boss.biz.menu.MenuBiz#modify(com.zillionfortune.boss.biz.menu.dto.MenuModifyRequest)
	 */
	@Override
	public BaseWebResponse modify(MenuModifyRequest req) {
		log.info("MenuBizImpl.modify.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			//step1: 验证菜单是否存在
			Menu currentOpMenu = menuService.selectByPrimaryKey(req.getMenuId());
			if (currentOpMenu == null || currentOpMenu.getDeleteFlag() == DeleteFlag.DELETED.code()) {
				// 菜单不存在场合，返回菜单不存在
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),
						ResultCode.MENU_ISNOT_EXIST.code(), ResultCode.MENU_ISNOT_EXIST.desc());

				log.info("MenuBizImpl.modify.resp:" + JSON.toJSONString(resp));

				return resp;
			}
			
			//step2: 创建菜单对象
			Menu opMenu = new Menu();
			opMenu.setId(req.getMenuId()); // 菜单Id
			opMenu.setName(req.getName()); // 菜单名称
			opMenu.setIsValid(req.getIsValid()); // 是否有效
			opMenu.setDisplayOrder(req.getDisplayOrder()); // 显示顺序
			opMenu.setReferUrl(req.getUrl()); // 菜单链接
			opMenu.setParentId(req.getParentId()); // 父菜单Id
			opMenu.setIcon(req.getIcon()); // 图标
			opMenu.setMemo(req.getRemark());  // 备注
			opMenu.setModifyBy(req.getModifyBy()); // 修改者
			opMenu.setModifyTime(new Date()); // 修改时间
			
			//step3: 执行更新操作
			menuService.update(opMenu);
			
			//step4: 日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("modify");
			history.setCreateBy(req.getModifyBy());
			history.setContent("菜单管理->修改菜单");
			
			operationHistoryService.insertOperationHistory(history);
			
			//step5: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("menuId", opMenu.getId());
			respMap.put("name", opMenu.getName());
			
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		}
		
		log.info("MenuBizImpl.modify.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}
	
	/**
	 * 删除菜单.
	 * @see com.zillionfortune.boss.biz.menu.MenuBiz#delete(com.zillionfortune.boss.biz.menu.dto.MenuDeleteRequest)
	 */
	@Override
	public BaseWebResponse delete(MenuDeleteRequest req) {
		log.info("MenuBizImpl.delete.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			//step1: 验证菜单是否存在
			Menu currentOpMenu = menuService.selectByPrimaryKey(req.getMenuId());
			if (currentOpMenu == null || currentOpMenu.getDeleteFlag() == DeleteFlag.DELETED.code()) {
				// 菜单不存在的场合，返回菜单不存在
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),
						ResultCode.MENU_ISNOT_EXIST.code(), ResultCode.MENU_ISNOT_EXIST.desc());

				log.info("MenuBizImpl.delete.resp:" + JSON.toJSONString(resp));

				return resp;
			}
			
			//step2: 判断菜单下是否存在有效子菜单
			Menu tempMenu = new Menu();
			tempMenu.setParentId(req.getMenuId());
			tempMenu.setDeleteFlag(DeleteFlag.EXISTS.code());
			List<Menu> menuList = menuService.selectBySelective(tempMenu);
			if (menuList != null && menuList.size() > 0) {
				// 菜单下存在子菜单的场合，返回【存在子菜单，不能删除】
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),
						ResultCode.SUB_MENU_IS_EXIST.code(), ResultCode.SUB_MENU_IS_EXIST.desc());

				log.info("MenuBizImpl.delete.resp:" + JSON.toJSONString(resp));

				return resp;
			}
			
			//step3: 判断菜单是否分配权限
			Power opPower = new Power();
			opPower.setMenuId(req.getMenuId());
			opPower.setDeleteFlag(DeleteFlag.EXISTS.code());
			List<Power> opPowerList = powerService.selectBySelective(opPower);
			if (opPowerList != null && opPowerList.size() > 0) {
				// 菜单已分配权限的场合，返回【菜单已分配权限无法删除】
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),
						ResultCode.MENU_POWER_IS_EXIST.code(), ResultCode.MENU_POWER_IS_EXIST.desc());

				log.info("MenuBizImpl.delete.resp:" + JSON.toJSONString(resp));

				return resp;
			}
			
			//step4: 执行更新操作
			Menu opMenu = new Menu();
			opMenu.setId(req.getMenuId()); // 菜单Id
			opMenu.setDeleteFlag(DeleteFlag.DELETED.code()); // 删除标识
			opMenu.setModifyBy(req.getDeleteBy()); // 删除者
			opMenu.setModifyTime(new Date()); // 修改时间
			
			menuService.delete(opMenu);
			
			//step5: 日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("delete");
			history.setCreateBy(req.getDeleteBy());
			history.setContent("菜单管理->删除菜单");
			
			operationHistoryService.insertOperationHistory(history);
			
			//step6: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("menuId", req.getMenuId());
			
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		}
		
		log.info("MenuBizImpl.delete.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}

	/**
	 * 查询菜单.
	 * @see com.zillionfortune.boss.biz.menu.MenuBiz#query(java.lang.Integer)
	 */
	@Override
	public BaseWebResponse query(Integer menuId) {
		log.info("MenuBizImpl.query.req:" + "{menuId:"+menuId+"}");
		
		BaseWebResponse resp;
		
		try {
			//step1: 执行查询操作
			Menu opMenu = menuService.selectByPrimaryKey(menuId);
			
			//step2: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			String tempMenuId = "";
			if (opMenu.getId() != null) {
				tempMenuId = opMenu.getId().toString();
			}
			respMap.put("menuId", tempMenuId); // 菜单Id
			respMap.put("name", opMenu.getName()); // 菜单名称
			respMap.put("url", opMenu.getReferUrl()); // 请求url
			String parentId = "";
			if (opMenu.getParentId() != null) {
				parentId = opMenu.getParentId().toString();
			}
			respMap.put("parentId", parentId); // 父菜单Id
			String displayOrder = "";
			if (opMenu.getDisplayOrder() != null) {
				displayOrder = opMenu.getDisplayOrder().toString();
			}
			respMap.put("displayOrder", displayOrder); // 显示顺序
			respMap.put("icon", opMenu.getIcon()); // 图标
			respMap.put("remark", opMenu.getMemo()); // 备注
			respMap.put("createTime", opMenu.getCreateTime()); // 创建时间
			String tempIsValid = "";
			if (opMenu.getIsValid() != null) {
				tempIsValid = opMenu.getIsValid().toString();
			}
			respMap.put("isValid", tempIsValid); // 是否有效
			
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		}
		
		log.info("MenuBizImpl.query.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}

	@Override
	public BaseWebResponse queryByPage(MenuQueryByPageRequest req) {
		log.info("MenuBizImpl.queryByPage.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			//step1: 执行父菜单查询
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", req.getUserId()); // 用户Id
			paramMap.put("parentId", req.getParentId()); // 父菜单Id
			paramMap.put("name", req.getName()); // 菜单名称
			paramMap.put("deleteFlag", DeleteFlag.EXISTS.code()); // 删除标识
			
			List<Menu> opMenuList = menuService.queryByPage(paramMap);
			
			List<MenuQueryByPageResponse> list = new ArrayList<MenuQueryByPageResponse>();
			List<MenuQueryByPageResponse> subList = new ArrayList<MenuQueryByPageResponse>();
			if (opMenuList != null && opMenuList.size() > 0) {
				// 归类一级菜单、二级菜单
				for (Menu menu : opMenuList) {
					MenuQueryByPageResponse respMenu = new MenuQueryByPageResponse();
					copyProperties(respMenu, menu);
					if (respMenu.getParentId() == null) {
						list.add(respMenu); // 一级菜单
					} else {
						subList.add(respMenu); // 二级菜单
					}
				}
				
				// 将二级菜单归类到对应的父菜单中
				for (MenuQueryByPageResponse listMenu : list) {
					List<MenuQueryByPageResponse> tempSubList = new ArrayList<MenuQueryByPageResponse>();
					
					for (MenuQueryByPageResponse subListMenu : subList) {
						if (subListMenu.getParentId() != null 
								&& listMenu.getMenuId() != null
								&& subListMenu.getParentId().intValue() == listMenu.getMenuId().intValue()) {
							tempSubList.add(subListMenu);
						}
					}
					listMenu.setSubList(tempSubList);
				}
				
				// 将没有父级菜单的二级菜单加入到一级菜单队列中
				for (MenuQueryByPageResponse subListMenu2 : subList) {
					boolean flg = false;
					for (MenuQueryByPageResponse listMenu2 : list) {
						// 能找到父级菜单的场合
						if (subListMenu2.getParentId() != null 
								&& listMenu2.getMenuId() != null
								&& subListMenu2.getParentId().intValue() == listMenu2.getMenuId().intValue()) {
							flg = true;
						}
					}
					
					if (flg == false) {
						list.add(subListMenu2);
					}
				}
				
			}
			
			//step2: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			int totalCount = 0;
			if(list != null && list.size() > 0){
				totalCount = list.size();
			}
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());
			respMap.put("list", list);
			
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		}
		
		log.info("MenuBizImpl.queryByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
	/**
	 * 查询菜单及权限.
	 * @see com.zillionfortune.boss.biz.menu.MenuBiz#queryMenuPower()
	 */
	@Override
	public BaseWebResponse queryMenuPower() {
		log.info("MenuBizImpl.queryMenuPower.req:" + JSON.toJSONString(null));
		
		BaseWebResponse resp;
		
		try {
			//step1: 查询菜单
			Menu opMenu = new Menu();
			opMenu.setIsValid(IsValid.VALID.code());
			opMenu.setDeleteFlag(DeleteFlag.EXISTS.code());
			List<Menu> menulist = menuService.selectBySelective(opMenu);
			
			//step2: 查询权限
			Power opPower =  new Power();
			opPower.setDeleteFlag(DeleteFlag.EXISTS.code());
			List<Power> powerList = powerService.selectBySelective(opPower);
			
			//step3: 归类菜单
			List<QueryMenuPowerResponse> list = new ArrayList<QueryMenuPowerResponse>();
			List<QueryMenuPowerResponse> respList = new ArrayList<QueryMenuPowerResponse>();
			List<Menu> tempSubList = new ArrayList<Menu>();
			if (menulist != null && menulist.size() > 0) {
				// 归类一级菜单、二级菜单
				for (Menu menu : menulist) {
					QueryMenuPowerResponse respMenuPower = new QueryMenuPowerResponse();
					respMenuPower.setId(menu.getId().toString());
					respMenuPower.setLabel(menu.getName());
					respMenuPower.setParentId(menu.getParentId() != null ? menu.getParentId().toString() : null);
					if (menu.getParentId() == null) {
						list.add(respMenuPower); // 一级菜单
					} else {
						tempSubList.add(menu); // 二级菜单
					}
				}

				// 将权限归类到二级菜单中
				List<QueryMenuPowerResponse> subList = new ArrayList<QueryMenuPowerResponse>();
				for (Menu tempMenu : tempSubList) {
					List<QueryMenuPowerResponse> tempPowerList = new ArrayList<QueryMenuPowerResponse>();
					QueryMenuPowerResponse tempMenuPower = new QueryMenuPowerResponse();
					tempMenuPower.setId(tempMenu.getId().toString());
					tempMenuPower.setLabel(tempMenu.getName());
					tempMenuPower.setParentId(tempMenu.getParentId() != null ? tempMenu.getParentId().toString() : null);

					for (Power power : powerList) {
						if (tempMenu.getId() != null
								&& power.getMenuId() != null
								&& tempMenu.getId().intValue() == power.getMenuId().intValue()) {
							// 将权限管理到对应的二级菜单中
							QueryMenuPowerResponse menuPower = new QueryMenuPowerResponse();
							menuPower.setId(power.getId().toString() + "_powerId");
							menuPower.setLabel(power.getName());
							tempPowerList.add(menuPower);
						}
					}
					if (tempPowerList.size() == 0) {
						continue; // 如果二级菜单没有分配权限，不显示二级菜单
					}
					tempMenuPower.setSubList(tempPowerList);
					subList.add(tempMenuPower);
				}

				// 将二级菜单归类到对应的父菜单中
				for (QueryMenuPowerResponse listMenu : list) {
					List<QueryMenuPowerResponse> subList2 = new ArrayList<QueryMenuPowerResponse>();

					for (QueryMenuPowerResponse subListMenu : subList) {
						if (StringUtils.isNotEmpty(subListMenu.getParentId())
								&& subListMenu.getParentId().equals(listMenu.getId())) {
							subList2.add(subListMenu);
						}
					}
					if (subList2.size() == 0) {
						continue; // 如果一级菜单下面的二级菜单都没有分配权限，不显示一级菜单
					}
					listMenu.setSubList(subList2);
				}

				// 如果一级菜单下面的二级菜单都没有分配权限，不显示一级菜单
				for (QueryMenuPowerResponse listMenu : list) {
					if (listMenu.getSubList() == null || listMenu.getSubList().size() == 0) {
						continue;
					}
					respList.add(listMenu);
				}
			}
			
			//step2: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("list", respList);
			
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		}
		
		log.info("MenuBizImpl.queryMenuPower.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
	
	/**
	 * copyProperties:将OpMenu型转换成MenuQueryByPageResponse型. <br/>
	 *
	 * @param resp
	 * @param opMenu
	 */
	private void copyProperties(MenuQueryByPageResponse resp, Menu opMenu) {
		resp.setMenuId(opMenu.getId());
		resp.setCode(opMenu.getCode());
		resp.setCreateBy(opMenu.getCreateBy());
		resp.setCreateTime(opMenu.getCreateTime());
		resp.setDeleteFlag(opMenu.getDeleteFlag());
		resp.setDisplayOrder(opMenu.getDisplayOrder());
		resp.setIcon(opMenu.getIcon());
		resp.setIsValid(opMenu.getIsValid());
		resp.setRemark(opMenu.getMemo());
		resp.setUrl(opMenu.getReferUrl());
		resp.setModifyBy(opMenu.getModifyBy());
		resp.setModifyTime(opMenu.getModifyTime());
		resp.setName(opMenu.getName());
		resp.setParentId(opMenu.getParentId());
	}
	
	/**
	 * checkNameRepeat:判断同一级菜单中是否有重复的菜单名称. <br/>
	 *
	 * @param parentId
	 * @param list
	 * @return
	 */
	private boolean checkNameRepeat(Integer parentId, List<Menu> list) {
		boolean flg = false;
		if (parentId == null) {
			for (Menu menu : list) {
				if (menu.getParentId() == null) {
					flg = true;
					return flg;
				}
			}
		} else {
			for (Menu menu : list) {
				if (menu.getParentId() != null) {
					flg = true;
					return flg;
				}
			}
		}
		return flg;
	}
	
}
