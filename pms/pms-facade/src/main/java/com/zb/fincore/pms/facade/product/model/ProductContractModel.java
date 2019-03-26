package com.zb.fincore.pms.facade.product.model;

import com.zb.fincore.pms.common.model.BaseModel;

/**
 * 功能: 产品合同信息数据模型
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 09:40
 * 版本: V1.0
 */
public class ProductContractModel extends BaseModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 9122336181340608628L;

    /**
     * 产品主键
     */
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同展示名称
     */
    private String contractDisplayName;

    /**
     * 合同文件路径
     */
    private String contractFileUrl;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractDisplayName() {
        return contractDisplayName;
    }

    public void setContractDisplayName(String contractDisplayName) {
        this.contractDisplayName = contractDisplayName;
    }

    public String getContractFileUrl() {
        return contractFileUrl;
    }

    public void setContractFileUrl(String contractFileUrl) {
        this.contractFileUrl = contractFileUrl;
    }
}
