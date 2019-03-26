package com.zb.p2p.trade.client.response;

import lombok.Data;

import java.util.List;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version GWStampContractResponse.java v1.0 2018/5/29 0029 下午 9:43 Zhengwenquan Exp $
 */
@Data
public class GWStampContractResponse {

    private String resultCode;
    private String resultMsg;

    private StampContractResponseData data;
}
