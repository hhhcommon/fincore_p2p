package com.zb.p2p.trade.common.enums;

/**
 * <p> 还款计划状态 </p>
 *
 * @author Vinson
 * @version $Id: BillStatusEnum.java, v 2.1 May 31, 2018 7:52:40 PM Zhengwenquan Exp $
 */

public enum BillStatusEnum {
    INIT("INIT", "初始化"),

    WAIT_REPAYMENT("WAIT_REPAYMENT", "待还款"),

    OVERDUE_REPAYMENT("OVERDUE_REPAYMENT", "逾期未还款"),

    PART_VERIFIED("PART_VERIFIED", "部分还款"),

    VERIFIED("VERIFIED", "全部还款"),

    OVERDUE_VERIFIED("OVERDUE_VERIFIED", "逾期已还款"),

    PAY_OFF("PAY_OFF", "已清偿"),

    CANCELED("CANCELED", "已废弃");

    private String code;
    private String desc;

    BillStatusEnum(String code, String desc) {
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

    public static BillStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (BillStatusEnum item : BillStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 接口请求的还款状态转换
     * @param assetRepayStatus
     * @return
     */
    public static BillStatusEnum getByAssetRepayStatus(Integer assetRepayStatus) {
        if (assetRepayStatus == null) {
            return null;
        }

        //0:待还款,1:部分还款,2:全部还款；3：逾期待还款；4：逾期已还款
        if (assetRepayStatus == 0) {
            return WAIT_REPAYMENT;
        } else if (assetRepayStatus == 1) {
            return PART_VERIFIED;
        } else if (assetRepayStatus == 2) {
            return VERIFIED;
        } else if (assetRepayStatus == 3) {
            return OVERDUE_REPAYMENT;
        } else if (assetRepayStatus == 4) {
            return OVERDUE_VERIFIED;
        }

        return null;
    }

}
