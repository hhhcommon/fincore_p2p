/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.history;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.web.controller.op.history.check.HistoryParameterChecker;
import com.zillionfortune.boss.web.controller.op.history.vo.HistoryQueryByPageRequestVo;
import com.zillionfortune.boss.web.controller.op.history.vo.HistoryQueryRequestVo;
import com.zillionfortune.boss.biz.history.HistoryBiz;
import com.zillionfortune.boss.biz.history.dto.HistoryQueryByPageRequest;

/**
 * ClassName: HistoryController <br/>
 * Function: 运营平台日志管理Controller. <br/>
 * Date: 2017年2月27日 下午2:21:47 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/operationhistoryservice")
public class HistoryController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private HistoryParameterChecker parameterChecker;
	
	@Resource
	private HistoryBiz historyBiz; 
	
	/**
	 * query:查询日志. <br/>
	 *
	 * @param vo
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse query(@RequestBody HistoryQueryRequestVo vo,HttpSession session){
		log.info("HistoryController.query.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			
			//step1: 参数校验
			parameterChecker.checkHistoryQueryRequest(vo);
			
			//step2: 调用查询方法
			resp = historyBiz.query(vo.getOperationHistoryId());
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} 
		
		log.info("HistoryController.query.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
	/**
	 * queryByPage:查询权限（分页）. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querybypage",method=RequestMethod.POST)
	public BaseWebResponse queryByPage(@RequestBody HistoryQueryByPageRequestVo vo){
		
		log.info("HistoryController.queryByPage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		
		try {
			//step1: 参数对象封装
			HistoryQueryByPageRequest req = new HistoryQueryByPageRequest();
			PropertyUtils.copyProperties(req, vo);
		
			//step2: 调用查询方法
		    if(req.getPageSize() != null && req.getPageNo() != null){
		    	PageBean pageBean = new PageBean(vo.getPageNo(), vo.getPageSize());
		    	req.setPageStart(pageBean.getPageStart());
		    }
		  
			resp = historyBiz.queryByPage(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} 
		
		log.info("HistoryController.queryByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
}
