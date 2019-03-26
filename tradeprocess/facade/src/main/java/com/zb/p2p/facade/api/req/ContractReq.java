package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
 
@Data
public class ContractReq implements Serializable {
    /**
     * 债券编号
     */
    private String creditorNo;

    
}
