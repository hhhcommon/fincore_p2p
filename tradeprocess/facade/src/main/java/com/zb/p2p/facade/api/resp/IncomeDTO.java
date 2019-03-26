package com.zb.p2p.facade.api.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
 
@Data
public class IncomeDTO implements Serializable{
   

    private BigDecimal income;

   
}
