package com.zb.fincore.pms.service.product.validate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.enums.product.ProductRiskLevelEnum;
import com.zb.fincore.common.enums.product.ProductSaleStatusEnum;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.DecimalUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.common.enums.ProductCollectStatusEnum;
import com.zb.fincore.pms.common.enums.ProductJoinChannelEnum;
import com.zb.fincore.pms.common.enums.ProductLockPeriodUnitEnum;
import com.zb.fincore.pms.common.enums.RegisterTypeEnum;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductBaseInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductContractInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductLadderInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductPeriodInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductProfitInfoRequest;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import com.zb.fincore.pms.facade.product.model.ProductLadderModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.service.dal.dao.ProductDao;
import com.zb.fincore.pms.service.dal.dao.ProductLineDao;
import com.zb.fincore.pms.service.dal.model.Product;
import com.zb.fincore.pms.service.dal.model.ProductLine;

/**
 * 功能: 产品数据库服务参数校验器
 * 日期: 2017/4/10 0030 19:25
 * 版本: V1.0
 */
@Component
public class ProductDbServiceParameterValidator {

    /**
     * 产品线数据访问对象
     */
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductLineDao productLineDao;

    /**
     * 检测产品上线请求参数
     *
     * @param product
     * @return
     */
    public BaseResponse checkPutProductOnLineRequestParameter(Product product) {
        //产品募集状态 为"待募集 或 募集期" 销售状态为 "已布署 或 下线" 审核状态 为审核通过 才允许执行上线操作
        if (ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_COLLECT.getCode() != product.getCollectStatus()
                && ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode() != product.getCollectStatus()) {
            return BaseResponse.build(Constants.PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE,
                    Constants.PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE_DESC);
        }

        if (ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode() != product.getSaleStatus()
                && ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode() != product.getSaleStatus()) {
            return BaseResponse.build(Constants.PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE,
                    Constants.PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE_DESC);
        }

        if (ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode() != product.getApprovalStatus()) {
            return BaseResponse.build(Constants.PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE,
                    Constants.PRODUCT_ON_LINE_NOT_IN_CORRECT_STATUS_CODE_DESC);
        }
        return null;
    }

    /**
     * 检测产品注册请求参数 基本信息 逻辑
     *
     * @param req 产品注册请求对象
     * @return 没有逻辑错误返回null
     */
    public RegisterProductResponse checkRegisterProductBaseInfoParameter(RegisterProductBaseInfoRequest req) {
        if (ProductRiskLevelEnum.getEnumItem(req.getRiskLevel()) == null) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_RISK_LEVEL_NOT_IN_ENUMS_RETURN_CODE,
                    Constants.PRODUCT_RISK_LEVEL_NOT_IN_ENUMS_RETURN_DESC);
        }

        if (PatternCodeTypeEnum.getEnumItem(req.getPatternCode()) == null) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_PATTERN_CODE_NOT_IN_ENUMS_RETURN_CODE,
                    Constants.PRODUCT_PATTERN_CODE_NOT_IN_ENUMS_RETURN_DESC);
        }

        if (!ChannelEnum.validateChannel(req.getSaleChannelCode())) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_CHANNEL_CODE_NOT_IN_ENUMS_RESULT_CODE,
                    Constants.PRODUCT_CHANNEL_CODE_NOT_IN_ENUMS_RESULT_DESC);
        }
//        if (req.getPatternCode().equals(PatternCodeTypeEnum.PERIODIC_REGULAR.getCode())) {
//        	if (ProductJoinChannelEnum.getEnumItem(req.getJoinChannelCode()) == null) {
//                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_JOIN_CHANNEL_CODE_NOT_IN_ENUMS_CODE,
//                        Constants.PRODUCT_JOIN_CHANNEL_CODE_NOT_IN_ENUMS_CODE_DESC);
//            }
//        }

        // p2p 定期类产品所需产品线信息 V2.0
        if (req.getPatternCode().equals(PatternCodeTypeEnum.N_LOOP_PLAN.getCode())) {
        	ProductLine productLine = productLineDao.selectByPrimaryKey(req.getProductLineId());
            if(null ==  productLine){
                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_LINE_UN_EXIST_RETURN_CODE, Constants.PRODUCT_LINE_UN_EXIST_RETURN_DESC);
            }
            req.setProductLineCode(productLine.getLineCode());
            req.setProductLineName(productLine.getLineDisplayName());
        }

//        Map<String, Object> params = new HashMap<String, Object>();
//        List<Integer> approvalStatusList = new ArrayList<Integer>();
//        params.put("productName", req.getProductName());
//        params.put("productDisplayName", req.getProductDisplayName());
//        approvalStatusList.add(ProductApprovalStatusEnum.WAIT_APPROVAL.getCode());
//        approvalStatusList.add(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
//        params.put("approvalStatusList", approvalStatusList);
//        int count = productDao.selectProductCountByNamesMap(params);
//        if (count > 0) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_EXIST_RESULT_CODE, Constants.PRODUCT_EXIST_RESULT_CODE_DESC);
//        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        List<Integer> approvalStatusList = new ArrayList<Integer>();
        params.put("numberPeriod", req.getNumberPeriod());
        approvalStatusList.add(ProductApprovalStatusEnum.WAIT_APPROVAL.getCode());
        approvalStatusList.add(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        params.put("approvalStatusList", approvalStatusList);
        int count = productDao.selectProductCountByNumberPeriodMap(params);
        if (count > 0) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_NUMBERPERIOD_EXIST_RESULT_CODE, Constants.PRODUCT_NUMBERPERIOD_EXIST_RESULT_CODE_DESC);
        }
        return null;
    }

    /**
     * 检测产品注册请求参数 期限信息 逻辑
     *
     * @param req 产品注册请求对象
     * @return 没有逻辑错误返回null
     */
    public RegisterProductResponse checkRegisterProductPeriodInfoParameter(RegisterProductPeriodInfoRequest req, String patternCode) {
    	
    	//募集起始日<=募集截止日
        if (DateUtils.isAfter(req.getSaleStartTime(), req.getSaleEndTime())) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE,
                    Constants.PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE_DESC);
        }
        
        // 定期 V2.0
    	if (patternCode.equals(PatternCodeTypeEnum.PERIODIC_REGULAR.getCode())) {
            //募集截止时间<=起息日
            String saleEndTimeStr = DateUtils.format(req.getSaleEndTime(), "yyyy-MM-dd HH:mm");
            Date saleEndTime = DateUtils.parse(saleEndTimeStr, DateUtils.DEFAULT_DATA_FORMAT);
            if(DateUtils.isAfter(saleEndTime, req.getValueTime())){
                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_END_DATE_LE_VALUE_DATE_CODE,
                        Constants.PRODUCT_SALE_END_DATE_LE_VALUE_DATE_CODE_DESC);
            }

//            //起息日<到期日
//            if (DateUtils.isAfterOrEqual(req.getValueTime(), req.getExpectExpireTime())) {
//                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE,
//                        Constants.PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE_DESC);
//            }
    //
//            //到期日<=回款日
//            if (DateUtils.isAfter(req.getExpectExpireTime(), req.getExpectClearTime())) {
//                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_EXPIRE_DATE_LE_EXPECT_CLEAR_PAYMENT_DATE_CODE,
//                        Constants.PRODUCT_EXPIRE_DATE_LE_EXPECT_CLEAR_PAYMENT_DATE_CODE_DESC);
//            }
            
          //起息日<回款日
          if (DateUtils.isAfterOrEqual(req.getValueTime(), req.getExpectClearTime())) {
              return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_VALUE_DATE_AFTER_EXPECT_CLEAR_DATE_CODE,
                      Constants.PRODUCT_VALUE_DATE_AFTER_EXPECT_CLEAR_DATE_CODE_DESC);
          }
    	}
//    	//产品预期上线时间不能晚于募集截止时间
//        if (DateUtils.isAfter(req.getExpectOnlineTime(), req.getSaleEndTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_EXPECT_ONLINE_TIME_AFTER_SALE_END_DATE_CODE,
//                    Constants.PRODUCT_EXPECT_ONLINE_TIME_AFTER_SALE_END_DATE_CODE_DESC);
//        }
//
//    	//募集起始日<=募集截止日
//        if (DateUtils.isAfter(req.getSaleStartTime(), req.getSaleEndTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE,
//                    Constants.PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE_DESC);
//        }
//
//        //募集结束日 不能晚于当前日期
//        String curDateStr = DateUtils.format(new Date(), DateUtils.DEFAULT_DATA_FORMAT);
//        Date curDate = DateUtils.parse(curDateStr, DateUtils.DEFAULT_DATA_FORMAT);
//        if (DateUtils.isAfter(curDate, req.getSaleEndTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_END_TIME_AFTER_CUR_TIME_RESULT_CODE,
//                    Constants.PRODUCT_SALE_END_TIME_AFTER_CUR_TIME_RESULT_CODE_DESC);
//        }
//
//        if (DateUtils.isAfter(req.getSaleEndTime(), req.getExpectEstablishTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_END_DATE_AFTER_ESTABLISH_DATE_CODE,
//                    Constants.PRODUCT_SALE_END_DATE_AFTER_ESTABLISH_DATE_CODE_DESC);
//        }
//
//        if(DateUtils.isAfter(req.getExpectEstablishTime(), req.getValueTime())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_ESTABLISH_DATE_AFTER_VALUE_DATE_CODE,
//                    Constants.PRODUCT_ESTABLISH_DATE_AFTER_VALUE_DATE_CODE_DESC);
//        }
//
//        if(DateUtils.isAfterOrEqual(req.getSaleEndTime(), req.getValueTime())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_END_DATE_BEFORE_VALUE_DATE_CODE,
//                    Constants.PRODUCT_SALE_END_DATE_BEFORE_VALUE_DATE_CODE_DESC);
//        }
//
//        //起息日<到期日
//        if (DateUtils.isAfterOrEqual(req.getValueTime(), req.getExpectExpireTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE,
//                    Constants.PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE_DESC);
//        }
//
//        if (DateUtils.isAfterOrEqual(req.getExpectExpireTime(), req.getExpectClearTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_EXPIRE_DATE_AFTER_EXPECT_CLEAR_PAYMENT_DATE_CODE,
//                    Constants.PRODUCT_EXPIRE_DATE_AFTER_EXPECT_CLEAR_PAYMENT_DATE_CODE_DESC);
//        }
//
//        //如果是阶梯收益产品，
//        if (PatternCodeTypeEnum.LADDER.getCode().equals(patternCode)) {
//            if(null == req.getInvestPeriodLoopUnit() || req.getInvestPeriodLoopUnit().intValue() <=0){
//                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_LADDER_INVEST_PERIOD_LOOP_UNIT_EMPTY_CODE,
//                        Constants.PRODUCT_LADDER_INVEST_PERIOD_LOOP_UNIT_EMPTY_CODE_DESC);
//            }
//        }
        return null;
    }
    
    /**
     * 检测更新产品募集金额
     *
     * @param req 产品注册请求对象
     * @return 没有逻辑错误返回null
     */
    public BaseResponse checkUpdateCollectAmountParameter(BigDecimal collectAmount,BigDecimal minInvestAmount) {
    	if(collectAmount==null||collectAmount.compareTo(BigDecimal.ZERO)<1){
    		 return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_TOTAL_AMOUNT_NOT_NULL_RESULT_CODE,
                     Constants.PRODUCT_TOTAL_AMOUNT_NOT_NULL_RESULT_CODE_DESC);
    	}
        if(DecimalUtils.gt(minInvestAmount, collectAmount)){
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_TOTAL_AMOUNT_GT_MIN_INVEST_AMOUNT_RESULT_CODE,
                    Constants.PRODUCT_TOTAL_AMOUNT_GT_MIN_INVEST_AMOUNT_RESULT_CODE_DESC);
        }

        return null;
    }

    /**
     * 检测产品注册请求参数 投资收益信息 逻辑
     *
     * @param req 产品注册请求对象
     * @return 没有逻辑错误返回null
     */
    public RegisterProductResponse checkRegisterProductProfitInfoParameter(RegisterProductProfitInfoRequest req, String registerType) {
    	//人工注册 
    	if (registerType.equals(RegisterTypeEnum.UNAUTO.getCode())) {
        	if(DecimalUtils.gt(req.getMinInvestAmount(), req.getTotalAmount())){
                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
                        Constants.PRODUCT_MIN_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
            }

            if(DecimalUtils.gt(req.getIncreaseAmount(), req.getTotalAmount())){
                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_INCREASE_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
                        Constants.PRODUCT_INCREASE_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
            }

            if(DecimalUtils.gt(req.getMaxInvestAmount(), req.getTotalAmount())){
                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
                        Constants.PRODUCT_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
            }
    	}
    	if(DecimalUtils.gt(req.getMinInvestAmount(), req.getMaxInvestAmount())){
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE,
                    Constants.PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE_DESC);
        }

//        if (ProductUnitEnum.getEnumItem(req.getUnit()) == null) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_PRODUCT_UNIT_CODE,
//                    Constants.PRODUCT_PRODUCT_UNIT_CODE_DESC);
//        }
//
//        if (ProductBasicInterestsPeriodEnum.getEnumItem(req.getBasicInterestsPeriod()) == null) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_BASIC_INTERESTS_PERIOD_CODE,
//                    Constants.PRODUCT_BASIC_INTERESTS_PERIOD_CODE_DESC);
//        }
//
//        if (ProductProfitTypeEnum.getEnumItem(req.getProfitType()) == null) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_PROFIT_TYPE_CODE,
//                    Constants.PRODUCT_PROFIT_TYPE_CODE_DESC);
//        }
//
//        if (ProductInvestTypeEnum.getEnumItem(req.getCalculateInvestType()) == null) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_CALCULATE_INVEST_TYPE_CODE,
//                    Constants.PRODUCT_CALCULATE_INVEST_TYPE_CODE_DESC);
//        }
//
//
//        if(DecimalUtils.gt(req.getMinInvestAmount(), req.getTotalAmount())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
//                    Constants.PRODUCT_MIN_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
//        }
//
//        if(DecimalUtils.gt(null == req.getSingleMaxInvestAmount() ? BigDecimal.ZERO : req.getSingleMaxInvestAmount(), req.getTotalAmount())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
//                    Constants.PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
//        }
//
//        if(DecimalUtils.gt(req.getIncreaseAmount(), req.getTotalAmount())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_INCREASE_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
//                    Constants.PRODUCT_INCREASE_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
//        }
//
//        if(null == req.getMinHoldAmount() || DecimalUtils.lt(req.getMinHoldAmount(), BigDecimal.ZERO)){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_HOLD_AMOUNT_GE_ZERO,
//                    Constants.PRODUCT_MIN_HOLD_AMOUNT_GE_ZERO_RESULT_CODE_DESC);
//        }
//
//        if(DecimalUtils.gt(req.getMinHoldAmount(), req.getTotalAmount())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_HOLD_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
//                    Constants.PRODUCT_MIN_HOLD_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
//        }
//
//        if(DecimalUtils.gt(req.getMaxInvestAmount(), req.getTotalAmount())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE,
//                    Constants.PRODUCT_MAX_INVEST_AMOUNT_GT_TOTAL_AMOUNT_RESULT_CODE_DESC);
//        }
//
//        if(DecimalUtils.gt(req.getMinYieldRate(), req.getMaxYieldRate())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_YIELD_RATE_ABOVE_MAX_YIELD_RATE_RESULT_CODE,
//                    Constants.PRODUCT_MIN_YIELD_RATE_ABOVE_MAX_YIELD_RATE_RESULT_CODE_DESC);
//        }
//
//        if(DecimalUtils.ge(null == req.getSingleMaxInvestAmount() ? BigDecimal.ZERO : req.getSingleMaxInvestAmount(), req.getMaxInvestAmount())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE,
//                    Constants.PRODUCT_SINGLE_MAX_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE_DESC);
//        }
//        if(DecimalUtils.gt(req.getMinInvestAmount(), req.getMaxInvestAmount())){
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE,
//                    Constants.PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE_DESC);
//        }
        return null;
    }

//    /**
//     * 检测产品注册请求参数 产品合同信息 逻辑
//     *
//     * @param req 产品注册请求对象
//     * @return 没有逻辑错误返回null
//     */
//    public RegisterProductResponse checkRegisterProductContractInfoParameter(RegisterProductContractInfoRequest req, Product product) {
//
//        if (CollectionUtils.isNullOrEmpty(req.getProductContractList())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_CONTRACT_LIST_INFO_EMPTY_CODE,
//                    Constants.PRODUCT_CONTRACT_LIST_INFO_EMPTY_CODE_DESC);
//        }
//
//        for(ProductContractModel productContractModel: req.getProductContractList()){
//            if((null == productContractModel.getContractType() || productContractModel.getContractType() <=0)
//                    || StringUtils.isBlank(productContractModel.getContractName())
//                    || StringUtils.isBlank(productContractModel.getContractDisplayName())
//                    || StringUtils.isBlank(productContractModel.getContractFileUrl())){
//                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_CONTRACT_INFO_UN_COMPLETELY_CODE,
//                        Constants.PRODUCT_CONTRACT_INFO_UN_COMPLETELY_CODE_DESC);
//            }
//        }
//
//        return null;
//    }

//    /**
//     * 检测产品注册请求参数 产品阶梯信息 逻辑
//     *
//     * @param req 产品注册请求对象
//     * @return 没有逻辑错误返回null
//     */
//    public RegisterProductResponse checkRegisterProductLadderInfoParameter(RegisterProductLadderInfoRequest req) {
//
//        if (CollectionUtils.isNullOrEmpty(req.getProductLadderList())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_LADDER_LIST_INFO_EMPTY_CODE,
//                    Constants.PRODUCT_LADDER_LIST_INFO_EMPTY_CODE_DESC);
//        }
//
//        for(ProductLadderModel productLadderModel: req.getProductLadderList()){
//            if((null == productLadderModel.getInvestPeriodLoopIndex() || productLadderModel.getInvestPeriodLoopIndex() <=0)
//                    || null == productLadderModel.getYieldRate()
//                    || null == productLadderModel.getPoundage()){
//                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_LADDER_INFO_UN_COMPLETELY_CODE,
//                        Constants.PRODUCT_LADDER_INFO_UN_COMPLETELY_CODE_DESC);
//            }
//        }
//
//        return null;
//    }

    /**
     * 校验产品审核请求参数逻辑
     *
     * @param req 产品审核请求对象
     * @return 没有逻辑错误返回null
     */
    public BaseResponse checkApproveProductRequestParameter(ApproveProductRequest req, Product product) {
        if (ProductApprovalStatusEnum.getEnumItem(req.getApprovalStatus()) == null) {
            return BaseResponse.build(Constants.PRODUCT_APPROVAL_STATUS_NOT_IN_ENUMS_CODE, Constants.PRODUCT_APPROVAL_STATUS_NOT_IN_ENUMS_CODE_DESC);
        }

        if (ProductApprovalStatusEnum.WAIT_APPROVAL.getCode() != product.getApprovalStatus()) {
            return BaseResponse.build(Constants.PRODUCT_APPROVAL_STATUS_CHANGED_RESULT_CODE, Constants.PRODUCT_APPROVAL_STATUS_CHANGED_RESULT_CODE_DESC);
        }

        if(StringUtils.isBlank(product.getApprovalRequireSign())){
            return BaseResponse.build(Constants.PRODUCT_APPROVAL_SIGN_UN_CONFIG_CODE,Constants.PRODUCT_APPROVAL_SIGN_UN_CONFIG_CODE_DESC);
        }
        return null;
    }

    /**
     * 检测产品列表查询请求参数
     *
     * @param req 产品注册请求对象
     * @return 没有逻辑错误返回null
     */
    public PageQueryResponse<ProductModel> checkQueryProductListParameter(QueryProductListRequest req) {
        if(StringUtils.isNotBlank(req.getApprovalStartTime()) && StringUtils.isNotBlank(req.getApprovalEndTime())){
            Date approvalStartTime = DateUtils.parse(req.getApprovalStartTime(), DateUtils.DEFAULT_DATA_FORMAT);
            Date approvalEndTime = DateUtils.parse(req.getApprovalEndTime(), DateUtils.DEFAULT_DATA_FORMAT);
            if(DateUtils.isAfter(approvalStartTime, approvalEndTime)){
                return BaseResponse.build(PageQueryResponse.class, Constants.PRODUCT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE,
                        Constants.PRODUCT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE_DESC);
            }
        }

        if(StringUtils.isNotBlank(req.getBeginCreateTime()) && StringUtils.isNotBlank(req.getEndCreateTime())){
            Date createStartTime = DateUtils.parse(req.getBeginCreateTime(), DateUtils.DEFAULT_DATA_FORMAT);
            Date createEndTime = DateUtils.parse(req.getEndCreateTime(), DateUtils.DEFAULT_DATA_FORMAT);
            if(DateUtils.isAfter(createStartTime, createEndTime)){
                return BaseResponse.build(PageQueryResponse.class, Constants.PRODUCT_COMMIT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE,
                        Constants.PRODUCT_COMMIT_APPROVAL_START_TIME_AFTER_END_TIME_RESULT_CODE_DESC);
            }
        }

        return null;
    }

}
