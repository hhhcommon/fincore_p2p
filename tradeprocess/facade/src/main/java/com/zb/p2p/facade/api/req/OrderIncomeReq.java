package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
 
@Data
public class OrderIncomeReq implements Serializable {
    /**
     * 投资单号
     */
    private String orderNo;
 
 
}
