package com.zb.p2p.trade.client.request;

import lombok.Data;

import java.util.List;

/**
 * <p> 电子合同查询请求 </p>
 *
 * @author Vinson
 * @version ContractRequest.java v1.0 2018/5/29 0029 下午 9:03 Zhengwenquan Exp $
 */
@Data
public class ContractRequest {

    List<String> assetNoList;
}
