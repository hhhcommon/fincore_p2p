package com.zb.p2p.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 预匹配实体
 *
 * @author zhangxin
 * @create 2017-08-31 11:00
 */
@Data
public class MatchRecordEntity {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 借款用户会员id
     */
    private String loanMemberId;

    /**
     * 平台用户id
     */
    private String memberId;

    /**
     * 债权编号
     */
    private String creditorNo;

    /**
     * 外部编号（马上贷）
     */
    private String loanNo;

    /**
     * 借款金额
     */
    private BigDecimal loanAmount;

    /**
     * 交易系统生成的订单编号
     */
    private String orderNo;

    /**
     * 外部订单号（唐小僧）
     */
    private String extOrderNo;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 预约匹配金额
     */
    private BigDecimal matchedAmount;

    /**
     * 预匹配状态(RESERVATION_MATCH_SUCCESS:预匹配成功,RESERVATION_MATCH_FAIL:匹配失败)
     */
    private String matchStatus;
    
    /*private String loanPaymentStatus; //放款状态*/

    /**
     * 是否已更新持仓 TRUE 是 FALSE 否
     */
    private String accountStatus;

    /**
     * 兑付计划生成标记 0未生成，1已生成
     */
    private String cashFlag;


    private BigDecimal totalIncome;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改人
     */
    private String modifyBy;
    
    private BigDecimal loanCharge;
    
    
    

}