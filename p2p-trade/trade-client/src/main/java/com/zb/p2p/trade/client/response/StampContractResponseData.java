package com.zb.p2p.trade.client.response;

import lombok.Data;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version StampContractResponseData.java v1.0 2018/5/29 0029 下午 9:45 Zhengwenquan Exp $
 */
@Data
public class StampContractResponseData {

    private String contractNo;

    // 契约锁唯一id
    private Long documentId;
}
