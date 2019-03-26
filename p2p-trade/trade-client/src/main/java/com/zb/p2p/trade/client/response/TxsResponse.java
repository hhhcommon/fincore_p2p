package com.zb.p2p.trade.client.response;

import lombok.Data;

/**
 * <p> 唐小僧接口响应结果 </p>
 *
 * @author Vinson
 * @version TxsResponse.java v1.0 2018/6/9 0009 下午 2:46 Zhengwenquan Exp $
 */
@Data
public class TxsResponse {

    // success, system_error
    private String response_code;

    private String response_message;

}
