/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.web.controller.dictionary;

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
import com.alibaba.fastjson.JSONObject;
import com.zillionfortune.boss.biz.dictionary.DictionaryBiz;
import com.zillionfortune.boss.biz.dictionary.dto.DictionaryRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.dictionary.vo.DictionaryQueryKeysByCodesVO;

/**
 * ClassName: DictionaryController <br/>
 * Function: 数据字典Controller. <br/>
 * Date: 2017年2月22日 上午11:26:50 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/dictionaryservice")
public class DictionaryController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DictionaryBiz dictionaryBiz;
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * query:查询数据字典. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse query(@RequestBody DictionaryRequest vo) {
		log.info("query.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			resp = dictionaryBiz.query(vo);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * query:查询数据字典大类（分页）. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querybypage",method=RequestMethod.POST)
	public BaseWebResponse queryByPage(@RequestBody DictionaryRequest vo) {
		log.info("queryByPage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			resp = dictionaryBiz.queryByPage(vo);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("queryByPage.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * query:查询数据字典大类（分页）. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querykeysbypage",method=RequestMethod.POST)
	public BaseWebResponse queryKeysByPage(@RequestBody DictionaryRequest vo) {
		log.info("queryKeysByPage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			resp = dictionaryBiz.queryKeysByPage(vo);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("queryKeysByPage.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * add:添加数据字典. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseWebResponse add(@RequestBody DictionaryRequest vo) {
		log.info("add.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			if (StringUtils.isBlank(vo.getCode())) {
				throw new BusinessException("数据字典Code不能为空");
			}
			// key 为空 则代表添加大类 否则视为添加列表值
			if (StringUtils.isBlank(vo.getKey())) {
				// 入参非空判断
				if (StringUtils.isBlank(vo.getName())) {
					throw new BusinessException("数据字典名称不能为空");
				}
			} else {
				if (StringUtils.isBlank(vo.getValue())) {
					throw new BusinessException("数据字典value不能为空");
				}
			}
			try {
				vo.setCreateBy(httpSessionUtils.getCuruser().getName());
				vo.setUserId(httpSessionUtils.getCuruser().getId());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常",e);
			}
			
			if(vo.getCreateBy()==null){
				vo.setCreateBy("SYS");
			}
			
			resp = dictionaryBiz.add(vo);
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
	 * modify:修改数据字典. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public BaseWebResponse modify(@RequestBody DictionaryRequest vo) {
		log.info("modify.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			if (vo.getDictionaryId() == null) {
				throw new BusinessException("数据字典id不能为空");
			}
			try {
				vo.setModifyBy(httpSessionUtils.getCuruser().getName());
				vo.setUserId(httpSessionUtils.getCuruser().getId());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常",e);
			}
			
			if(vo.getCreateBy()==null){
				vo.setCreateBy("SYS");
			}
			
			resp = dictionaryBiz.modify(vo);
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
	 * delete:删除数据字典. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public BaseWebResponse delete(@RequestBody JSONObject jsonObject) {
		log.info("delete.req:" + JSON.toJSONString(jsonObject));
		
		BaseWebResponse resp = null; 
		
		try {
			String code = jsonObject.getString("code");
			String key = jsonObject.getString("key");
			// 入参非空判断
			if (StringUtils.isBlank(code)) {
				throw new BusinessException("数据字典编码不能为空");
			}
			
			// 封装参数对象
			DictionaryRequest req = new DictionaryRequest();
			req.setCode(code);
			req.setKey(key);
			try {
				req.setModifyBy(httpSessionUtils.getCuruser().getName());
				req.setUserId(httpSessionUtils.getCuruser().getId());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常",e);
			}
			
			if(req.getCreateBy()==null){
				req.setCreateBy("SYS");
			}
			
			resp = dictionaryBiz.delete(req);
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
	 * queryKeysByCodes:根据数据字典Code查询对应的字典列表. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querykeysbycodes",method=RequestMethod.POST)
	public BaseWebResponse queryKeysByCodes(@RequestBody DictionaryQueryKeysByCodesVO vo) {
		log.info("queryKeysByCodes.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			if (vo == null) {
				throw new BusinessException("请求对象不能为空");
			}
			
			if (vo.getCodes() == null || vo.getCodes().size() == 0) {
				throw new BusinessException("字典code列表不能为空");
			}
			
			resp = dictionaryBiz.queryKeysByCodes(vo.getCodes());
		} catch (BusinessException e){
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("queryKeysByCodes.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
}