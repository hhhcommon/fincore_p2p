package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
 
@Data
public class AccountReq implements Serializable {
    /**
     * 会员ID
     */
    private String memberId;
 
 
}
