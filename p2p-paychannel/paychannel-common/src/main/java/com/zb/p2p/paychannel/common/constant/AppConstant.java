package com.zb.p2p.paychannel.common.constant;

public class AppConstant {

    //全局锁标识
    public final static String GLOBAL_MATCH_LOCK_KEY = "matchLock";
    public final static String GLOBAL_RESERVATION_LOCK_KEY = "reservationLock";

    public final static String SYS_CONSTANT_PRODUCT_SOURCE_02 = "02";//产品来源,用于区分二期的产品还是三期的产品（02：定期即二期产品，05：N日循环计划,对应PMS pattern_code）
    public final static String SYS_CONSTANT_PRODUCT_SOURCE_05 = "05";//产品来源,用于区分二期的产品还是三期的产品（02：定期即二期产品，05：N日循环计划,对应PMS pattern_code）

    public final static String SYS_CONSTANT_VALUE_0 = "0";//常量0
    public final static String SYS_CONSTANT_VALUE_1 = "1";//常量1

    public final static String ORDER_MATCH_TIME_OUT_REFUND = "ORDER_MATCH_TIME_OUT_REFUND";

}
