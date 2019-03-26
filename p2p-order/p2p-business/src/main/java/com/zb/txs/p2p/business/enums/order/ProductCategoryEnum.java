package com.zb.txs.p2p.business.enums.order;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2018/1/9 0004 下午 21:55
 * Version: 1.0
 */
public enum ProductCategoryEnum {
    NONE("", ""),
    MSY("MSY", "马上赢");

    private String code;
    private String desc;

    private ProductCategoryEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean validateType(String code) {
        ProductCategoryEnum[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            ProductCategoryEnum typeEnum = arr$[i$];
            if(typeEnum.getCode().equals(code)) {
                return true;
            }
        }

        return false;
    }

    public static String getTypeDesc(String code) {
        for (ProductCategoryEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item.getDesc();
            }
        }
        return null;
    }

    public static ProductCategoryEnum convertFromCode(String code) {
        for (ProductCategoryEnum categoryEnum : values()) {
            if (categoryEnum.getCode().equals(code)) {
                return categoryEnum;
            }

        }
        return NONE;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
