package com.zb.p2p.trade.client.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 放款通知请求参数对象
 * Created by zhangxin on 2017/9/6.
 */
@Data
public class NotifyLoanStatusReq implements Serializable {

//    private String transactionNo;
//
//    private String orderNo;
//
//    private String notifyType;
//
//    private String status;
//
//    private String tradeAmount;
//
//    private String resultCode;
//
//    private String resultMessage;
//
//    private String requestTime;
//
//    private List<Object> data;

    private String loanOrderNo;

    private Integer loanStatus;

    private BigDecimal repayAmount;

    private Date repayTime;

}
