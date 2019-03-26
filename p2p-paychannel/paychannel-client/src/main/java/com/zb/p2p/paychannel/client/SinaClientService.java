package com.zb.p2p.paychannel.client;

import feign.Headers;
import feign.RequestLine;
import java.util.List;

/**
 * 系统接口定义
 */
public interface SinaClientService {

    /**
     * @param list
     * @return
     */
    @RequestLine("POST /internal/createDebtRight.json")
    @Headers("Content-Type: application/json")
    void createDebtRight(List<String> list);

}
