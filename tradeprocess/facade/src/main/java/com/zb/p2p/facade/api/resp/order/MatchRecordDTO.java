package com.zb.p2p.facade.api.resp.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 匹配记录对象
 * @author
 * @create 2018-01-10 15:42
 */
@Data
public class MatchRecordDTO implements Serializable{

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

    private BigDecimal totalIncome;

    /**
     * 还款总金额：本金+手续费+利息
     */
    private BigDecimal repayAmount;

    /**
     * 还款状态：（正常：normal，逾期：expire）
     */
    private String repayStatus;

    /**
     * 兑付状态（兑付中：cashing，兑付成功：cashSucced兑付失败：cashFailed）
     */
    private String cashStatus;

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

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改人
     */
    private String modifyBy;
}
