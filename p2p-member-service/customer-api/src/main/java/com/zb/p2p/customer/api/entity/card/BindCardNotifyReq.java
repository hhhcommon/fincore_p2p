package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class BindCardNotifyReq {
    @ApiModelProperty(value="客户ID")
    private String customerId;
    @ApiModelProperty(value="直接绑卡流水号")
    private String orderNo;
    @ApiModelProperty(value="处理结果状态1-成功2-处理中3-失败")
    private String status;
    @ApiModelProperty(value="处理结果内容")
    private String content;
    @ApiModelProperty(value="E账户账号")
    private String eBankAccount;
    @ApiModelProperty(value="虚拟户账户")
    private String virtualAccount;
    
    public BindCardNotifyReq() {
    }
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String geteBankAccount() {
        return eBankAccount;
    }

    public void seteBankAccount(String eBankAccount) {
        this.eBankAccount = eBankAccount;
    }
    
    public String getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(String virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    @Override
    public String toString(){
        return "BindCardNotifyReq ["
                + "customerId=" + customerId
                + ",orderNo=" + orderNo
                + ",status=" + status
                + ",content=" + content
                + ",eBankAccount=" + eBankAccount
                + ",virtualAccount=" + virtualAccount
                + "]";
    }
}
