package com.zb.fincore.pms.facade.product.model;

import com.zb.fincore.pms.common.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款信息数据模型
 */
public class ProductLoanInfoModel extends BaseModel {
    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 8817553332753369139L;

    private String loanOrderNo;

    private Integer loanType;

    private BigDecimal loanAmount;

    private BigDecimal loanRate;

    private Integer loanValueDays;

    private BigDecimal loanInterest;

    private BigDecimal loanFee;

    private String memberId;

    private String bankAccountNo;

    private Integer repaymentType;

    private String financeSubjectCode;

    private String financeSubjectName;

    private String financeSubjectAddress;

    private String financeSubjectTel;

    private String financeProjectCode;

    private String financeProjectDes;

    private String financeGuaranteeInfo;

    private String survivalPeriodInfo;

    private String enterpriseInfo;

    private BigDecimal matchAmount;//匹配金额

    private String assetNo;//债权编号

    private String riskLevel;

    private Integer leftCollectionDays;

    private Date applicationTime;

    private Date valueStartTime;

    private Date matchExpireTime;

    public String getLoanOrderNo() {
        return loanOrderNo;
    }

    public void setLoanOrderNo(String loanOrderNo) {
        this.loanOrderNo = loanOrderNo;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public Integer getLoanValueDays() {
        return loanValueDays;
    }

    public void setLoanValueDays(Integer loanValueDays) {
        this.loanValueDays = loanValueDays;
    }

    public BigDecimal getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(BigDecimal loanInterest) {
        this.loanInterest = loanInterest;
    }

    public BigDecimal getLoanFee() {
        return loanFee;
    }

    public void setLoanFee(BigDecimal loanFee) {
        this.loanFee = loanFee;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public Integer getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(Integer repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getFinanceSubjectCode() {
        return financeSubjectCode;
    }

    public void setFinanceSubjectCode(String financeSubjectCode) {
        this.financeSubjectCode = financeSubjectCode;
    }

    public String getFinanceSubjectName() {
        return financeSubjectName;
    }

    public void setFinanceSubjectName(String financeSubjectName) {
        this.financeSubjectName = financeSubjectName;
    }

    public String getFinanceSubjectAddress() {
        return financeSubjectAddress;
    }

    public void setFinanceSubjectAddress(String financeSubjectAddress) {
        this.financeSubjectAddress = financeSubjectAddress;
    }

    public String getFinanceSubjectTel() {
        return financeSubjectTel;
    }

    public void setFinanceSubjectTel(String financeSubjectTel) {
        this.financeSubjectTel = financeSubjectTel;
    }

    public String getFinanceProjectCode() {
        return financeProjectCode;
    }

    public void setFinanceProjectCode(String financeProjectCode) {
        this.financeProjectCode = financeProjectCode;
    }

    public String getFinanceProjectDes() {
        return financeProjectDes;
    }

    public void setFinanceProjectDes(String financeProjectDes) {
        this.financeProjectDes = financeProjectDes;
    }

    public String getFinanceGuaranteeInfo() {
        return financeGuaranteeInfo;
    }

    public void setFinanceGuaranteeInfo(String financeGuaranteeInfo) {
        this.financeGuaranteeInfo = financeGuaranteeInfo;
    }

    public String getSurvivalPeriodInfo() {
        return survivalPeriodInfo;
    }

    public void setSurvivalPeriodInfo(String survivalPeriodInfo) {
        this.survivalPeriodInfo = survivalPeriodInfo;
    }

    public String getEnterpriseInfo() {
        return enterpriseInfo;
    }

    public void setEnterpriseInfo(String enterpriseInfo) {
        this.enterpriseInfo = enterpriseInfo;
    }

    public BigDecimal getMatchAmount() {
        return matchAmount;
    }

    public void setMatchAmount(BigDecimal matchAmount) {
        this.matchAmount = matchAmount;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getLeftCollectionDays() {
        return leftCollectionDays;
    }

    public void setLeftCollectionDays(Integer leftCollectionDays) {
        this.leftCollectionDays = leftCollectionDays;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Date getValueStartTime() {
        return valueStartTime;
    }

    public void setValueStartTime(Date valueStartTime) {
        this.valueStartTime = valueStartTime;
    }

    public Date getMatchExpireTime() {
        return matchExpireTime;
    }

    public void setMatchExpireTime(Date matchExpireTime) {
        this.matchExpireTime = matchExpireTime;
    }
}