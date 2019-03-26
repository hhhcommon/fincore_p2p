package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import com.zb.fincore.pms.facade.product.model.ProductLadderModel;
import java.util.List;

/**
 * 产品注册 阶梯信息字段 请求对象
 *
 * @author
 * @create 2017-04-10 10:32
 */
public class RegisterProductLadderInfoRequest extends BaseRequest{

    private String productCode;

    /*********************产品注册 阶梯信息字段 start *****************************/
    /**
     * 产品阶梯信息列表
     */
    private List<ProductLadderModel> productLadderList;
    /*********************产品注册 阶梯信息字段 end *****************************/


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<ProductLadderModel> getProductLadderList() {
        return productLadderList;
    }

    public void setProductLadderList(List<ProductLadderModel> productLadderList) {
        this.productLadderList = productLadderList;
    }
}
