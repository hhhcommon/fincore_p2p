package com.zb.p2p.trade.persistence.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class RepayAmountDTO {
 
    private String assetCode;

    private String stageSeq;

    private Date billStartDate;
    private Date billEndDate;
 
    private BigDecimal principle;  // 本金

    private BigDecimal income;  // 利息
    
    private BigDecimal loanCharge; //手续费

}
