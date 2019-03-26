package com.zb.p2p.trade.common.enums;

/**
 * <p> 支付结果状态码 </p>
 *
 * @author Vinson
 * @version TradeTypeEnum.java v1.0 2018/5/21 14:30 Zhengwenquan Exp $
 */
public enum TradeTypeEnum {

    RECHARGE("RECHARGE", "充值交易"),
    WITHDRAW("WITHDRAW", "提现交易"),
    LOAN("LOAN", "放款交易"),
    LOAN_FEE("LOAN_FEE", "手续费交易"),
    CASH("CASH", "兑付交易")

    ;

    private String code;
    private String desc;

    TradeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TradeTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (TradeTypeEnum item : TradeTypeEnum.values()) {
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
