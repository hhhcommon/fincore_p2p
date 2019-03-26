package com.zb.p2p.customer.common.util;

import java.math.BigDecimal;

public class MoneyUtil {

	/**
	 * 转换为标准金额输出格式(向下取整)
	 * @param val
	 * @return
	 */
	public static String convertToStandardMoneyStr(BigDecimal val) {
		String ret = null;
        BigDecimal roundVal = val.setScale(2,BigDecimal.ROUND_DOWN);
		ret = roundVal.toString();
		
		return ret;
	}
	
}
