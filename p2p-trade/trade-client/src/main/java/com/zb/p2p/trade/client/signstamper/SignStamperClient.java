package com.zb.p2p.trade.client.signstamper;

import com.zb.p2p.trade.client.request.GWStampContractReq;
import com.zb.p2p.trade.client.request.ViewContractRequest;
import com.zb.p2p.trade.client.response.GWStampContractResponse;
import com.zb.p2p.trade.client.response.ViewContractResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * <p> 调用电子签章(合同中心)服务Client </p>
 *
 * @author Vinson
 * @version SignStamperClient.java v1.0 2018/3/9 14:20 Zhengwenquan Exp $
 */
public interface SignStamperClient {

    /**
     * 电子合同签章
     * @param req
     * @return
     */
    @RequestLine("POST /tripartiteAuthent/authent")
    @Headers("Content-Type: application/json")
    GWStampContractResponse contractForStamp(GWStampContractReq req);

    /**
     * 电子合同查看URL和下载URL
     * @param req
     * @return
     */
    @RequestLine("POST /signStamper/batchContractViewUrlAndDownload")
    @Headers("Content-Type: application/json")
    ViewContractResponse viewAndDownloadContract(ViewContractRequest req);
}
