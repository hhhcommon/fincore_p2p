package com.zb.p2p.facade.api.req;

import lombok.Data;
import java.math.BigDecimal;

/**
 * Created by limingxin on 2017/8/31.
 */
@Data
public class OrderMatchReq {

	 /*交易系统的     外部订单号 */
    private String   extOrderNo;
    
    //资产code
    private String  assetCode;
    
    /*下单时间*/
    private String orderTime;
    /*会员id*/
    private String memberId;
    /*产品代码*/
    private String productCode;
    /*交易渠道*/
    private String saleChannel;
    /*借款总金额*/
    private BigDecimal assetAmount;
    /*匹配成交金额*/
    private BigDecimal matchedAmount;

}
