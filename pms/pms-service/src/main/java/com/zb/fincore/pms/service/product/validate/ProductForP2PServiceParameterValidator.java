package com.zb.fincore.pms.service.product.validate;

import com.zb.fincore.common.enums.product.ProductSaleStatusEnum;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.DecimalUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.common.enums.ProductCollectStatusEnum;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.service.dal.model.Product;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 功能: 产品数据库服务参数校验器
 * 日期: 2017/4/10 0030 19:25
 * 版本: V1.0
 */
@Component
public class ProductForP2PServiceParameterValidator {

    public RegisterProductResponse checkRegisterProductPeriodInfoParameter(RegisterProductPeriodInfoRequest req, String patternCode) {
        //募集起始日<=募集截止日<=起息日； 起息日<到期日<=回款日；  当天=<募集截止时间(59分59秒)
    	
//        //募集截止日(59分59秒) 不能晚于当前日期
//        String curDateStr = DateUtils.format(new Date(), DateUtils.DEFAULT_DATA_FORMAT);
//        Date curDate = DateUtils.parse(curDateStr, DateUtils.DEFAULT_DATA_FORMAT);
//        if (DateUtils.isAfter(curDate, req.getSaleEndTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_END_TIME_AFTER_CUR_TIME_RESULT_CODE,
//                    Constants.PRODUCT_SALE_END_TIME_AFTER_CUR_TIME_RESULT_CODE_DESC);
//        }
        
        //募集起始日<=募集截止日
        if (DateUtils.isAfter(req.getSaleStartTime(), req.getSaleEndTime())) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE,
                    Constants.PRODUCT_SALE_START_DATE_AFTER_OR_EQUAL_SALE_END_DATE_CODE_DESC);
        }

        //募集截止时间<=起息日
        String saleEndTimeStr = DateUtils.format(req.getSaleEndTime(), "yyyy-MM-dd HH:mm");
        Date saleEndTime = DateUtils.parse(saleEndTimeStr, DateUtils.DEFAULT_DATA_FORMAT);
        if(DateUtils.isAfter(saleEndTime, req.getValueTime())){
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_SALE_END_DATE_LE_VALUE_DATE_CODE,
                    Constants.PRODUCT_SALE_END_DATE_LE_VALUE_DATE_CODE_DESC);
        }

//        //起息日<到期日
//        if (DateUtils.isAfterOrEqual(req.getValueTime(), req.getExpectExpireTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE,
//                    Constants.PRODUCT_VALUE_DATE_AFTER_EXPIRE_DATE_CODE_DESC);
//        }
//
//        //到期日<=回款日
//        if (DateUtils.isAfter(req.getExpectExpireTime(), req.getExpectClearTime())) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_EXPIRE_DATE_LE_EXPECT_CLEAR_PAYMENT_DATE_CODE,
//                    Constants.PRODUCT_EXPIRE_DATE_LE_EXPECT_CLEAR_PAYMENT_DATE_CODE_DESC);
//        }
        
      //起息日<回款日
      if (DateUtils.isAfterOrEqual(req.getValueTime(), req.getExpectClearTime())) {
          return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_VALUE_DATE_AFTER_EXPECT_CLEAR_DATE_CODE,
                  Constants.PRODUCT_VALUE_DATE_AFTER_EXPECT_CLEAR_DATE_CODE_DESC);
      }
        return null;
    }

    /**
     * 检测产品注册请求参数 投资收益信息 逻辑
     *
     * @param req 产品注册请求对象
     * @return 没有逻辑错误返回null
     */
    public RegisterProductResponse checkRegisterProductProfitInfoParameter(RegisterProductProfitInfoRequest req) {
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

        if(DecimalUtils.gt(req.getMinInvestAmount(), req.getMaxInvestAmount())){
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE,
                    Constants.PRODUCT_MIN_INVEST_AMOUNT_GE_MAX_INVEST_AMOUNT_RESULT_CODE_DESC);
        }

        return null;
    }
    
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

}
