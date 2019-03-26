package com.zb.fincore.pms.common.enums;

/**
 * Created by kaiyun on 2017/1/11 0011. ZHYE:账户余额、ZZB:至尊宝、YHK:银行卡
 */
public enum BuyWaysEnum {

	/** ZHYE - 账户余额 */
	ZHYE("ZHYE", "账户余额"),
    /** ZZB - 至尊宝 */
    ZZB("ZZB", "至尊宝"),
    /** YHK - 银行卡 */
    YHK("YHK", "银行卡");

    private String code;
    private String desc;

    BuyWaysEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static BuyWaysEnum getEnumItem(String code) {
        for (BuyWaysEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
