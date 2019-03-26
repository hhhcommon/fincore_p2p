package com.zb.p2p.paychannel.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseReq implements Serializable{

    private static final long serialVersionUID = 8910746980088819191L;

    /**
     * 客户端ID
     */
    private String            clientId;

    /**
     * 客户端IP
     */
    private String            clientIp;

    /**
     * 服务端IP
     */
    private String            serverIp;
}
