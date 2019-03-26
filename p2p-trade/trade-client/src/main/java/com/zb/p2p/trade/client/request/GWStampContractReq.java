package com.zb.p2p.trade.client.request;

import lombok.Data;

/**
 * <p> 电子签章请求 </p>
 *
 * @author Vinson
 * @version GWStampContractReq.java v1.0 2018/5/29 0029 下午 9:21 Zhengwenquan Exp $
 */
@Data
public class GWStampContractReq {

    private String bizType; // P2P
    private String extSerialNo;
    private String saleChannel; //渠道
    // html格式的合同
    private String htmlStr;
    private String personName;
    private String personIdcard;
    private String personPaperType;
    private String personMobile;

    private String companyName;
    private String companyRegisterNo;
    private String companyAddress;
}
