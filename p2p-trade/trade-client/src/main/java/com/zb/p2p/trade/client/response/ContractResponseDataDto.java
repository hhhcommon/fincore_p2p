package com.zb.p2p.trade.client.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @create 2018-06-01 15:22
 */
@Data
public class ContractResponseDataDto implements Serializable{

    private Long documentId;
    private String viewUrl;
    private String downloadUrl;

}
