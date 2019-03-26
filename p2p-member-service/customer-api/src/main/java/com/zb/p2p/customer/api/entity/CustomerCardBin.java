/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import com.zb.p2p.customer.api.entity.card.Card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author guolitao
 *
 */
@ApiModel
public class CustomerCardBin extends Card {

    @ApiModelProperty(value="绑定ID")
    private Long bindId;
    @ApiModelProperty(value="银行卡号")
    private String bankCardNo;
    @ApiModelProperty(value="签约协议号")
    private String signId;
    @ApiModelProperty(value="银行预留手机号")
    private String bankMobile;
    @ApiModelProperty(value="是否可以解绑")
    private boolean unbindFlag;
    
    @ApiModelProperty(value="卡类型 D：借记卡，C：贷记卡")
    private String cardType;
    @ApiModelProperty(value="单笔限额")
    private String payMax;
    @ApiModelProperty(value="当日限额")
    private String payDayMax;
    
    private String idCardNo; // 身份证
    private String idCardName; //姓名
    
    
    
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

	
    
    public Long getBindId() {
        return bindId;
    }

    public void setBindId(Long bindId) {
        this.bindId = bindId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public boolean isUnbindFlag() {
        return unbindFlag;
    }

    public void setUnbindFlag(boolean unbindFlag) {
        this.unbindFlag = unbindFlag;
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
}
