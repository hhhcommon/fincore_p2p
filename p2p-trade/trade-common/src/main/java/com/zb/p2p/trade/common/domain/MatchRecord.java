package com.zb.p2p.trade.common.domain;

import com.zb.p2p.trade.common.enums.MatchStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 匹配记录对象
 * @author
 * @create 2018-01-10 15:42
 */
@Data
public class MatchRecord {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 平台用户id
     */
    private String memberId;

    /**
     * 借款用户会员id
     */
    private String loanMemberId;

    /**
     * 借款编号（马上贷）
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

    private String parentOrderNo;

    private String originalOrderNo;

    /**
     * 外部订单号（唐小僧）
     */
    private String extOrderNo;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 原始资产编号
     */
    private String originalAssetCode;

    /**
     * 转让资产编号
     */
    private String transferCode;

    /**
     * 预约匹配金额
     */
    private BigDecimal matchedAmount;

    /**
     * 预匹配状态(RESERVATION_MATCH_SUCCESS:预匹配成功,RESERVATION_MATCH_FAIL:匹配失败)
     */
    private MatchStatusEnum matchStatus;

    private BigDecimal totalIncome;

    /**
     * 还款状态：（正常：normal，逾期：expire）
     */
    private String repayStatus;

    /**
     * 债权状态:（成立：created；逾期：expired；待兑付：unCash；兑付中：cashing；已兑付：cashSucced；兑付失败：cashFailed）
     */
    private String creditorStatus;

    /**
     * 是否已更新持仓 TRUE 是 FALSE 否
     */
    private String accountStatus;

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

    private BigDecimal loanCharge;

    /**
     * 资产类型(1:原始资产,2:债转资产)
     */
    private Integer AssetType;
}
