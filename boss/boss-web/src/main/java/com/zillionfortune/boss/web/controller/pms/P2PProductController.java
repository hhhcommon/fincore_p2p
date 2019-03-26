/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import com.zb.fincore.pms.facade.product.dto.req.*;
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
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import com.zb.fincore.pms.facade.product.model.ProductLadderModel;
import com.zillionfortune.boss.biz.pms.P2PProductBiz;
import com.zillionfortune.boss.biz.pms.dto.ProductLadderRegisterModel;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.dal.entity.FileInfoConvert;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.pms.vo.ApproveP2PProductVo;
import com.zillionfortune.boss.web.controller.pms.vo.P2PProductRegisterRequestVo;
import com.zillionfortune.boss.web.controller.pms.vo.ProductRegisterRequestVo;
import com.zillionfortune.boss.web.controller.pms.vo.PutOrOutP2PProductOffLineRequestVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryP2PProductApprovalListVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryP2PProductListVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductDetailVo;

/**
 * ClassName: ProductServiceController <br/>
 * Function: P2P产品服务Controller. <br/>
 * Date: 2017年5月8日 下午4:29:08 <br/>
 * 
 * @author wangzinan_tech@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/p2pproductservice")
public class P2PProductController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private P2PProductBiz p2PProductBiz;

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
	public BaseWebResponse registerProduct(@RequestBody P2PProductRegisterRequestVo vo) {
        log.info("P2PProductController.registerProduct.req:" + JSON.toJSONString(vo));
        BaseWebResponse resp = null;
        RegisterProductRequest req = null;
        try {

            req = new RegisterProductRequest();
            BeanUtils.copy(vo, req);
            copyRequestForProductRegister(vo, req);
            String createBy = "";
            Integer userId = 0;
            if (httpSessionUtils.getCuruser() != null) {
                createBy = httpSessionUtils.getCuruser().getName();
                userId = httpSessionUtils.getCuruser().getId();
            }
            resp = p2PProductBiz.registerProduct(req, createBy, userId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
            }
        } finally {
            log.info("P2PProductController.registerProduct.resp:" + JSON.toJSONString(resp));
        }
        return resp;
//		log.info("P2PProductController.registerProduct.req:" + JSON.toJSONString(vo));
//		BaseWebResponse resp = null;
//		RegisterProductRequest req = null;
//		try {
//			req = new RegisterProductRequest();
//			BeanUtils.copy(vo, req);
//			copyRequestForProductRegister(vo, req);
//			String createBy = "";
//			Integer userId = 0;
//			if (httpSessionUtils.getCuruser() != null) {
//				createBy = httpSessionUtils.getCuruser().getName();
//				userId = httpSessionUtils.getCuruser().getId();
//			}
//			resp = p2PProductBiz.registerProduct(req, createBy, userId);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			if (e instanceof BusinessException) {
//				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
//			} else {
//				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
//			}
//		} finally {
//			log.info("P2PProductController.registerProduct.resp:" + JSON.toJSONString(resp));
//		}
//		return resp;
	}

	/**
	 * queryProductList:产品列表查询. <br/>
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryproductlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryProductList(@RequestBody QueryP2PProductListVo vo) {
		log.info("P2PProductController.queryProductList.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		QueryProductListRequest req = null;
		try {
			req = new QueryProductListRequest();
			BeanUtils.copy(vo, req);
			resp = p2PProductBiz.queryProductList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PProductController.queryProductList.resp:" + JSON.toJSONString(resp));
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
		log.info("P2PProductController.queryProductDetail.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = p2PProductBiz.queryProductDetail(vo.getProductCode());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PProductController.queryProductDetail.resp:" + JSON.toJSONString(resp));
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
	public BaseWebResponse putProductOnLine(@RequestBody PutOrOutP2PProductOffLineRequestVo vo) {
		log.info("P2PProductController.putProductOnLine.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		UpdateProductSaleStatusRequest req = null;
		try {
			req = new UpdateProductSaleStatusRequest();
			req.setProductCode(vo.getProductCode());
			resp = p2PProductBiz.putProductOnLine(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("P2PProductController.putProductOnLine.resp:" + JSON.toJSONString(resp));
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
	public BaseWebResponse putProductOffLine(@RequestBody PutOrOutP2PProductOffLineRequestVo vo) {
		log.info("P2PProductController.putProductOffLine.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		UpdateProductSaleStatusRequest req = null;
		try {
			req = new UpdateProductSaleStatusRequest();
			req.setProductCode(vo.getProductCode());
			resp = p2PProductBiz.putProductOffLine(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PProductController.putProductOffLine.resp:" + JSON.toJSONString(resp));
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
	public BaseWebResponse approveProduct(@RequestBody ApproveP2PProductVo vo) {
		log.info("P2PProductController.approveProduct.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		ApproveProductRequest req = null;
		try {
			req = new ApproveProductRequest();
			req.setProductCode(vo.getProductCode()); // 产品编号
			if (vo.getApprovalStatus() != null) {
				req.setApprovalStatus(Integer.valueOf(vo.getApprovalStatus())); // 审核状态
			}
			req.setApprovalSuggestion(vo.getApprovalSuggestion()); // 审核意见
			req.setApprovalBy(httpSessionUtils.getCuruser().getName()); // 审核人
			resp = p2PProductBiz.approveProduct(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PProductController.approveProduct.resp:" + JSON.toJSONString(resp));
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
	public BaseWebResponse queryProductApprovalList(@RequestBody QueryP2PProductApprovalListVo vo) {
		log.info("P2PProductController.queryProductApprovalList.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		QueryProductApprovalListRequest req = null;
		try {
			req = new QueryProductApprovalListRequest();
			req.setProductCode(vo.getProductCode()); // 产品编号
			req.setPageNo(vo.getPageNo()); // 当前页
			req.setPageSize(vo.getPageSize()); // 每页数量
			resp = p2PProductBiz.queryProductApprovalList(req, httpSessionUtils.getCuruser().getName());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PProductController.queryProductApprovalList.resp:" + JSON.toJSONString(resp));
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
		req.setLoanOrderNoSet(vo.getLoanOrderNoSet());
		req.setCollectMode(vo.getCollectMode());
		if (StringUtils.isNotBlank(vo.getCalendarMode())) {
			req.setCalendarMode(Integer.valueOf(vo.getCalendarMode())); // 日历模式
		}
		if (StringUtils.isNotBlank(vo.getSaleStatus())) {
			req.setSaleStatus(Integer.valueOf(vo.getSaleStatus())); // 销售状态
		}
		if (StringUtils.isNotBlank(vo.getCollectStatus())) {
			req.setCollectStatus(Integer.valueOf(vo.getCollectStatus())); // 募集状态
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
				req.setSaleStartTime(format.parse(vo.getSaleStartTime())); // 募集起始日
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotBlank(vo.getSaleEndTime())) {
			try {
				req.setSaleEndTime(formatTime.parse(vo.getSaleEndTime()+":00")); // 募集截止日
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotBlank(vo.getValueTime())) {
			try {
				req.setValueTime(format.parse(vo.getValueTime())); // 起息日
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotBlank(vo.getInvestPeriodLoopUnit())) {
			req.setInvestPeriodLoopUnit(Integer.valueOf(vo.getInvestPeriodLoopUnit())); // 循环周期
		}
		if (StringUtils.isNotBlank(vo.getExpectExpireTime())) {
			try {
				req.setExpectExpireTime(format.parse(vo.getExpectExpireTime())); // 到期日（预期）
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
		if (StringUtils.isNotBlank(vo.getExpectClearTime())) {
			try {
				req.setExpectClearTime(format.parse(vo.getExpectClearTime())); // 到期回款日（预计结清日）
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

        req.setOpenType(vo.getOpenType());
        req.setRegisterType(vo.getRegisterType());
        req.setBuyWays(vo.getBuyWays());
        req.setPayChannel(vo.getPayChannel());
        req.setNumberPeriod(vo.getNumberPeriod());
        req.setCeaseTime(vo.getCeaseTime());

		return;

		/*
		 * List<ProductContractModel> productContractModelList = new
		 * ArrayList<ProductContractModel>();
		 * setProductContractList(vo.getInformationDisclosure(),
		 * productContractModelList);
		 * req.setProductContractList(productContractModelList); // 信息披露
		 * 
		 * List<ProductLadderModel> productLadderList = new
		 * ArrayList<ProductLadderModel>();
		 * setProductLadderList(vo.getProductLadderList(), productLadderList);
		 * req.setProductLadderList(productLadderList); // 阶梯收益列表
		 */}

	/**
	 * setProductContractList:披露信息参数准备. <br/>
	 * 
	 * @param fileInfoList
	 * @param productContractList
	 */
	private void setProductContractList(List<FileInfoConvert> fileInfoList, List<ProductContractModel> productContractList) {
		for (FileInfoConvert req : fileInfoList) {
			ProductContractModel model = new ProductContractModel();
			model.setContractDisplayName(req.getShowName()); // 合同展示名
			model.setContractFileUrl(req.getDownloadUrl()); // 合同文件路径
			model.setContractName(req.getFileName()); // 合同文件名
			model.setContractType(StringUtils.isNotBlank(req.getHookType()) ? Integer.valueOf(req.getHookType()) : null); // 合同文件类型
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
		for (ProductLadderRegisterModel req : modelList) {
			ProductLadderModel model = new ProductLadderModel();
			model.setInvestPeriodLoopIndex(StringUtils.isNotBlank(req.getInvestPeriodLoopIndex()) ? Integer.valueOf(req.getInvestPeriodLoopIndex()) : null); // 当期循环轮次
			model.setYieldRate(StringUtils.isNotBlank(req.getYieldRate()) ? new BigDecimal(req.getYieldRate()) : null); // 当期实际收益率
			model.setPoundage(StringUtils.isNotBlank(req.getPoundage()) ? new BigDecimal(req.getPoundage()) : null); // 当期手续费
			productLadderModelList.add(model);
		}
	}

}
