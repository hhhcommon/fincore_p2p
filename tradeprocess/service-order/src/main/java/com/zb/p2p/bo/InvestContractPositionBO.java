package com.zb.p2p.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mengkai on 2017/8/30.
 */
@Data
public class InvestContractPositionBO implements Serializable {
    /**
     * 合同编号 预约单
     */
    private String investContractNo, reservationNo,investTelNo;
    /**
     * 渠道
     */
    private String saleChannel;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 投资人id
     */
    private String investMemberId;
    /**
     * 投资人姓名（甲方）
     */
    private String investMemberName;
    /**
     * 身份证号码
     */
    private String investIdentityCard;
    /**
     * 投资金额
     */
    private BigDecimal investTotalAmount;
    /**
     * 年化收益率
     */
    private BigDecimal investYearYield;
    /**
     * 委托投资日(用户投资成功日期，精确到年月日)
     */
    private Date investStartDate;
    /**
     * 委托到期日(此阶梯产品到期日)
     */
    private Date investEndDate;
    /**
     * 投资期限(天)
     */
    private int investLockDate;


}
