package com.zb.p2p.trade.common.enums;

/**
 * Created by zhangxin on 2017/4/7.
 */
public enum InterfaceRetryBusinessTypeEnum {
    NONE("", ""),
    ASSET_MATCH_NOTIFY_TXS("ASSET_MATCH_NOTIFY_TXS", "资产匹配通知唐小僧"),
    ASSET_MATCH_NOTIFY_AMS("ASSET_MATCH_NOTIFY_AMS", "资产匹配通知资管"),
    PRODUCT_FROZEN_STOCK_NOTIFY("PRODUCT_FROZEN_STOCK_NOTIFY", "通知产品冻结库存"),
    PRODUCT_STOCK_NOTIFY("PRODUCT_STOCK_NOTIFY", "通知产品占用库存"),
    ASSET_RELATION_NOTIFY("ASSET_RELATION_NOTIFY", "通知资产端绑定产品和资产关系"),
    ACCOUNT_NOTIFY_TXS("ORDER_NOTIFY_TXS", "通知唐小僧持仓单"),
    LOAN_NOTIFY_PAYMENT("LOAN_NOTIFY_PAYMENT", "通知支付放款"),
    LOAN_SUCCESS_NOTIFY_ASSERT("LOAN_SUCCESS_NOTIFY_ASSERT", "通知资管放款完成"),
    TAIL_BALANCE_TRANSFER_PARA_MEMBERID("TAIL_BALANCE_TRANSFER_PARA_MEMBERID", "调用支付进行尾差处理机构会员查询"),
    TAIL_BALANCE_TRANSFER("TAIL_BALANCE_TRANSFER", "调用支付进行尾差处理"),
    CASH_PARA_MEMBERID("CASH_PARA_MEMBERID", "调用支付进行兑付处理机构会员查询"),
    CASH_PARA_SIGNID("CASH_PARA_SIGNID", "调用支付进行兑付处理绑卡标识查询"),
    CASH("CASH", "调用支付进行兑付处理"),
    CASH_RESULT_NOTIFY_TXS("CASH_RESULT_NOTIFY_TXS", "兑付结果通知唐小僧"),
    CASH_RESULT_NOTIFY_ASSET("CASH_RESULT_NOTIFY_ASSET", "兑付结果通知资管"),
    QUERY_CASH_RESULT("QUERY_CASH_RESULT", "查询兑付状态"),
    SIGN_CONTRACT("SIGN_CONTRACT", "合同盖章"),
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
