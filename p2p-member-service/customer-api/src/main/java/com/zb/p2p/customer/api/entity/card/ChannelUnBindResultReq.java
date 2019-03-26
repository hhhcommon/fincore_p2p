package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class ChannelUnBindResultReq extends PreUnBindReq{
    @ApiModelProperty(value="客户编号")
    private String clientNo;
    @ApiModelProperty(value="客户端流水号")
    private String clientSeqNo;
    
    public ChannelUnBindResultReq() {
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
    
    @Override
    public String toString(){
        return "ChannelUnBindResultReq ["
                + "clientNo=" + clientNo + 
                ", clientSeqNo=" + clientSeqNo +
                "]";
    }
}
