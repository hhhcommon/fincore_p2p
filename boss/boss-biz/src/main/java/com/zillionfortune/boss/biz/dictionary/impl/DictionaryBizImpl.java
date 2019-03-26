/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.dictionary.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.dictionary.DictionaryBiz;
import com.zillionfortune.boss.biz.dictionary.dto.DictionaryKeysResponseDto;
import com.zillionfortune.boss.biz.dictionary.dto.DictionaryRequest;
import com.zillionfortune.boss.biz.dictionary.dto.DictionaryResponseDto;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.DictionaryStatus;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.utils.BeanUtilsWrapper;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.dal.entity.Dictionary;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.service.dictionary.DictionaryService;
import com.zillionfortune.boss.service.history.OperationHistoryService;

/**
 * ClassName: DictionaryBizImpl <br/>
 * Function: 数据字典Biz接口实现. <br/>
 * Date: 2017年2月22日 上午11:17:54 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Component
public class DictionaryBizImpl implements DictionaryBiz {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private OperationHistoryService operationHistoryService;
	
	/**
	 * @see com.zillionfortune.boss.biz.dictionary.DictionaryBiz#query(com.zillionfortune.boss.biz.dictionary.dto.DictionaryRequest)
	 */
	@Override
	public BaseWebResponse query(DictionaryRequest req) {
		log.info("query.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			
			//1.===创建角色对象
			Dictionary opDictionary = new Dictionary();
			opDictionary.setId(req.getDictionaryId());
			opDictionary.setCode(req.getCode());
			opDictionary.setKey(req.getKey());
			opDictionary.setDeleteFlag(DeleteFlag.EXISTS.code());
			// 2. 执行查询
			List<Dictionary> dictionaries = dictionaryService.queryList(opDictionary);
			//3.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			// 转换结果集
			DictionaryKeysResponseDto  dictionaryKeysResponseDto = new DictionaryKeysResponseDto();
			if (dictionaries != null && ! dictionaries.isEmpty()) {
				Dictionary dictionary = dictionaries.get(0);
				BeanUtilsWrapper.copyProperties(dictionaryKeysResponseDto, dictionary);
				dictionaryKeysResponseDto.setDictionaryId(dictionary.getId());
				resp.setData(dictionaryKeysResponseDto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		}
		
		log.info("query.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
	/**
	 * @see com.zillionfortune.boss.biz.dictionary.DictionaryBiz#queryByPage(com.zillionfortune.boss.biz.dictionary.dto.DictionaryRequest)
	 */
	@Override
	public BaseWebResponse queryByPage(DictionaryRequest req) {
		log.info("queryByPage.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			//1.===创建角色对象
			Dictionary opDictionary = new Dictionary();
			BeanUtilsWrapper.copyProperties(opDictionary, req);
			opDictionary.setDeleteFlag(DeleteFlag.EXISTS.code());
			// 2. 执行查询
			int totalCount = dictionaryService.selectDictionaryByPageCount(opDictionary);
			List<DictionaryResponseDto> rsList = new ArrayList<DictionaryResponseDto>();
			if (totalCount > 0) {
				List<Dictionary> opDictionaries = dictionaryService.selectDictionaryByPage(opDictionary);
				// 转换结果集
				if (opDictionaries != null) {
					for (Dictionary dictionary : opDictionaries) {
						DictionaryResponseDto dictionaryResponseDto = new DictionaryResponseDto();
						BeanUtilsWrapper.copyProperties(dictionaryResponseDto, dictionary);
						dictionaryResponseDto.setDictionaryId(dictionary.getId());
						rsList.add(dictionaryResponseDto);
					}
				}
				
			}
			//3.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());

			Map<String,Object> respMap = new HashMap<String, Object>();
			respMap.put("list", rsList);
			
			// 设置分页信息
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());
			
			if(totalCount > 0 && req.getPageSize() !=null ){
				respMap.put("totalPage", new PageBean().countPageCount(totalCount, req.getPageSize()));
			}
			
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		}
		
		log.info("queryByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
	/**
	 * @see com.zillionfortune.boss.biz.dictionary.DictionaryBiz#queryKeysByPage(com.zillionfortune.boss.biz.dictionary.dto.DictionaryRequest)
	 */
	@Override
	public BaseWebResponse queryKeysByPage(DictionaryRequest req) {
		log.info("queryKeysByPage.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			//1.===创建角色对象
			Dictionary opDictionary = new Dictionary();
			BeanUtilsWrapper.copyProperties(opDictionary, req);
			opDictionary.setDeleteFlag(DeleteFlag.EXISTS.code());
			// 2. 执行查询
			int totalCount = dictionaryService.selectDictionaryKeysByPageCount(opDictionary);
			List<DictionaryKeysResponseDto> rsList = new ArrayList<DictionaryKeysResponseDto>();
			List<DictionaryKeysResponseDto> tempList = new ArrayList<DictionaryKeysResponseDto>();
			if (totalCount > 0) {
				List<Dictionary> opDictionaries = dictionaryService.selectDictionaryKeysByPage(opDictionary);
				// 转换结果集
				if (CollectionUtils.isNotEmpty(opDictionaries)) {
					for (Iterator iterator = opDictionaries.iterator(); iterator.hasNext();) {
						Dictionary dictionary = (Dictionary) iterator.next();
						DictionaryKeysResponseDto dictionaryKeysResponseDto = new DictionaryKeysResponseDto();
						BeanUtilsWrapper.copyProperties(dictionaryKeysResponseDto, dictionary);
						dictionaryKeysResponseDto.setDictionaryId(dictionary.getId());
						// 结果集根据seq排序、null 的排在数字后
						if (dictionary.getSeq() == null) {
							tempList.add(dictionaryKeysResponseDto);
						} else {
							rsList.add(dictionaryKeysResponseDto);
						}
					}
					
					rsList.addAll(tempList);
				}
				
			}
			//3.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());

			Map<String,Object> respMap = new HashMap<String, Object>();
			respMap.put("list", rsList);
			// 设置分页信息
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());
			
			if(totalCount > 0 && req.getPageSize() !=null ){
				respMap.put("totalPage", new PageBean().countPageCount(totalCount, req.getPageSize()));
			}
			
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		}
		
		log.info("queryKeysByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}

	@Override
	public BaseWebResponse add(DictionaryRequest req) {
		log.info("add.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			// 根据code查询数据字典
			Dictionary opDictionary = new Dictionary();
			opDictionary.setCode(req.getCode());
			opDictionary.setDeleteFlag(DeleteFlag.EXISTS.code());
			List<Dictionary> checkList = dictionaryService.queryList(opDictionary);
			OperationHistory history=new OperationHistory();
			
			// key为空则是新增数据字典大类  反之新增字典值
			if (StringUtils.isBlank(req.getKey())) {
				// 字典code存在则发出提示信息
				if (checkList != null && !checkList.isEmpty()) {
					resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.DICTIONARY_CODE_IS_EXIST.code(),ResultCode.DICTIONARY_CODE_IS_EXIST.desc());
					return resp;
				}
				
				// 封装新增对象
				BeanUtilsWrapper.copyProperties(opDictionary, req);
				opDictionary.setKey(""); // key 不可以为null，则初始化为"", 代表为字典数据大类
				dictionaryService.insert(opDictionary);
				
				history.setContent("字典管理->新增字典大类");
			} else {
				// 字典code不存在
				if (checkList == null || checkList.isEmpty()) {
					resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.DICTIONARY_CODE_NOT_EXIST.code(),ResultCode.DICTIONARY_CODE_NOT_EXIST.desc());
					return resp;
				}
				
				for (Dictionary tempdDictionary : checkList) {
					// key存在则发出提示信息
					if (req.getKey().equals(tempdDictionary.getKey())){
						resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.DICTIONARY_KEY_IS_EXIST.code(),ResultCode.DICTIONARY_KEY_IS_EXIST.desc());
						return resp;
					}
				}
				
				// 封装新增对象
				BeanUtilsWrapper.copyProperties(opDictionary, req);
				Dictionary tempDictionary = checkList.get(0);
				opDictionary.setName(tempDictionary.getName());
				opDictionary.setRemark(tempDictionary.getRemark());
				/*// 若用户不自定义设置seq 则默认设置seq
				if (opDictionary.getSeq() == null) {
					opDictionary.setSeq(checkList.size()+1); 
				}*/
				
				dictionaryService.insert(opDictionary);
				
				history.setContent("字典管理->新增字典列表值");
			}
			
			//日志插入
			history.setUserId(req.getUserId());
			history.setOperationType("add");
			history.setCreateBy(req.getCreateBy());
			operationHistoryService.insertOperationHistory(history);
			
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("add.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}

	@Override
	public BaseWebResponse modify(DictionaryRequest req) {
		log.info("modify.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			// 检查 数据字典是否存在
			Dictionary checkOpDictionary = dictionaryService.selectByPrimaryKey(req.getDictionaryId());
			// 判断是否存在， 不存在则发出提示信息  
			if (DeleteFlag.DELETED.code() == checkOpDictionary.getDeleteFlag()) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.RECORD_IS_NOT_EXIST.code(),ResultCode.RECORD_IS_NOT_EXIST.desc());
				return resp;
			}
			
			OperationHistory history=new OperationHistory();
			Dictionary opDictionary = new Dictionary();
			opDictionary.setModifyBy(req.getModifyBy());
			//修改字典大类
			if (StringUtils.isBlank(checkOpDictionary.getKey())) {
				opDictionary.setCode(checkOpDictionary.getCode());
				opDictionary.setName(req.getName());
				opDictionary.setRemark(req.getRemark());
				dictionaryService.update(opDictionary);
				
				history.setContent("字典管理->修改字典大类");
			} else {
				checkOpDictionary = new Dictionary();
				checkOpDictionary.setCode(req.getCode());
				checkOpDictionary.setKey(req.getKey());
				checkOpDictionary.setDeleteFlag(DeleteFlag.EXISTS.code());
				checkOpDictionary = dictionaryService.queryOpDictionary(checkOpDictionary);
				// key是重复的
				if (checkOpDictionary != null 
						&& checkOpDictionary.getId() != null
						&& req.getDictionaryId() != null
						&& checkOpDictionary.getId().intValue() != req.getDictionaryId().intValue()) {
					resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.DICTIONARY_KEY_IS_EXIST.code(),ResultCode.DICTIONARY_KEY_IS_EXIST.desc());
					return resp;
				}
				
				BeanUtilsWrapper.copyProperties(opDictionary, req);
				opDictionary.setId(req.getDictionaryId());
				opDictionary.setCode(null);
				opDictionary.setName(null);
				opDictionary.setRemark(null);
				dictionaryService.updateByPrimaryKeySelective(opDictionary);
				
				history.setContent("字典管理->修改字典列表值");
			}
			
			//日志插入
			history.setUserId(req.getUserId());
			history.setOperationType("modify");
			history.setCreateBy(req.getModifyBy());
			operationHistoryService.insertOperationHistory(history);
			
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("add.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}

	@Override
	public BaseWebResponse delete(DictionaryRequest req) {
		log.info("delete.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			// 检查 数据字典code是否存在
			Dictionary opDictionary = new Dictionary();
			opDictionary.setCode(req.getCode());
			opDictionary.setKey(req.getKey());
			opDictionary.setDeleteFlag(DeleteFlag.EXISTS.code());
			List<Dictionary> checkList = dictionaryService.queryList(opDictionary);
			// 不存在则发出提示信息
			if (checkList == null || checkList.isEmpty()) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.RECORD_IS_NOT_EXIST.code(),ResultCode.RECORD_IS_NOT_EXIST.desc());
				return resp;
			}
			
			for(Dictionary tempDictionary:checkList) {
				// 检查数据字典列表值是否已启用
				if (tempDictionary.getStatus() != null && tempDictionary.getStatus() == DictionaryStatus.ENABLED.code()) {
					resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.DICTIONARY_KEY_IS_ENABLED.code(),ResultCode.DICTIONARY_KEY_IS_ENABLED.desc());
					return resp;
				}
			}
			
			// 更新删除状态
			opDictionary.setDeleteFlag(DeleteFlag.DELETED.code());
			opDictionary.setModifyBy(req.getModifyBy());
			dictionaryService.update(opDictionary);
			
			//日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("delete");
			history.setCreateBy(req.getModifyBy());
			//修改字典大类
			if (StringUtils.isBlank(req.getKey())) {
				history.setContent("字典管理->删除字典大类");
			} else {
				history.setContent("字典管理->删除字典列表值");
			}
			
			operationHistoryService.insertOperationHistory(history);
			
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("add.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}
	
	@Override
	public BaseWebResponse queryKeysByCodes(List<String> codes) {
		log.info("queryKeysByCodes.req:" + JSON.toJSONString(codes));
		
		BaseWebResponse resp;
		
		try {
			// 1. 执行查询
			List<Dictionary> opDictionaries = dictionaryService.selectDictionaryKeysByCodes(codes);
			// 转换结果集
			HashMap<String, List<DictionaryKeysResponseDto>> respMap = new HashMap<String, List<DictionaryKeysResponseDto>>();
			if (opDictionaries != null && opDictionaries.size() > 0) {
				for (int i = 0; i < codes.size(); i++) {
					String tempCode = codes.get(i);
					List<DictionaryKeysResponseDto> templist = new ArrayList<DictionaryKeysResponseDto>();
					for (Iterator iterator = opDictionaries.iterator(); iterator.hasNext();) {
						Dictionary dictionary = (Dictionary) iterator.next();
						DictionaryKeysResponseDto dictionaryKeysResponseDto = new DictionaryKeysResponseDto();
						BeanUtilsWrapper.copyProperties(dictionaryKeysResponseDto, dictionary);
						dictionaryKeysResponseDto.setDictionaryId(dictionary.getId());
						
						if (tempCode.equals(dictionary.getCode())) {
							templist.add(dictionaryKeysResponseDto);
						}
					}
					respMap.put(tempCode, templist);
				}
			}
			
			//2.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());

			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		}
		
		log.info("queryKeysByCodes.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}

}
