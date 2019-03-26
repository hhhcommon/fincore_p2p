/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.history.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.history.HistoryBiz;
import com.zillionfortune.boss.biz.history.dto.HistoryQueryByPageRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.dal.entity.HistoryQueryByPageConvert;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.User;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.user.UserService;

/**
 * ClassName: HistoryBizImpl <br/>
 * Function: 运营平台日志管理biz层接口实现. <br/>
 * Date: 2017年2月27日 下午2:57:26 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class HistoryBizImpl implements HistoryBiz {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OperationHistoryService historyService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 根据主键查询日志.
	 * @see com.zillionfortune.boss.biz.history.HistoryBiz#query(java.lang.Integer)
	 */
	@Override
	public BaseWebResponse query(Integer operationHistoryId) {

		log.info("HistoryBizImpl.query.req:" + "{operationHistoryId:"+operationHistoryId+"}");
		
		BaseWebResponse resp = null;
	
		try {
			
			//1.===执行查询操作
			OperationHistory history = historyService.selectByPrimaryKey(operationHistoryId);
			
			//2.===处理反馈结果
			if(history == null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.CAN_NOT_QUERY_HISTORY.code(),
						ResultCode.CAN_NOT_QUERY_HISTORY.desc());
				
				return resp;
			}
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			resp.setData(history);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("HistoryBizImpl.query.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	
	}
	
	/**
	 * 查询日志（分页）.
	 * @see com.zillionfortune.boss.biz.history.HistoryBiz#queryByPage(com.zillionfortune.boss.biz.history.dto.HistoryQueryByPageRequest)
	 */
	@Override
	public BaseWebResponse queryByPage(HistoryQueryByPageRequest req) {

		log.info("HistoryBizImpl.queryByPage.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		try {
			List<HistoryQueryByPageConvert> historyList = null;
			
			//step1: 通过用户名获取用户Id
			User user = new User();
			if (!StringUtils.isBlank(req.getUserName())) {
				user = userService.selectByUserName(req.getUserName());
				if (user == null) {
					resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
					Map<String,Object> respMap = new HashMap<String,Object>();
					respMap.put("totalCount", 0);
					respMap.put("pageSize", req.getPageSize());
					respMap.put("pageNo", req.getPageNo());
					respMap.put("totalPage", 0);
					respMap.put("list",historyList);
					
					resp.setData(respMap);

					return resp;
				}
			}
			
			//step2: 创建查询用请求对象
			OperationHistory history = new OperationHistory();
			history.setUserId(user.getId());
			history.setPageSize(req.getPageSize());
			history.setPageStart(req.getPageStart());
			
			
			//step3: 执行查询操作
			int totalCount = historyService.selectBySelectiveCount(history);
			
			
			if(totalCount > 0){
				historyList = historyService.queryByPage(history);
			}
			
			//step4: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());
			
			if(totalCount > 0 && req.getPageSize() !=null ){
				respMap.put("totalPage", new PageBean().countPageCount(totalCount, req.getPageSize()));
			}
			
			respMap.put("list",historyList);
			
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
		
		log.info("HistoryBizImpl.queryByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}

}
