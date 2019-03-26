package com.zb.txs.p2p.business.enums;

/**
 * Created by wangwanbin on 2017/9/1.
 */
public enum SequenceEnum {

    RESERVATION("RESERVATION", "预约sequence"),
    ORDER("ORDER", "订单sequence"),
    ACCOUNT("ACCOUNT", "持仓sequence"),
    //INVESTCONTRACT("INVESTCONTRACT","投资人合同sequence"),
    CONTRACT("ZDxwqyd-1", "借款人合同sequence"),
    ASSETMATCHE("ASSETMATCHE", "资产匹配"),
    ACCOUNT_NO("ACCOUNT_NO", "持仓单编号sequence"),
    BATCH("BATCH", "批次编号sequence"),
    CREDITOR("CREDITOR", "债权编号"),
    WITHDRAW("WITHDRAW", "提现编号"),
    TRANSFER("TRANSFER", "转账编号"),
    TRANSFER_FEE("TRANSFER_FEE", "转账手续费编号"),
    CASH("CASH", "兑付编号sequence"),
    MESSAGE("message", "短信"),
    ;


    private String code;
    private String desc;

    SequenceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
