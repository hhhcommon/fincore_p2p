package com.zb.fincore.pms.common.enums;

/**
 * Created by zhangxin on 2017/4/7.
 */
public enum InterfaceRetryBusinessTypeEnum {
    NONE("", ""),
    /** 资产匹配通知交易 */
    ASSET_MATCH_NOTIFY_TRADE("ASSET_MATCH_NOTIFY_TRADE", "资产匹配通知交易"),
    /** 开放产品计划通知订单 */
    OPEN_PRODUCT_PLAN_NOTIFY_ORDER("OPEN_PRODUCT_PLAN_NOTIFY_ORDER", "开放产品计划通知订单"),
    /** 开放产品计划通知订单 */
    TXS_SYNC_STATUS_NOTIFY("TXS_SYNC_STATUS_NOTIFY", "产品募集期结束或下线通知唐小僧")
    ;


    private String code;
    private String desc;

    InterfaceRetryBusinessTypeEnum(String code, String desc) {
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

    public static InterfaceRetryBusinessTypeEnum convertFromCode(String code) {
        for (InterfaceRetryBusinessTypeEnum businessTypeEnum : values()) {
            if (businessTypeEnum.getCode().equals(code)) {
                return businessTypeEnum;
            }

        }
        return NONE;
    }
}
