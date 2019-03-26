package com.zb.p2p.trade.common.enums;

/**
 * <p> 还款方式 </p>
 *
 * @author Vinson
 * @version RepaymentTypeEnum.java v1.0 2018/4/20 19:30 Zhengwenquan Exp $
 */
public enum RepaymentTypeEnum {
    STAGE("STAGE", "分期还本付息"),

    CREDITOR("CREDITOR", "一次性到期还本付息"),

    TRANSFER("TRANSFER", "债转资产");

    private String code;
    private String desc;

    RepaymentTypeEnum(String code, String desc) {
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

    public static RepaymentTypeEnum getByRepayType(String code) {
        if (code == null) {
            return null;
        }

        for (RepaymentTypeEnum item : RepaymentTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 接口请求的还款方式转换为债权的还款类型
     * @param repaymentType
     * @return
     */
    public static RepaymentTypeEnum getByRepayType(Integer repaymentType) {
        if (repaymentType == null) {
            return null;
        }

        //1 到期还本付息，2 每月付息到期还本，3 等额本息，4 等额本金，5 利息自动拨付本金复投
        if (repaymentType == 1) {
            return CREDITOR;
        } else if (repaymentType == 3) {
            return STAGE;
        }

        return null;
    }
}
