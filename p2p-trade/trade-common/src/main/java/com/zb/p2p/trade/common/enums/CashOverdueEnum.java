package com.zb.p2p.trade.common.enums;

/**
 * <p> 兑付逾期标记 </p>
 *
 * @author Vinson
 * @version $Id: CashOverdueEnum.java, v 0.1 Mar 6, 2018 7:52:40 PM Zhengwenquan Exp $
 */

public enum CashOverdueEnum {
    EXPIRE("EXPIRE", "逾期兑付"),

    PART("PART", "部分垫付兑付"),

    NORMAL("NORMAL","正常兑付");

    private String code;
    private String desc;

    CashOverdueEnum(String code, String desc) {
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

    public static CashOverdueEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (CashOverdueEnum item : CashOverdueEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 接口请求的还款状态转换
     * @param repayStatus
     * @return
     */
    public static CashOverdueEnum getByRepayStatus(Integer repayStatus) {
        if (repayStatus == null) {
            return null;
        }

        // 0:待还款,1:部分还款,2:全部还款；3：逾期待还款；4：逾期已还款
        if (repayStatus == 1) {
            return PART;
        } else if (repayStatus == 2) {
            return NORMAL;
        } else if (repayStatus == 3) {
            return EXPIRE;
        }

        return null;
    }

}
