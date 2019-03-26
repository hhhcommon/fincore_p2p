package com.zb.fincore.pms.common.enums;

/**
 * ProductLineStatusEnum
 * 产品线状态枚举
 * 2016年11月22日
 *
 * @author zhangxin
 */
public enum ProductUnitEnum {

    SHARE(1, "份额"),
    RMB(2, "人民币");

    private int code;
    private String desc;

    ProductUnitEnum(int code, String desc) {
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
    public static ProductUnitEnum getEnumItem(int code) {
        for (ProductUnitEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
