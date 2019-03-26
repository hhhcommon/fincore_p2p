package com.zb.p2p.service.order;

import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.enums.SaleChannelEnum;
import com.zb.p2p.enums.SourceIdEnum;
import com.zb.p2p.facade.api.req.LoanReq;
import com.zb.p2p.facade.api.req.OrderReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Arrays;

/**
 * Created by limingxin on 2017/9/1.
 */
@Service
public class CheckService {
    private static Logger logger = LoggerFactory.getLogger(CheckService.class);

    /**
     * 检查借款的参数（借款不用验证，因为网关在资管系统）
     *
     * @return
     */
    public CommonResp checkLoanReq(LoanReq loanReq) {
        if (StringUtils.isBlank(loanReq.getMemberId())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "会员ID不能为空");
        }
        if (null == loanReq.getLoanAmount()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款金额不能为空");
        }
        if (StringUtils.isBlank(loanReq.getLoanNo())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款编号不能为空");
        }
        if (null == loanReq.getLoanFee()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款手续费不能为空");
        }
        if (null == loanReq.getLoanInterests()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款利息不能为空");
        }
        if (StringUtils.isBlank(loanReq.getProductCode())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "产品编码不能为空");
        }
        if (StringUtils.isBlank(loanReq.getProductName())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "产品名称不能为空");
        }
        if (null == loanReq.getLockDate()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款期限不能为空");
        }
        if (null == loanReq.getLoanTime()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款时间不能为空");
        }
        if (StringUtils.isBlank(loanReq.getSaleChannel())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "交易渠道不能为空");
        } else {
            if (!SourceIdEnum.MSD.getCode().equals(loanReq.getSaleChannel())) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "渠道错误");
            }
        }
        if (null == loanReq.getLoanRate()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款利率不能为空");
        }
        if (StringUtils.isBlank(loanReq.getBranchBankProvince())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "开户行省名不能为空");
        }
        if (StringUtils.isBlank(loanReq.getBranchBankCity())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "开户行市名不能为空");
        }
        if (StringUtils.isBlank(loanReq.getBranchBankInst())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "开户行机构名不能为空");
        }
        if (StringUtils.isBlank(loanReq.getBankName())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "收款人银行名称不能为空");
        }
        if (StringUtils.isBlank(loanReq.getBankAccountNo())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "收款人银行卡号不能为空");
        }
        if (StringUtils.isBlank(loanReq.getMemberName())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "收款人姓名不能为空");
        }

        if (StringUtils.isBlank(loanReq.getFinanceSubjectName())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "融资方名称不能为空");
        }
        if (StringUtils.isBlank(loanReq.getFinSubDesensitizationName())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "融资方脱敏名称不能为空");
        }
        if (StringUtils.isBlank(loanReq.getFinanceSubjectAddress())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "融资方地址不能为空");
        }
        if (StringUtils.isBlank(loanReq.getCompanyCertificateNo())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款方统一社会信用代码不能为空");
        }
        if (StringUtils.isBlank(loanReq.getLoanPurpose())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款用途不能为空");
        }
        if (null == loanReq.getRepaymentType()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "还款方式不能为空");
        }

        if (StringUtils.isBlank(loanReq.getFinanceSubjectTel())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "融资方联系方式不能为空");
        }
        return null;
    }

    /**
     * 检查订单的参数
     *
     * @return
     */
    public CommonResp checkOrder(OrderReq orderReq) {
        if (StringUtils.isBlank(orderReq.getProductCode())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "产品编码不能为空");
        }
        if (StringUtils.isBlank(orderReq.getExtOrderNo())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "外部订单号不能为空");
        }
        if (StringUtils.isBlank(orderReq.getSaleChannel())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "交易渠道不能为空");
        } else {
            if (!SourceIdEnum.MSD.getCode().equals(orderReq.getSaleChannel())) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "渠道错误");
            }
        }
        if (StringUtils.isBlank(orderReq.getMemberId())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "会员ID不能为空");
        }
        if (StringUtils.isBlank(orderReq.getOrderTime())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "下单时间不能为空");
        } else if (!DateUtils.judgeDateFormat(orderReq.getOrderTime())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "下单时间格式错误");
        }
        if (StringUtils.isBlank(orderReq.getName())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "用户名不能为空");
        }
        if (StringUtils.isBlank(orderReq.getCertNo())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "用户身份证不能为空");
        }
        if (StringUtils.isBlank(orderReq.getTelNo())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "联系方式不能为空");
        }
        if (null == orderReq.getInvestAmount()) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "投资金额不能为空");
        }
        return null;
    }

}
