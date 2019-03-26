package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 产品注册 合同信息字段 请求对象
 *
 * @author
 * @create 2017-04-10 10:32
 */
public class RegisterProductContractInfoRequest extends BaseRequest{

    private String productCode;

    /*********************产品注册 合同信息字段 start *****************************/
    /**
     * 产品合同信息列表
     */
    private List<ProductContractModel> productContractList;
    /*********************产品注册 合同信息字段 end *****************************/


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<ProductContractModel> getProductContractList() {
        return productContractList;
    }

    public void setProductContractList(List<ProductContractModel> productContractList) {
        this.productContractList = productContractList;
    }
}
