package com.zb.txs.p2p.business.invest.repose;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by liguoliang on 2017/9/1.
 */
@Data
public class OrderMatchInfo implements Serializable {

    /**
     * 借款用户名
     */
    private String financeSubjectName;

    /**
     * 外部编号（马上贷）
     */
    private String loanNo;

    /**
     * 预约匹配金额
     */
    private BigDecimal matchedAmount;

    /**
     * 债权编号
     */
    private String creditorNo;

}
