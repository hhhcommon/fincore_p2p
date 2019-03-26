package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
     * 外部编号（马上贷）
     */
    private String loanNo;

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
     * 借款金额
     */
    private BigDecimal loanAmount;

    /**
     * 预约匹配金额
     */
    private BigDecimal matchedAmount;

    /**
     * 预匹配状态(RESERVATION_MATCH_SUCCESS:预匹配成功,RESERVATION_MATCH_FAIL:匹配失败)
     */
    private String matchStatus;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 总收益
     */
    private BigDecimal totalIncome;

    /**
     * 是否已更新持仓 TRUE 是 FALSE 否
     */
    private String accountStatus;

    /**
     * 兑付计划生成标记 0未生成，1已生成
     */
    private Integer cashFlag;

    /**
     * 收益计算标志,0:未生成,1:已生成
     */
    private Integer interestFlag;

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

    /**
     * 借款手续费
     */
    private BigDecimal loanCharge;

    private String originalAssetCode;//资产编号
    private String transferCode;//转让编号
    private String assetPoolCode;//资产池编号
    private BigDecimal transferAmount;//债转本金(债转订单特有)
    private BigDecimal transferInterests;//债转利息(债转资产特有)
    private BigDecimal matchTransferAmount;//匹配的债转本金
    private BigDecimal matchTransferInterests;//匹配的债转利息
    private String originalOrderNo;//原始订单号
    private String parentOrderNo;//父订单号
    private Integer assetType;//资产类型(1:原始资产,2:债转资产)


}