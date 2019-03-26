package com.zb.p2p.customer.client.domain;

import lombok.Data;

import java.util.List;

@Data
public class TxsSyncResponse<T> {

    private String code;
    private String msg;
    private List<T> data;

}
