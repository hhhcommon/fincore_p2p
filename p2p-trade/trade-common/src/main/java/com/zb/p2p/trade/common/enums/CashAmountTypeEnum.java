package com.zb.p2p.trade.common.enums;

/**
 * <p> 兑付金额类型 </p>
 *
 * @author Vinson
 * @version CashAmountTypeEnum.java v1.0 2018/4/23 19:30 Zhengwenquan Exp $
 */
public enum CashAmountTypeEnum {

    EXPECT,

    ACTUAL,

    TRANSFER;


    public static CashAmountTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (CashAmountTypeEnum memberType : CashAmountTypeEnum.values()) {
            if (memberType.name().equals(code)) {
                return memberType;
            }
        }

        return null;
    }

}
