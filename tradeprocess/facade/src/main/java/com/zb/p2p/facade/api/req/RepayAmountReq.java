package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
 
@Data
public class RepayAmountReq implements Serializable {
     
	/**
	 * 借款单编号
	 */
    private List< String> loanNoList;
     
}
