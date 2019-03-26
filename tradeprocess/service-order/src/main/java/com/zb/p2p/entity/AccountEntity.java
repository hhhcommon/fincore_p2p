package com.zb.p2p.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountEntity {
    private Long id;

    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 第三方会员编号
     */
    private String memberId;

    /**
     * 销售渠道
     */
    //private String saleChannel;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 资产持有金额
     */
    private BigDecimal amount;

    private BigDecimal interest; //   总收益

    private Integer version;

    /**
     * 投资标记 提现标记
     */
    //private Integer cashFlag; //已废弃

    
    //private String interestFlag;
    private String  withdrawFlag;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    /*提现流水号*/
    private String withdrawNo;

}