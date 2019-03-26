package com.zb.txs.p2p.order.service;

import com.alibaba.druid.util.StringUtils;
import com.zb.txs.p2p.business.enums.order.ResponseCodeEnum;
import com.zb.txs.p2p.business.order.request.OrderCallBackReq;
import com.zb.txs.p2p.business.order.response.CommonResponse;
import com.zb.txs.p2p.business.product.request.ProductCutDayRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Function:  参数校验  <br/>
 * Date:  2017/10/19 14:15 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Slf4j
public class OrderServiceParamsDetector {

    /**
     * 订单回调 参数校验
     *
     * @param request
     * @return
     */
    public static CommonResponse<Object> verifyOrderNotify(OrderCallBackReq request) {
        log.info("verifyOrderNotify,Request：{}", Objects.isNull(request) ? "null" : request.toString());
        if (Objects.isNull(request)) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "参数校验失败", null);
        } else if (StringUtils.isEmpty(request.getMemberId())) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "会员ID不能为空", null);
        } else if (StringUtils.isEmpty(request.getOriginalOrderNo())) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "短信验证码流水号不能为空", null);
        } else if (StringUtils.isEmpty(request.getOrderId())) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "订单流水号不能为空", null);
//        } else if (StringUtils.isEmpty(request.getInvestAmount())) {
//            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "投资金额不能为空", null);
//        } else if (StringUtils.isEmpty(request.getPayNo())) {
//            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "支付流水号不能为空", null);
        } else if (StringUtils.isEmpty(request.getPayStatus())) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "支付状态不能为空", null);
        } else if (StringUtils.isEmpty(request.getPayCode())) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "支付返回码不能为空", null);
        } else if (StringUtils.isEmpty(request.getPayMsg())) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "支付返回信息不能为空", null);
        }
        return CommonResponse.success(null);
    }

    /**
     * 验证日切参数
     * @param productCutDayRecord
     * @return
     */
    public static CommonResponse<Object> validateParameters(ProductCutDayRecord productCutDayRecord) {
        try {
            Objects.requireNonNull(productCutDayRecord, "请求参数不能为空");
            log.info("日切参数：{}", productCutDayRecord.toString());
//            if (StringUtils.isEmpty(productCutDayRecord.getSerialNo())) {
//                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "唯一号为空", null);
//            }
            if (Objects.isNull(productCutDayRecord.getProductCodes())) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "产品Code为null", null);
            }
            if (productCutDayRecord.getProductCodes().size() == 0) {
                return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "产品Code为空", null);
            }
        }catch (Exception ex){
            log.error("日切参数：{} 出现问题",productCutDayRecord.toString());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "日切参数异常", null);
        }
        return  CommonResponse.success(null);
    }

}
