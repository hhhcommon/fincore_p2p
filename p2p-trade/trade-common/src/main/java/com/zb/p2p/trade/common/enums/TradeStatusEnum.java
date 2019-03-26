package com.zb.p2p.trade.common.enums;

/**
 * <p> 支付结果状态码 </p>
 *
 * @author Vinson
 * @version TradeStatusEnum.java v1.0 2018/5/21 14:30 Zhengwenquan Exp $
 */
public enum TradeStatusEnum {

    PROCESSING("P", "支付交易处理中"),

    SUCCESS("S", "支付交易成功"),

    FAIL("F", "支付交易失败"),

    CLOSED("C", "支付交易关闭");

    private String code;
    private String desc;

    TradeStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TradeStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (TradeStatusEnum item : TradeStatusEnum.values()) {
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
