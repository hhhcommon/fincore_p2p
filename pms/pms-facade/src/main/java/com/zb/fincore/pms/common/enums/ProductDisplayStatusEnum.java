package com.zb.fincore.pms.common.enums;

/**
 * ProductDisplayStatusEnum
 * 产品显示状态枚举
 * @author MABIAO
 */

public enum ProductDisplayStatusEnum {

    VISIBLE(1, "显示"),

    INVISIBLE(2, "不显示");

    private int code;
    private String desc;

    ProductDisplayStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static ProductDisplayStatusEnum getEnumItem(int code) {
        for (ProductDisplayStatusEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
