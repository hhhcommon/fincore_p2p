package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class Card {
    @ApiModelProperty(value="银行简称")
    private String bankCode;
    @ApiModelProperty(value="银行名称")
    private String bankName;
    @ApiModelProperty(value="卡类型 D：借记卡，C：贷记卡")
    private String cardType;
    @ApiModelProperty(value="单笔限额")
    private String payMax;
    @ApiModelProperty(value="当日限额")
    private String payDayMax;

    public Card() {
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPayMax() {
        return payMax;
    }

    public void setPayMax(String payMax) {
        this.payMax = payMax;
    }

    public String getPayDayMax() {
        return payDayMax;
    }

    public void setPayDayMax(String payDayMax) {
        this.payDayMax = payDayMax;
    }
    
    @Override
    public String toString(){
        return "Card ["
                + "bankCode=" + bankCode + 
                ", bankName=" + bankName +
                ", cardType=" + cardType +
                ", payMax=" + payMax +
                ", payDayMax=" + payDayMax +
                "]";
    }
}
