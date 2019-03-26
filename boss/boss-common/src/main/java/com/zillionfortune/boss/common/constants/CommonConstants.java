package com.zillionfortune.boss.common.constants;

public class CommonConstants {

    /**
     * 产品  查询来源 常量
     */
    public static final String PRODUCT_CONSTANT_QUERY_FROM_OPERATION = "Operation";
    /**
     * 产品  查询来源 常量
     */
    public static final String PRODUCT_CONSTANT_QUERY_FROM_T = "T";
	
	/** 操作员*/
	public static final String TRADE_OPERATE_SYSTEM = "system";
	
	public static final String ENDECRYPT_KEY="zillionfortune.operation";
	
	/**短信平台分配的接入系统编码*/
	public static final String SMS_SYS_CODE="10000";
	
	/**短信平台分配的短信编码*/
	public static final String SMS_CODE_AUTH_SUCC="10000_AUTH_SUCC";
	public static final String SMS_CODE_AUTH_FAIL="10000_AUTH_FAIL";

	
	public static final String tradeQueryRefundUrl= "http://192.168.0.74:8080/trading/refundRegularService/refundRegularQuery.html";
	
	public static final String tradeQueryRedeemUrl= "http://192.168.0.74:8080/trading/redeemRegularService/redeemRegularQuery.html";
	
	/**查询企业会员认证信息（分页）*/
	public static final String MEMBER_INFO_BY_PAGE_URL="http://192.168.0.41:8080/cif/enterpriseuserinfoqueryservice/queryuserauthinfobypage.html";
	/**企业认证信息审核*/
	public static final String MEMBER_INFO_AUDIT_URL="http://192.168.0.41:8080/cif/enterpriseuserauditservice/audit.html";
	/**查询企业会员列表（分页）*/
	public static final String MEMBER_USER_INFO_BY_PAGE_URL="http://192.168.0.41:8080/cif/enterpriseuserinfoqueryservice/queryinfobypage.html";
	
}
