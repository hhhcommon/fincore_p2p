package com.zb.p2p.facade.api.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function:查询兑付信息
 * Author: created by liguoliang
 * Date: 2017/9/8 0008 下午 4:29
 * Version: 1.0
 */
@Data
public class CashRecordDTO implements Serializable {

    //产品编号
    private String productCode;

    //会员ID
    private String memberId;

    //渠道
    private String saleChannel;

    //兑付本金
    private BigDecimal cashAmount;

    //兑付利息
    private BigDecimal cashIncome;

    //兑付日期
    private String cashDate;

    //兑付状态
    private String status;
}
