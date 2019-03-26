package com.zb.p2p.facade.api.resp;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

 
@Data
public class RepayAmountDTO implements Serializable{
 
    private static final long serialVersionUID = -5410424256635488285L;
 
    private String loanNo;
 
    private BigDecimal amountAndIncome;  //本金+利息
    
    private BigDecimal totalLoanCharge; //手续费


}
