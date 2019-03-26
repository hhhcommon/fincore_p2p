package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;

import java.util.List;

/**
 * 产品募集状态变更请求参数
 *
 * @author
 * @create 2017-4-11 10:05:44
 */
public class NoticeProductStockSelloutRequest extends BaseRequest {

    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 8668717300620958433L;

    private List<String> productCodeList;

    public List<String> getProductCodeList() {
        return productCodeList;
    }

    public void setProductCodeList(List<String> productCodeList) {
        this.productCodeList = productCodeList;
    }
}
