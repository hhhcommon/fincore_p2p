package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class CashRecordReq implements Serializable {
    //产品编号
    private String productNo;
    //会员ID
    private String memberId;
    //渠道
    private String saleChannel;

}