package com.zb.p2p.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class P2pBatchEntity {
    private Integer id;

    //请求流水号
    private String reqNo;

    //批次类型：CASH-兑付;DIFFER-尾差
    private String batchType;

    //处理状态：INIT:初始化;PROCESSING:处理中;DIFFER_SUCCESS:差额批次处理完成;PROCESS_FAIL:流程处理异常,PROCESS_SUCCESS:流程处理成功；PAY_FAIL:支付处理异常;PAY_SUCCESS:支付处理成功
    private String status;

    //产品编号
    private String productCode;

    //产品兑付总金额
    private BigDecimal productTotalAmount;

    //资产还款总金额
    private BigDecimal repayTotalAmount;

    //流程处理日期
    private Date batchDate;

    //备注
    private String memo;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private Integer version;

}