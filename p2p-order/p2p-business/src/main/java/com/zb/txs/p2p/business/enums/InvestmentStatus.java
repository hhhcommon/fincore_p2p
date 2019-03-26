package com.zb.txs.p2p.business.enums;


public enum  InvestmentStatus {

    //匹配中
    MATCHING(1,"匹配中"),
    //已回款
    PAID(3,"已回款"),
    //在投中
    INVESTING(2,"在投中"),
    //支付中
    PAYING(4,"支付中")
    ;

    Integer code;
    String value;
    private static final InvestmentStatus[] status = values();

    public static InvestmentStatus get(String strValue) {
        for (InvestmentStatus state : status) {
            if (state.getValue().equals(strValue)) {
                return state;
            }
        }
        return null;
    }

    InvestmentStatus(Integer code, String value) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }
    public Integer getCode() {
        return code;
    }

}
