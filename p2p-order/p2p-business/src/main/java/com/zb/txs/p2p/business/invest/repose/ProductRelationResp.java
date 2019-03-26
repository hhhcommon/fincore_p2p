package com.zb.txs.p2p.business.invest.repose;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by limingxin on 2018/1/19.
 */
@Data
public class ProductRelationResp {
    String loanOrderNo, loanType, repaymentType, financeSubjectCode, financeSubjectName, financeSubjectAddress, financeSubjectTel, financeProjectCode, financeProjectDes, financeGuaranteeInfo, survivalPeriodInfo, enterpriseInfo, assetNo, riskLevel;
    int leftCollectionDays, loanValueDays;
    String applicationTime, valueStartTime;
    BigDecimal loanAmount, loanRate, loanInterest, loanFee, matchAmount;

}
