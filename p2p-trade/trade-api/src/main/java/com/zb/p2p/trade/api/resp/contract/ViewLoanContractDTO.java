package com.zb.p2p.trade.api.resp.contract;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by mengkai on 2017/8/30.
 */
@Data
public class ViewLoanContractDTO implements Serializable {

    private String contractNo;
    /**
     * 查看合同URL
     */
    private String viewUrl;
    /**
     * 下载合同URL
     */
    private String downloadUrl;

}
