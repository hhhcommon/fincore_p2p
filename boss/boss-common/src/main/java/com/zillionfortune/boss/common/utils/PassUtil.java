package com.zillionfortune.boss.common.utils;

import java.io.UnsupportedEncodingException;

public class PassUtil {
	
	/**
	 * 密码加密
	 * @param pass
	 * @return
	 */
	public static String getPassword(String pass){
		/*Endecrypt test = new Endecrypt();
		String reValue = test.get3DESEncrypt(pass, CommonConstants.ENDECRYPT_KEY);
		return reValue;*/
		String reValue = null;
		try {
			reValue = MD5.digest(pass.trim(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reValue;
	}
}
