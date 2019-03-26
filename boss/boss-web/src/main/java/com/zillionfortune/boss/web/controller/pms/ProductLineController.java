/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.pms.facade.line.dto.req.QueryProductLineListRequest;
import com.zillionfortune.boss.biz.pms.ProductLineBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.ProductTypeEnum;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.pms.check.ProductLineParameterChecker;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductLineListVO;

/**
 * ClassName: ProductLineController <br/>
 * Function: 产品线服务Controller. <br/>
 * Date: 2017年5月31日 上午11:00:49 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/productlineservice")
public class ProductLineController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductLineBiz productLineBiz;
	
	@Autowired
	private ProductLineParameterChecker parameterChecker;

	/**
	 * queryProductLineList:产品线列表查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryproductlinelist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryProductLineList(@RequestBody QueryProductLineListVO vo) {
		log.info("ProductController.queryProductLineList.req:");

		BaseWebResponse resp = null;
		QueryProductLineListRequest req = null;
		try {
			parameterChecker.checkQueryProductLineListRequest(vo);
			
			req = new QueryProductLineListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			resp = productLineBiz.queryProductLineList(req,String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryProductLineList.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * queryProductLineList:产品线列表查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryinternalproductlinelist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryInternalProductLineList(@RequestBody QueryProductLineListVO vo) {
		log.info("ProductController.queryInternalProductLineList.req:");

		BaseWebResponse resp = null;
		QueryProductLineListRequest req = null;
		try {
			parameterChecker.checkQueryProductLineListRequest(vo);
			
			req = new QueryProductLineListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			resp = productLineBiz.queryProductLineList(req,String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryProductLineList.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}

}
