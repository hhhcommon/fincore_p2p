package com.zb.fincore.pms.common.enums;

/**
 * ProductSyncStatusEnum
 * 产品同步状态枚举
 * @author MABIAO
 */

public enum ProductSyncStatusEnum {

    UN_SYNC(0, "未同步"),
    SYNCED(1, "已同步");

    private int code;
    private String desc;

    ProductSyncStatusEnum(int code, String desc) {
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
    public static ProductSyncStatusEnum getEnumItem(int code) {
        for (ProductSyncStatusEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
