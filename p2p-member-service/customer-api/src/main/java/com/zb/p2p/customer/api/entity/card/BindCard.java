package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class BindCard {
    @ApiModelProperty(value="银行卡号")
    private String bankCardNo;
    @ApiModelProperty(value="银行编码")
    private String bankCode;
    @ApiModelProperty(value="银行名称")
    private String bankName;
    @ApiModelProperty(value="卡类型")
    private String cardType;
    @ApiModelProperty(value="证件类型 01-身份证")
    private String idCardType;
    @ApiModelProperty(value="证件号码")
    private String idCardNo;
    @ApiModelProperty(value="身份证姓名")
    private String idCardName;
    @ApiModelProperty(value="银行预留手机号")
    private String mobile;

    public BindCard() {
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
