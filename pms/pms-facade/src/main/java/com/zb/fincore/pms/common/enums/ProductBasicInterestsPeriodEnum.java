package com.zb.fincore.pms.common.enums;

/**
 * ProductBasicInterestsPeriodEnum
 * 年基础计息周期(360, 365, 366三种枚举值)
 * 2016年11月22日
 *
 * @author zhangxin
 */
public enum ProductBasicInterestsPeriodEnum {

    ProductBasicInterestsPeriod_360(360, "360计息方式"),
    ProductBasicInterestsPeriod_365(365, "365计息方式"),
    ProductBasicInterestsPeriod_366(366, "366计息方式");

    private int code;
    private String desc;

    ProductBasicInterestsPeriodEnum(int code, String desc) {
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
    public static ProductBasicInterestsPeriodEnum getEnumItem(int code) {
        for (ProductBasicInterestsPeriodEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
