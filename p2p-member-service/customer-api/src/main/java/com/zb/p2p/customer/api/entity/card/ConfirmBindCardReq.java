package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class ConfirmBindCardReq {
    @ApiModelProperty(value="银行卡号")
    private String cardNo;
    @ApiModelProperty(value="银行编码")
    private String bankCode;
    @ApiModelProperty(value="银行名称")
    private String bankName;
    @ApiModelProperty(value="卡类型 D借记卡、C贷记卡")
    private String cardType;
    @ApiModelProperty(value="证件类型 01-身份证")
    private String idCardType;
    @ApiModelProperty(value="证件号码")
    private String idCardNo;
    @ApiModelProperty(value="身份证姓名")
    private String memberName;
    @ApiModelProperty(value="银行预留手机号")
    private String mobile;
    @ApiModelProperty(value="短信验证码")
    private String smsCode;
//    @ApiModelProperty(value="客户类型 0-企业；1-个人")
//    private String memberType;
    
    public ConfirmBindCardReq() {
    }
    
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    
//    public String getMemberType() {
//        return memberType;
//    }
//
//    public void setMemberType(String memberType) {
//        this.memberType = memberType;
//    }

    @Override
    public String toString(){
        return "ConfirmBindCardReq ["
                + "mobile=" + mobile
                + ",cardNo=" + cardNo
                + ",bankCode=" + bankCode
                + ",bankName=" + bankName
                + ",idCardType=" + idCardType
                + ",idCardNo=" + idCardNo
                + ",memberName=" + memberName
                + ",smsCode=" + smsCode
                + ",cardType=" + cardType
//                + ",memberType=" + memberType
                + "]";
    }
}
