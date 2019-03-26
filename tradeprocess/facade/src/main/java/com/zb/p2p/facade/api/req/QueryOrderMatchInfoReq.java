package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询订单详情
 * @author
 * @create 2018-01-10 15:39
 */
@Data
public class QueryOrderMatchInfoReq implements Serializable{

    private String extOrderNo;
}
