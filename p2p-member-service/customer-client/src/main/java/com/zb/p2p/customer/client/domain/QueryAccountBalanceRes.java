package com.zb.p2p.customer.client.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class QueryAccountBalanceRes {

    //	可用余额	BigDecimal	是	单位为元，保留小数点2位，如：2.00代表2元
    private BigDecimal availableAmount;
    //	我司冻结余额	BigDecimal	是	单位为元，保留小数点2位，如：2.00代表2元
    private BigDecimal frozenAmount;
    //	账户总额	BigDecimal	是	单位为元，保留小数点2位，如：2.00代表2元
    private BigDecimal totalAmount;
}
