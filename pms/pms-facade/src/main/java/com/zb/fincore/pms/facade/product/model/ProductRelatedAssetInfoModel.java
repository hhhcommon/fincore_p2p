package com.zb.fincore.pms.facade.product.model;

import java.io.Serializable;

/**
 * 产品关联的资产相关信息
 * Created by zhangxin on 2017/6/13 0013.
 */
public class ProductRelatedAssetInfoModel implements Serializable {

    private static final long serialVersionUID = -321264850917559846L;

    /**
     * 受托方
     */
    private String trusteeName;

    /**
     * 合作结构
     */
    private String cooperationOrgName;

    public String getTrusteeName() {
        return trusteeName;
    }

    public void setTrusteeName(String trusteeName) {
        this.trusteeName = trusteeName;
    }

    public String getCooperationOrgName() {
        return cooperationOrgName;
    }

    public void setCooperationOrgName(String cooperationOrgName) {
        this.cooperationOrgName = cooperationOrgName;
    }
}
