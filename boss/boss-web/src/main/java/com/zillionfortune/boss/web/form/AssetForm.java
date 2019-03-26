package com.zillionfortune.boss.web.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yuxingsong on 2017/1/9 0009.
 */
public class AssetForm {
    private String productCode;
    private String confirmResult;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getConfirmResult() {
        return confirmResult;
    }

    public void setConfirmResult(String confirmResult) {
        this.confirmResult = confirmResult;
    }
}
