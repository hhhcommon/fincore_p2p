package com.zb.p2p.trade.api.req;

import com.zb.p2p.trade.common.util.MD5Util;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class RepayAmountReq implements Serializable {

//    @NotNull(message = "借款单编号不能为空")
//    @Size(min = 1, message = "借款单编号不能为空")
//    private List<String> loanNoList;

    @NotNull(message = "资产编号不能为空")
    @Size(min = 1, message = "资产编号不能为空")
    private List<String> assetCodeList;

    public String generateCacheKey() {
        return MD5Util.md5For32(this.toString().getBytes());
    }
}
