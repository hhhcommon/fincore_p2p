package com.zb.p2p.trade.client.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @create 2018-06-01 15:22
 */
@Data
public class ViewContractResponse implements Serializable{
    private String resultCode;
    private String resultMsg;
    private ViewContractResponseData data;
}
