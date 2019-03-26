package com.zb.fincore.pms.common.enums;

/**
 * ProductProfitTypeEnum
 * 产品收益方式枚举
 *
 * @author
 */
public enum ProductProfitTypeEnum {
	
	/** T+N */
    T_PLUS_N(1, "T+N"),
    /** 固定起息日 */
    PERIODIC_VALUE(2, "固定起息日"),
    /** 等额本息 */
    AVERAGE_CAPITAL_PLUS_INTEREST(3, "等额本息");

    private int code;
    private String desc;

    ProductProfitTypeEnum(int code, String desc) {
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
    public static ProductProfitTypeEnum getEnumItem(int code) {
        for (ProductProfitTypeEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
