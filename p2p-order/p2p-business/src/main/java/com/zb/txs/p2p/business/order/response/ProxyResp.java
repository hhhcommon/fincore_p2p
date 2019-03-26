package com.zb.txs.p2p.business.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by limingxin on 2018/1/19.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProxyResp<T> {
    String respCode, respMsg, addition;
    List<T> dataList;
}
