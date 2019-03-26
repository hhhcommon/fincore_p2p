package com.zb.p2p.customer.common.util;

/**
 * 会员常量
 * Created by laoguoliang on 2017/10/12 0012.
 */
public interface CustomerConstants {
    /**customerId*/
    String CUSTOMERID = "customerId";

    Integer ZERO = 0;
    Integer ONE = 1;
    Integer TWO = 2;
    Integer THREE = 3;
    
    /**是否实名*/
    /**是否实名 0-否*/
    Integer IS_REAL_0 = 0;
    /**是否实名 1-是*/
    Integer IS_REAL_1 = 1;

    /**是否已绑卡*/
    /**是否已绑卡 0-否*/
    Integer IS_BIND_CARD_0 = 0;
    /**是否已绑卡 1-是*/
    Integer IS_BIND_CARD_1 = 1;

    /**是否已风险评测*/
    /**是否已风险评测 0-否*/
    Integer IS_RISK_RATE_0 = 0;
    /**是否已风险评测 1-是*/
    Integer IS_RISK_RATE_1 = 1;
    
    /**状态[0：预绑卡、1：启用 2:解绑]*/
    /**0：预绑卡*/
    Integer BIND_STAUS_0 = 0;
    /**1：启用*/
    Integer BIND_STAUS_1 = 1;
    /**2:解绑*/
    Integer BIND_STAUS_2 = 2;
    
    /**金融核心Payment系统 */
    /**请求金核用sourceId*/
    String PAYMENT_SOURCEID_P2P = "P2P";
    /**请求金核用sourceId*/
    String PAYMENT_SOURCEID_MSD = "MSD";
    String PAYMENT_SOURCEID_QJS_YST = "QJS_YST";
    
    
    /**会员类型(10-个人)*/
    String PAYMENT_MEMBER_TYPE_10 = "10";
    /**会员类型(20-机构)*/
    String PAYMENT_MEMBER_TYPE_20 = "20";
    /**账户类型：VIR-虚拟账户,ELC-电子账户，logic-逻辑账户*/
    String PAYMENT_ACCOUNT_TYPE_LOGIC = "logic";

    /**会员ID加锁超时时间:30分钟*/
    long LOCK_CUSTOMER_TIMEOUT = 1800L;
    String LOCK_BIND_CARD = "LOCK_BIND_CARD";
}
