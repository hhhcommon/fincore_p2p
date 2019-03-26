/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductApprovalListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectAmountRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import com.zb.fincore.pms.facade.product.model.ProductLadderModel;
import com.zillionfortune.boss.biz.pms.ProductBiz;
import com.zillionfortune.boss.biz.pms.dto.ProductLadderRegisterModel;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.ProductTypeEnum;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.dal.entity.FileInfoConvert;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.pms.check.ProductParameterChecker;
import com.zillionfortune.boss.web.controller.pms.vo.ApproveProductVo;
import com.zillionfortune.boss.web.controller.pms.vo.ProductRegisterRequestVo;
import com.zillionfortune.boss.web.controller.pms.vo.PutOrOutProductOffLineRequestVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductApprovalListVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductDetailVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductListVo;
import com.zillionfortune.boss.web.controller.pms.vo.UpdateProductCollectAmountRequestVo;

/**
 * ClassName: ProductServiceController <br/>
 * Function: 产品服务Controller. <br/>
 * Date: 2017年5月8日 下午4:29:08 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/productservice")
public class ProductController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final static String INTERNAL_PATTERN_CODE="02";

	@Autowired
	private ProductBiz productBiz;

	@Autowired
	private ProductParameterChecker parameterChecker;
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * registerProduct:产品注册. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/registerproduct", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse registerProduct(@RequestBody ProductRegisterRequestVo vo) {
		log.info("ProductController.registerProduct.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		RegisterProductRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkProductRegisterRequest(vo);

			// step2: 参数对象封装
			req = new RegisterProductRequest();
			copyRequestForProductRegister(vo, req);
			String createBy = "";
			Integer userId = 0;
			if (httpSessionUtils.getCuruser() != null) {
				createBy = httpSessionUtils.getCuruser().getName();
				userId = httpSessionUtils.getCuruser().getId();
			}

			// step3: 调用产品注册
			resp = productBiz.registerProduct(req, createBy, userId,String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.registerProduct.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * registerProduct:产品注册. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/registerinternalproduct", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse registerInternalProduct(@RequestBody ProductRegisterRequestVo vo) {
		log.info("ProductController.registerProduct.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		RegisterProductRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkProductRegisterRequest(vo);

			// step2: 参数对象封装
			req = new RegisterProductRequest();
			copyRequestForProductRegister(vo, req);
			String createBy = "";
			Integer userId = 0;
			if (httpSessionUtils.getCuruser() != null) {
				createBy = httpSessionUtils.getCuruser().getName();
				userId = httpSessionUtils.getCuruser().getId();
			}

			// step3: 调用产品注册
			resp = productBiz.registerInternalProduct(req, createBy, userId);
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.registerProduct.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * queryProductList:产品列表查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryproductlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryProductList(@RequestBody QueryProductListVo vo) {
		log.info("ProductController.queryProductList.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		QueryProductListRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkQueryProductListRequest(vo);

			// step2: 参数对象封装
			req = new QueryProductListRequest();
			copyRequestForQueryProductList(vo, req);

			// step3: 调用产品列表查询
			resp = productBiz.queryProductList(req,String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryProductList.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * queryProductList:定期产品列表查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryinternalproductlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryInternalProductList(@RequestBody QueryProductListVo vo) {
		log.info("ProductController.queryInternalProductList.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		QueryProductListRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkQueryProductListRequest(vo);

			// step2: 参数对象封装
			req = new QueryProductListRequest();
			copyRequestForQueryProductList(vo, req);

			// step3: 调用产品列表查询
			resp = productBiz.queryProductList(req,String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryInternalProductList.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * queryProductDetail:产品详情查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryproductdetail", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryProductDetail(@RequestBody QueryProductDetailVo vo) {
		log.info("ProductController.queryProductDetail.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		try {
			// step1: 参数校验
			parameterChecker.checkQueryProductDetailRequest(vo);

			// step2: 调用产品详情查询
			resp = productBiz.queryProductDetail(vo.getProductCode(),String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryProductDetail.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * queryProductDetail:定期产品详情查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryinternalproductdetail", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryInternalProductDetail(@RequestBody QueryProductDetailVo vo) {
		log.info("ProductController.queryInternalProductDetail.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		try {
			// step1: 参数校验
			parameterChecker.checkQueryProductDetailRequest(vo);

			// step2: 调用产品详情查询
			resp = productBiz.queryProductDetail(vo.getProductCode(),String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryInternalProductDetail.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * putProductOnLine:产品上线. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/putproductonline", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse putProductOnLine(@RequestBody PutOrOutProductOffLineRequestVo vo) {
		log.info("ProductController.putProductOnLine.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		UpdateProductSaleStatusRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkPutOrOutProductOffLineRequest(vo);
			
			// step2: 参数对象封装
			req = new UpdateProductSaleStatusRequest();
			req.setProductCode(vo.getProductCode());

			// step3: 调用产品上线
			resp = productBiz.putProductOnLine(req,String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.putProductOnLine.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	
	/**
	 * putProductOnLine:定期产品上线. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/putinternalproductonline", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse putInternalProductOnLine(@RequestBody PutOrOutProductOffLineRequestVo vo) {
		log.info("ProductController.putProductOnLine.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		UpdateProductSaleStatusRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkPutOrOutProductOffLineRequest(vo);
			
			// step2: 参数对象封装
			req = new UpdateProductSaleStatusRequest();
			req.setProductCode(vo.getProductCode());

			// step3: 调用产品上线
			resp = productBiz.putProductOnLine(req,String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.putProductOnLine.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * 更新产品募集金额. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/updatecollectamount", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse updateCollectAmount(@RequestBody UpdateProductCollectAmountRequestVo vo) {
		log.info("ProductController.updateCollectAmount.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		UpdateProductCollectAmountRequest req=null;
		try {
			// step1: 参数校验
			parameterChecker.checkUpdateProductCollectAmountRequest(vo);
			
			// step2: 参数对象封装
			req = new UpdateProductCollectAmountRequest();
			req.setProductCode(vo.getProductCode());
			req.setCollectAmount(vo.getCollectAmount());
			// step3: 调用更新产品募集金额接口
			resp = productBiz.updateCollectAmount(req);
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.updateCollectAmount.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * putProductOffLine:产品下线. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/putproductoffline", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse putProductOffLine(@RequestBody PutOrOutProductOffLineRequestVo vo) {
		log.info("ProductController.putProductOffLine.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		UpdateProductSaleStatusRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkPutOrOutProductOffLineRequest(vo);
			
			// step2: 参数对象封装
			req = new UpdateProductSaleStatusRequest();
			req.setProductCode(vo.getProductCode());

			// step3: 调用产品下线
			resp = productBiz.putProductOffLine(req,String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.putProductOffLine.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * putProductOffLine:定期产品下线. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/putinternalproductoffline", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse putInternalProductOffLine(@RequestBody PutOrOutProductOffLineRequestVo vo) {
		log.info("ProductController.putInternalProductOffLine.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		UpdateProductSaleStatusRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkPutOrOutProductOffLineRequest(vo);
			
			// step2: 参数对象封装
			req = new UpdateProductSaleStatusRequest();
			req.setProductCode(vo.getProductCode());

			// step3: 调用产品下线
			resp = productBiz.putProductOffLine(req,String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.putInternalProductOffLine.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * countUpProductStaus:产品状态分类统计. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/countupproductstatus", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse countUpProductStatus() {
		log.info("ProductController.countUpProductStatus.req:");

		BaseWebResponse resp = null;
		try {
			// 调用产品状态分类统计
			resp = productBiz.countUpProductStatus(String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.countUpProductStatus.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * countUpProductStaus:产品状态分类统计. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/countupinternalproductstatus", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse countUpInternalProductStatus() {
		log.info("ProductController.countUpProductStatus.req:");

		BaseWebResponse resp = null;
		try {
			// 调用产品状态分类统计
			resp = productBiz.countUpProductStatus(String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.countUpProductStatus.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * approveProduct:产品审核. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/approveproduct", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse approveProduct(@RequestBody ApproveProductVo vo) {
		log.info("ProductController.approveProduct.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		ApproveProductRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkApproveProductRequest(vo);
			
			// step2: 参数对象封装
			req = new ApproveProductRequest();
			req.setProductCode(vo.getProductCode()); // 产品编号
			if (StringUtils.isNotBlank(vo.getApprovalStatus())) {
				req.setApprovalStatus(Integer.valueOf(vo.getApprovalStatus())); // 审核状态
			}
			req.setApprovalSuggestion(vo.getApprovalSuggestion()); // 审核意见
			req.setApprovalBy(httpSessionUtils.getCuruser().getName()); // 审核人

			// step3: 调用产品审核
			resp = productBiz.approveProduct(req,String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.approveProduct.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * approveProduct:定期产品审核. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/approveinternalproduct", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse approveInternalProduct(@RequestBody ApproveProductVo vo) {
		log.info("ProductController.approveProduct.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		ApproveProductRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkApproveProductRequest(vo);
			
			// step2: 参数对象封装
			req = new ApproveProductRequest();
			req.setProductCode(vo.getProductCode()); // 产品编号
			if (StringUtils.isNotBlank(vo.getApprovalStatus())) {
				req.setApprovalStatus(Integer.valueOf(vo.getApprovalStatus())); // 审核状态
			}
			req.setApprovalSuggestion(vo.getApprovalSuggestion()); // 审核意见
			req.setApprovalBy(httpSessionUtils.getCuruser().getName()); // 审核人

			// step3: 调用产品审核
			resp = productBiz.approveProduct(req,String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.approveProduct.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * queryProductApprovalList:产品审核信息列表查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryproductapprovallist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryProductApprovalList(@RequestBody QueryProductApprovalListVo vo) {
		log.info("ProductController.queryProductApprovalList.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		QueryProductApprovalListRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkProductApprovalListRequest(vo);

			// step2: 参数对象封装
			req = new QueryProductApprovalListRequest();
			req.setProductCode(vo.getProductCode()); // 产品编号
			req.setPageNo(vo.getPageNo()); // 当前页
			req.setPageSize(vo.getPageSize()); // 每页数量

			// step3: 调用产品审核信息列表查询
			resp = productBiz.queryProductApprovalList(req, httpSessionUtils.getCuruser().getName(),String.valueOf(ProductTypeEnum.CURRENT.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryProductApprovalList.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * queryProductApprovalList:产品审核信息列表查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryinternalproductapprovallist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryInternalProductApprovalList(@RequestBody QueryProductApprovalListVo vo) {
		log.info("ProductController.queryInternalProductApprovalList.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;
		QueryProductApprovalListRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkProductApprovalListRequest(vo);

			// step2: 参数对象封装
			req = new QueryProductApprovalListRequest();
			req.setProductCode(vo.getProductCode()); // 产品编号
			req.setPageNo(vo.getPageNo()); // 当前页
			req.setPageSize(vo.getPageSize()); // 每页数量

			// step3: 调用产品审核信息列表查询
			resp = productBiz.queryProductApprovalList(req, httpSessionUtils.getCuruser().getName(),String.valueOf(ProductTypeEnum.INTERVAL.code()));
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.queryInternalProductApprovalList.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}

	/**
	 * copyRequestForProductRegister:产品注册用参数准备. <br/>
	 *
	 * @param vo
	 * @param req
	 */
	private void copyRequestForProductRegister(ProductRegisterRequestVo vo, RegisterProductRequest req) {
		// 产品基本信息
		req.setProductName(vo.getProductName()); // 产品名称
		if (StringUtils.isNotBlank(vo.getProductLineId())) {
			req.setProductLineId(Long.valueOf(vo.getProductLineId())); // 产品线Id
		}
		req.setSaleChannelCode(vo.getSaleChannelCode()); // 销售渠道
		req.setRiskLevel(vo.getRiskLevel()); // 风险等级
		req.setProductDisplayName(vo.getProductDisplayName()); // 产品展示名
		req.setPatternCode(vo.getPatternCode()); // 产品类型
		req.setJoinChannelCode(vo.getJoinChannelCode()); // 接入渠道
		if (StringUtils.isNotBlank(vo.getCalendarMode())) {
			req.setCalendarMode(Integer.valueOf(vo.getCalendarMode())); // 日历模式
		}
		if (StringUtils.isNotBlank(vo.getSaleStatus())) {
			req.setSaleStatus(Integer.valueOf(vo.getSaleStatus())); // 销售状态
		}
		if (StringUtils.isNotBlank(vo.getCollectStatus())) {
		}
		
		req.setIntroduction(vo.getIntroduction()); // 产品介绍
		
		// 产品交易信息
		if (StringUtils.isNotBlank(vo.getTotalAmount())) {
			req.setTotalAmount(new BigDecimal(vo.getTotalAmount())); // 募集总规模
		}
		if (StringUtils.isNotBlank(vo.getIncreaseAmount())) {
			req.setIncreaseAmount(new BigDecimal(vo.getIncreaseAmount())); // 步长（递增金额）
		}
		if (StringUtils.isNotBlank(vo.getAssetPoolType())) {
			req.setAssetPoolType(Integer.valueOf(vo.getAssetPoolType())); // 资产池类型
		}
		if (StringUtils.isNotBlank(vo.getMaxInvestAmount())) {
			req.setMaxInvestAmount(new BigDecimal(vo.getMaxInvestAmount())); // 个人限投
		}
		if (StringUtils.isNotBlank(vo.getMinInvestAmount())) {
			req.setMinInvestAmount(new BigDecimal(vo.getMinInvestAmount())); // 起购金额
		}
		req.setAssetPoolCode(vo.getAssetPoolCode()); // 资产池编码
		req.setAssetPoolName(vo.getAssetPoolName()); // 资产池名称
		if (StringUtils.isNotBlank(vo.getMinHoldAmount())) {
			req.setMinHoldAmount(new BigDecimal(vo.getMinHoldAmount())); // 最低可持有金额
		}
		req.setFundSettleParty(vo.getFundSettleParty()); // 资金结算方
		if (StringUtils.isNotBlank(vo.getMaxYieldRate())) {
			req.setMaxYieldRate(new BigDecimal(vo.getMaxYieldRate())); // 预期年化收益率上限
		}
		if (StringUtils.isNotBlank(vo.getMinYieldRate())) {
			req.setMinYieldRate(new BigDecimal(vo.getMinYieldRate())); // 预期年化收益率下限
		}
		
		// 产品业务信息
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isNotBlank(vo.getSaleStartTime())) {
			try {
				req.setSaleStartTime(format.parse(vo.getSaleStartTime()));  // 募集起始日
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		if (StringUtils.isNotBlank(vo.getExpectEstablishTime())) {
			try {
				req.setExpectEstablishTime(format.parse(vo.getExpectEstablishTime()));  
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		if (StringUtils.isNotBlank(vo.getSaleEndTime())) {
			try {
				req.setSaleEndTime(formatTime.parse(vo.getSaleEndTime() + " 23:59:59"));  // 募集截止日
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		if (StringUtils.isNotBlank(vo.getValueTime())) {
			try {
				req.setValueTime(format.parse(vo.getValueTime()));  // 起息日
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		if (StringUtils.isNotBlank(vo.getInvestPeriodLoopUnit())) {
			req.setInvestPeriodLoopUnit(Integer.valueOf(vo.getInvestPeriodLoopUnit())); // 循环周期
		}
		if (StringUtils.isNotBlank(vo.getExpectExpireTime())) {
			try {
				req.setExpectExpireTime(format.parse(vo.getExpectExpireTime()));  // 到期日（预期）
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		if (StringUtils.isNotBlank(vo.getExpectClearTime())) {
			try {
				req.setExpectClearTime(format.parse(vo.getExpectClearTime()));  // 到期回款日（预计结清日）
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		if (StringUtils.isNotBlank(vo.getInvestPeriod())) {
			req.setInvestPeriod(Integer.valueOf(vo.getInvestPeriod())); // 产品期限
		}
		if (StringUtils.isNotBlank(vo.getInvestPeriodUnit())) {
			req.setInvestPeriodUnit(Integer.valueOf(vo.getInvestPeriodUnit())); // 投资期限单位
		}
		if (StringUtils.isNotBlank(vo.getCurrentYieldRate())) {
			req.setCurrentYieldRate(new BigDecimal(vo.getCurrentYieldRate())); // 当期利率
		}
		if (StringUtils.isNotBlank(vo.getFloatingYieldRate())) {
			req.setFloatingYieldRate(new BigDecimal(vo.getFloatingYieldRate())); // 浮动利率
		}
		
		if(INTERNAL_PATTERN_CODE.equals(vo.getPatternCode())){
			return;
		}
		
		List<ProductContractModel> productContractModelList = new ArrayList<ProductContractModel>();
		setProductContractList(vo.getInformationDisclosure(), productContractModelList); 
		req.setProductContractList(productContractModelList); // 信息披露
		
		 List<ProductLadderModel> productLadderList = new ArrayList<ProductLadderModel>();
		 setProductLadderList(vo.getProductLadderList(), productLadderList);
		 req.setProductLadderList(productLadderList); // 阶梯收益列表
	}
	
	/**
	 * setProductContractList:披露信息参数准备. <br/>
	 *
	 * @param fileInfoList
	 * @param productContractList
	 */
	private void setProductContractList(List<FileInfoConvert> fileInfoList, List<ProductContractModel> productContractList) {
		for (FileInfoConvert req: fileInfoList) {
			ProductContractModel model = new ProductContractModel();
			model.setContractDisplayName(req.getShowName()); // 合同展示名
			model.setContractFileUrl(req.getDownloadUrl());  // 合同文件路径
			model.setContractName(req.getFileName()); // 合同文件名
			model.setContractType(StringUtils.isNotBlank(req.getHookType())? Integer.valueOf(req.getHookType()) : null); // 合同文件类型
			productContractList.add(model);
		}
	}
	
	/**
	 * setProductLadderList:阶梯收益列表参数准备. <br/>
	 *
	 * @param modelList
	 * @param productLadderModelList
	 */
	private void setProductLadderList(List<ProductLadderRegisterModel> modelList, List<ProductLadderModel> productLadderModelList) {
		for (ProductLadderRegisterModel req: modelList) {
			ProductLadderModel model = new ProductLadderModel();
			model.setInvestPeriodLoopIndex(StringUtils.isNotBlank(req.getInvestPeriodLoopIndex())? Integer.valueOf(req.getInvestPeriodLoopIndex()) : null); // 当期循环轮次
			model.setYieldRate(StringUtils.isNotBlank(req.getYieldRate())? new BigDecimal(req.getYieldRate()) : null); // 当期实际收益率
			model.setPoundage(StringUtils.isNotBlank(req.getPoundage())? new BigDecimal(req.getPoundage()) : null); // 当期手续费
			productLadderModelList.add(model);
		}
	}
	
	/**
	 * copyRequestForQueryProductList:产品列表查询用参数准备. <br/>
	 *
	 * @param vo
	 * @param req
	 */
	private void copyRequestForQueryProductList(QueryProductListVo vo, QueryProductListRequest req) {
		req.setProductCode(vo.getProductCode()); // 产品编码
		req.setProductName(vo.getProductName()); // 产品名称
		req.setProductDisplayName(vo.getProductDisplayName()); // 产品展示名
		if (StringUtils.isNotBlank(vo.getProductLineId())) {
			req.setProductLineId(Long.valueOf(vo.getProductLineId())); // 产品线Id
		}
		req.setProductLineCode(vo.getProductLineCode()); // 产品线编码
		req.setAssetPoolCode(vo.getAssetPoolCode()); // 资产池编码
		req.setPatternCode(vo.getPatternCode()); // 产品类型
		req.setSaleChannelCode(vo.getSaleChannelCode()); // 销售渠道
		req.setJoinChannelCode(vo.getJoinChannelCode()); // 接入渠道
		if (StringUtils.isNotBlank(vo.getTotalAmount())) {
			req.setTotalAmount(new BigDecimal(vo.getTotalAmount())); // 募集总规模
		}
		if (StringUtils.isNotBlank(vo.getSaleStatus())) {
			req.setSaleStatus(Integer.valueOf(vo.getSaleStatus())); // 销售状态
		}
		if (StringUtils.isNotBlank(vo.getCollectStatus())) {
			req.setCollectStatus(Integer.valueOf(vo.getCollectStatus())); // 募集状态
		}
		if (StringUtils.isNotBlank(vo.getDisplayStatus())) {
			req.setDisplayStatus(Integer.valueOf(vo.getDisplayStatus())); // 显示状态
		}
		req.setRiskLevel(vo.getRiskLevel()); // 风险等级
		if (StringUtils.isNotBlank(vo.getDisplayStatus())) {
			req.setApprovalStatus(Integer.valueOf(vo.getApprovalStatus())); // 产品审核状态
		}
		req.setBeginCreateTime(vo.getBeginCreateTime()); // 查询创建开始时间(yyyy-mm-dd)
		req.setEndCreateTime(vo.getEndCreateTime());     // 查询创建结束时间(yyyy-mm-dd)
		req.setApprovalStartTime(vo.getApprovalStartTime()); // 审核开始时间(yyyy-mm-dd)
		req.setApprovalEndTime(vo.getApprovalEndTime());     // 审核结束时间(yyyy-mm-dd)
		req.setPageNo(vo.getPageNo()); // 当前页
		req.setPageSize(vo.getPageSize()); // 每页数量
	}

}
