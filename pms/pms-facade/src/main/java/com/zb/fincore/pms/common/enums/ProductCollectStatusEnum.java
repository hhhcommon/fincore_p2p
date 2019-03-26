package com.zb.fincore.pms.common.enums;

/**
 * ProductCollectStatusEnum
 * 产品募集状态枚举
 * @author MABIAO
 */

public enum ProductCollectStatusEnum {

	/** 10 - 待募集 */
    PRODUCT_COLLECT_STATUS_WAIT_COLLECT(10, "待募集"),
    /** 11 - 募集期 */
    PRODUCT_COLLECT_STATUS_COLLECTING(11, "募集期"),
    /** 12 - 待成立 */
    PRODUCT_COLLECT_STATUS_WAIT_ESTABLISH(12, "待成立"),
    /** 13 - 已成立 */
    PRODUCT_COLLECT_STATUS_ESTABLISHED(13, "已成立"),
    /** 14 - 存续期 */
    PRODUCT_COLLECT_STATUS_VALUING(14, "存续期"),
    /** 15 - 到期 */
    PRODUCT_COLLECT_STATUS_VALUE_EXPIRE(15, "到期"),
    /** 16 - 待兑付 */
    PRODUCT_COLLECT_STATUS_WAIT_REDEEM(16, "待兑付"),
    /** 17 - 兑付完成 */
    PRODUCT_COLLECT_STATUS_REDEEMED(17, "兑付完成"),
    /** 18 - 流标 */
    PRODUCT_COLLECT_STATUS_FAILURE_SALE(18, "流标");
	
    private int code;
    private String desc;

    ProductCollectStatusEnum(int code, String desc) {
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
    public static ProductCollectStatusEnum getEnumItem(int code) {
        for (ProductCollectStatusEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
