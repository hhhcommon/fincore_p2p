package com.zb.p2p.customer.common.util;

public class BusinessUtil {
	/**
	 * 转换成开放给浏览器的会员id
	 * @param customerId
	 * @return
	 */
	public static String convertToOpenCustomerId(String customerId){
		String ret = MD5Util.encrypt(customerId);
		return ret;
	}
}
