/**
 * 
 */
package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author guolitao
 *
 */
@ApiModel
public class BindCardHelp {

    @ApiModelProperty(value="姓名")
    private String name;
    @ApiModelProperty(value="证件号")
    private String idCardNo;
    @ApiModelProperty(value="银行卡号")
    private String bankCardNo;
    @ApiModelProperty(value="银行预留手机号")
    private String bankMobile;
    @ApiModelProperty(value="是否实名")
    private String isReal;
    @ApiModelProperty(value="是否已绑卡")
    private String isBindCard;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getIsReal() {
        return isReal;
    }

    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }

    public String getIsBindCard() {
        return isBindCard;
    }

    public void setIsBindCard(String isBindCard) {
        this.isBindCard = isBindCard;
    }

    @Override
    public String toString(){
        return "BindCardHelp["
                + "name=" + name + 
                ",idCardNo=" + idCardNo + 
                ",bankCardNo=" + bankCardNo + 
                ",bankMobile=" + bankMobile + 
                ",isReal=" + isReal + 
                ",isBindCard=" + isBindCard + 
                "]";
    }
}
