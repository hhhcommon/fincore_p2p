package com.zb.p2p.facade.api.resp.contract;

import java.io.Serializable;

import lombok.Data;
 
@Data
public class ContractParamDTO implements Serializable {
	 
	private String repayDeadline; 
    
    private String overdueInterestRate; 
    
    private String overdueDaysForTerminateContract;  
}
