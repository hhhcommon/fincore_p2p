package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by limingxin on 2017/8/31.
 */
@Data
public class OrderReq implements Serializable{

    /*会员id*/
    private String memberId;

    /*交易系统外部订单号*/
    private String extOrderNo;

    private String orderNo;

    /*产品代码*/
    private String productCode;

    /*交易渠道*/
    private String saleChannel;

    /*投资金额*/
    private BigDecimal investAmount;

    /*下单时间*/
    private String orderTime;

    private String name;

    private String certNo;

    private String telNo;

}
