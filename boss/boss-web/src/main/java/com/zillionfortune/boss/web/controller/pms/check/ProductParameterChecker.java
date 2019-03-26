/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.check;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.zillionfortune.boss.biz.pms.dto.ProductLadderRegisterModel;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.pms.vo.ApproveProductVo;
import com.zillionfortune.boss.web.controller.pms.vo.ProductRegisterRequestVo;
import com.zillionfortune.boss.web.controller.pms.vo.PutOrOutProductOffLineRequestVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductApprovalListVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductDetailVo;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductListVo;
import com.zillionfortune.boss.web.controller.pms.vo.UpdateProductCollectAmountRequestVo;

/**
 * ClassName: ProductParameterChecker <br/>
 * Function: 产品管理请求参数校验. <br/>
 * Date: 2017年5月9日 下午1:58:26 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Component
public class ProductParameterChecker {
	
	private final static String INTERNAL_PATTERN_CODE="02";

	/**
	 * checkProductRegisterRequest:产品注册相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkProductRegisterRequest(ProductRegisterRequestVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}

		
		
		if(INTERNAL_PATTERN_CODE.equals(req.getPatternCode())){
			return;
		}
		
		// 阶梯收益列表
		List<ProductLadderRegisterModel> productLadderRegisterModelList = req.getProductLadderList();
		if (productLadderRegisterModelList == null || productLadderRegisterModelList.size() == 0) {
			throw new BusinessException("阶梯收益列表不能为空");
		} else {
			for (ProductLadderRegisterModel model : productLadderRegisterModelList) {
				// 当期循环轮次
				if (StringUtils.isNotBlank(model.getInvestPeriodLoopIndex())) {
					try {
						Integer.valueOf(model.getInvestPeriodLoopIndex());
					} catch (Exception e) {
						throw new BusinessException("当前循环轮次必须为整数");
					}
				}
				
				// 实际收益率
				if (StringUtils.isNotBlank(model.getYieldRate())) {
					try {
						new BigDecimal(model.getYieldRate());
					} catch (Exception e) {
						throw new BusinessException("实际收益率必须为数字");
					}
				}
				
				// 手续费
				if (StringUtils.isNotBlank(model.getPoundage())) {
					try {
						new BigDecimal(model.getPoundage());
					} catch (Exception e) {
						throw new BusinessException("手续费必须为数字");
					}
				}
			}
		}
	}

	/**
	 * checkQueryProductListRequest:产品列表查询相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkQueryProductListRequest(QueryProductListVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}

		// 产品线Id
		if (StringUtils.isNotBlank(req.getProductLineId())) {
			try {
				Long.valueOf(req.getProductLineId());
			} catch (Exception e) {
				throw new BusinessException("产品线Id必须为整数");
			}
		}

		// 销售状态
		if (StringUtils.isNotBlank(req.getSaleStatus())) {
			try {
				Integer.valueOf(req.getSaleStatus());
			} catch (Exception e) {
				throw new BusinessException("销售状态必须为整数");
			}
		}

		// 募集状态
		if (StringUtils.isNotBlank(req.getCollectStatus())) {
			try {
				Integer.valueOf(req.getCollectStatus());
			} catch (Exception e) {
				throw new BusinessException("募集状态必须为整数");
			}
		}

		// 募集总规模
		if (StringUtils.isNotBlank(req.getTotalAmount())) {
			try {
				new BigDecimal(req.getTotalAmount());
			} catch (Exception e) {
				throw new BusinessException("募集总规模必须为数字");
			}
		}

		// 显示状态
		if (StringUtils.isNotBlank(req.getDisplayStatus())) {
			try {
				Integer.valueOf(req.getDisplayStatus());
			} catch (Exception e) {
				throw new BusinessException("显示状态必须为整数");
			}
		}

		// 产品审核状态
		if (StringUtils.isNotBlank(req.getApprovalStatus())) {
			try {
				Integer.valueOf(req.getApprovalStatus());
			} catch (Exception e) {
				throw new BusinessException("产品审核状态必须为整数");
			}
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 查询创建开始时间
		if (StringUtils.isNotBlank(req.getBeginCreateTime())) {
			try {
				format.parse(req.getBeginCreateTime());
			} catch (Exception e) {
				throw new BusinessException("查询创建开始时间必须为yyyy-MM-dd时间格式");
			}
		}
		
		// 查询创建结束时间
		if (StringUtils.isNotBlank(req.getEndCreateTime())) {
			try {
				format.parse(req.getEndCreateTime());
			} catch (Exception e) {
				throw new BusinessException("查询创建结束时间必须为yyyy-MM-dd时间格式");
			}
		}
		
		// 审核开始时间
		if (StringUtils.isNotBlank(req.getApprovalStartTime())) {
			try {
				format.parse(req.getApprovalStartTime());
			} catch (Exception e) {
				throw new BusinessException("审核开始时间必须为yyyy-MM-dd时间格式");
			}
		}
		
		// 审核结束时间
		if (StringUtils.isNotBlank(req.getApprovalEndTime())) {
			try {
				format.parse(req.getApprovalEndTime());
			} catch (Exception e) {
				throw new BusinessException("审核结束时间必须为yyyy-MM-dd时间格式");
			}
		}
	}
	
	/**
	 * checkQueryProductDetailRequest:产品详情查询相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkQueryProductDetailRequest(QueryProductDetailVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}
		
		if (StringUtils.isBlank(req.getProductCode())) {
			throw new BusinessException("产品编号不能为空");
		}
	}
	
	/**
	 * checkPutOrOutProductOffLineRequest:产品上线/下线相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkPutOrOutProductOffLineRequest(PutOrOutProductOffLineRequestVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}
		
		if (StringUtils.isBlank(req.getProductCode())) {
			throw new BusinessException("产品编号不能为空");
		}
	}
	
	/**
	 * checkPutOrOutProductOffLineRequest:产品上线/下线相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkUpdateProductCollectAmountRequest(UpdateProductCollectAmountRequestVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}
		
		if (StringUtils.isBlank(req.getProductCode())) {
			throw new BusinessException("产品编号不能为空");
		}
		
		if (req.getCollectAmount()==null) {
			throw new BusinessException("募集金额不能为空");
		}
	}
	
	/**
	 * checkApproveProductRequest:产品审核相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkApproveProductRequest(ApproveProductVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}
		
		// 产品审核状态
		if (StringUtils.isNotBlank(req.getApprovalStatus())) {
			try {
				Integer.valueOf(req.getApprovalStatus());
			} catch (Exception e) {
				throw new BusinessException("产品审核状态必须为整数");
			}
		}
	}
	
	/**
	 * checkProductApprovalListRequest:产品审核信息列表查询相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkProductApprovalListRequest(QueryProductApprovalListVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}
		
		if (StringUtils.isBlank(req.getProductCode())) {
			throw new BusinessException("产品编号不能为空");
		}
	}
}
