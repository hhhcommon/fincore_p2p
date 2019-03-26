package com.zb.p2p.trade.api.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class IncomeDto implements Serializable{
   
    // 昨日总收益
    private BigDecimal incomeAmount;

    public IncomeDto() {
        this.incomeAmount = BigDecimal.ZERO;
    }
}
