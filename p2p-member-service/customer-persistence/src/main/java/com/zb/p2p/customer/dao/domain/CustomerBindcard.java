package com.zb.p2p.customer.dao.domain;

import java.util.Date;

public class CustomerBindcard {
    private Long bindId;

    private Long customerId;

    private String bankCardNo;

    private String bankCode;

    private String bankName;

    private String cardType;

    private Integer status;

    private String idCardType;

    private String idCardNo;

    private String idCardName;

    private String mobile;

    private String preOrderNo;

    private Date preOrderTime;

    private String confirmOrderNo;

    private Date confirmOrderTime;

    private String signId;

    private Date createTime;

    private Date updateTime;

    public Long getBindId() {
        return bindId;
    }

    public void setBindId(Long bindId) {
        this.bindId = bindId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType == null ? null : idCardType.trim();
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName == null ? null : idCardName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPreOrderNo() {
        return preOrderNo;
    }

    public void setPreOrderNo(String preOrderNo) {
        this.preOrderNo = preOrderNo == null ? null : preOrderNo.trim();
    }

    public Date getPreOrderTime() {
        return preOrderTime;
    }

    public void setPreOrderTime(Date preOrderTime) {
        this.preOrderTime = preOrderTime;
    }

    public String getConfirmOrderNo() {
        return confirmOrderNo;
    }

    public void setConfirmOrderNo(String confirmOrderNo) {
        this.confirmOrderNo = confirmOrderNo == null ? null : confirmOrderNo.trim();
    }

    public Date getConfirmOrderTime() {
        return confirmOrderTime;
    }

    public void setConfirmOrderTime(Date confirmOrderTime) {
        this.confirmOrderTime = confirmOrderTime;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId == null ? null : signId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}