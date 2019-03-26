package com.zb.p2p.trade.api.req;

import java.io.Serializable;

/**
 * Created by mengkai on 2017/8/31.
 */
public class InvestContractReq implements Serializable {
    /**
     * 投资人会员id
     */
    private String memberId;

    /**
     * 产品编码
     */
    private String productCode;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
