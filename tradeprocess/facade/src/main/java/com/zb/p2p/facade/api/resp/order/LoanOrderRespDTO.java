package com.zb.p2p.facade.api.resp.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by limingxin on 2017/9/1.
 */
@Data
public class LoanOrderRespDTO implements Serializable {

    private Integer id;

    /**
     * 第三方平台会员编号
     */
    private String memberId;

    /**
     * 借款金额
     */
    private BigDecimal loanAmount;

    /**
     * 已匹配金额
     */
    private BigDecimal matchedAmount;

    /**
     * 借款记录编号
     */
    private String loanNo;

    /**
     * 借款费用
     */
    private BigDecimal loanFee;

    /**
     * 借款利息
     */
    private BigDecimal loanInterests;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 成立日(为匹配当天日期)
     */
    private Date establishTime;

    /**
     * 起息日
     */
    private Date valueStartTime;

    /**
     * 计息结束日
     */
    private Date valueEndTime;

    /**
     * 过期时间
     */
    private Date expireDate;

    /**
     * 锁定期
     */
    private Integer lockDate;

    /**
     * 借款时间
     */
    private Date loanTime;

    /**
     * 借款状态，枚举呢
     */
    private String loanStatus;

    /**
     * 销售渠道
     */
    private String saleChannel;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    /**
     * 合同状态
     */
    private String contractStatus;

    /**
     * 放款支付状态
     */
    private String loanPaymentStatus;

    /**
     * 放款成功时间
     */
    private Date loanPaymentTime;

//    private BigDecimal totalAmount;

    private String branchBankProvince; //开户行省名

    private String branchBankCity;//开户行市名

    private String branchBankInst;//开户行机构名

    private String bankName;//收款人银行名称

    private String bankAccountNo;//收款人银行卡号

    private String memberName;//收款人姓名

    private BigDecimal loanRate; //借款利率

    private String financeSubjectName; //融资方名称

    private String financeSubjectAddress; //融资方地址

    private Integer  repaymentType; //1 到期还本付息，2 每月付息到期还本，3 等额本息，4 等额本金，5 利息自动拨付本金复投

    private String  companyCertificateNo; //借款方统一社会信用代码

    private String  financeSubjectTel; //融资方联系方式

    private String loanPurpose; //借款用途

    private List<MatchRecordDTO> matchRecordDTOList;

}
