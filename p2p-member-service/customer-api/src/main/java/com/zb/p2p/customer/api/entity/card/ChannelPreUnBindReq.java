package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class ChannelPreUnBindReq extends PreUnBindReq{
    @ApiModelProperty(value="客户编号")
    private String clientNo;
    @ApiModelProperty(value="客户端流水号")
    private String clientSeqNo;
    @ApiModelProperty(value="侨金所会员号")
    private String qjsCustomerId;
    
    public ChannelPreUnBindReq() {
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }

    public String getClientSeqNo() {
        return clientSeqNo;
    }

    public void setClientSeqNo(String clientSeqNo) {
        this.clientSeqNo = clientSeqNo;
    }
    
    public String getQjsCustomerId() {
        return qjsCustomerId;
    }

    public void setQjsCustomerId(String qjsCustomerId) {
        this.qjsCustomerId = qjsCustomerId;
    }
    
    @Override
    public String toString(){
        return "ChannelPreUnBindReq ["
                + "clientNo=" + clientNo + 
                ", clientSeqNo=" + clientSeqNo +
                ", qjsCustomerId=" + qjsCustomerId +
                "]";
    }
}
