package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class CardBin {
    @ApiModelProperty(value="银行简称")
    private String bankCode;
    @ApiModelProperty(value="银行名称")
    private String bankName;
    @ApiModelProperty(value="卡类型 D：借记卡，C：贷记卡")
    private String cardType;

    public CardBin() {
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
    
    @Override
    public String toString(){
        return "CardBin ["
                + "bankCode=" + bankCode + 
                ", bankName=" + bankName +
                ", cardType=" + cardType +
                "]";
    }
}
