package com.zb.p2p.trade.client.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @create 2018-06-01 15:16
 */
@Data
public class ViewContractRequest implements Serializable{

    private List<String> documentIdList;

    private String saleChannel;
}
