package com.zb.p2p.trade.persistence.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p> 金额统计映射 </p>
 *
 * @author Vinson
 * @version CashSumAmountEntity.java v1.0 2018/4/25 10:23 Zhengwenquan Exp $
 */
@Data
public class CashSumAmountEntity {

    private String memberId;

    private BigDecimal expectPrincipal;
    private BigDecimal expectInterest;
    private BigDecimal actualPrincipal;
    private BigDecimal actualInterest;

    private BigDecimal amount;

    private BigDecimal totalPortion;

}
