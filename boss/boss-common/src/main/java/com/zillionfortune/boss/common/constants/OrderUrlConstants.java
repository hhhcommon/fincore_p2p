package com.zillionfortune.boss.common.constants;

/**
 * ClassName: OrderUrlConstants <br/>
 * Function: TODO 接口URL工具类. <br/>
 * Date: 2016年12月28日 下午3:36:37 <br/>
 *
 * @author mabiao@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class OrderUrlConstants {
	
	/** 操作员*/
	public static final String TRADE_OPERATE_SYSTEM = "system";
	
	public static final String ENDECRYPT_KEY="zillionfortune.operation";
	
	/**
	 * 业务单查询接口连接
	 */
	public final static String queryBusinessOrderList = "http://192.168.0.74:8080"
			+ "/order/order/queryBusinessOrderList.html";
	
	/**
	 * 批次列表查询接口连接
	 */
	public final static String querySerialBatchList = "http://192.168.0.74:8080"
			+ "/order/bankSerial/querySerialBatchList.html";
	
	/**
	 * 银行流水列表查询接口连接
	 */
	public final static String queryBankSerialList = "http://192.168.0.74:8080"
			+ "/order/bankSerial/queryBankSerialList.html";
	
	/**
	 * 银行流水列表查询接口连接
	 */
	public final static String updateBankSerial = "http://192.168.0.74:8080"
			+ "/order/bankSerial/updateBankSerial.html";
	
	/**
	 * 银行流水列表查询接口连接
	 */
	public final static String uploadBankSerial = "http://192.168.0.74:8080"
			+ "/order/fileOperate/uploadBankSerial.html";
	

}
