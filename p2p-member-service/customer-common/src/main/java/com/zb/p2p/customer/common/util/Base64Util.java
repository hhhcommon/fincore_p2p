package com.zb.p2p.customer.common.util;

import org.apache.commons.codec.binary.Base64;

import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;


/**
 * Base64工具类
 * @author zhuss
 * 2016年9月29日下午2:36:00
 */
public class Base64Util {

	/**
	 * 加密
	 * @param bytes
	 * @return
	 */
	public static String encrypt(byte[] bytes) {
		byte[] arr = Base64.encodeBase64(bytes, true);
		if(null == arr)
			throw new AppException(AppCodeEnum._1111_ERROR.getMessage());
		return new String(arr);
	}


	/**
	 * 解密
	 * @param encryptString
	 * @return
	 */
	public static byte[] decrypt(String encryptString) {
		byte[] arr = Base64.decodeBase64(encryptString.getBytes());
		if(null == arr)
			throw new AppException(AppCodeEnum._1111_ERROR.getMessage());
		return arr;
	}
	
	public static void main(String[] args) {
		String a = "124llkks";
		System.out.println(encrypt(a.getBytes()));
		System.out.println(new String(decrypt(encrypt(a.getBytes()))));
	}
}