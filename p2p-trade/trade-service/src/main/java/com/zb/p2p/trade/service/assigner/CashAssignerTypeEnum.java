package com.zb.p2p.trade.service.assigner;

/**
 * <p> 兑付分配器类型 </p>
 *
 * @author Vinson
 * @version CashAssignerTypeEnum.java v1.0 2018/3/7 19:30 Zhengwenquan Exp $
 */
public enum CashAssignerTypeEnum {

    RATIO_SHARE,

    ONE_OFF_CREDITOR,

    EXTERNAL,

    FULL_TRANSFER;


    public static CashAssignerTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (CashAssignerTypeEnum memberType : CashAssignerTypeEnum.values()) {
            if (memberType.name().equals(code)) {
                return memberType;
            }
        }

        return null;
    }

}
