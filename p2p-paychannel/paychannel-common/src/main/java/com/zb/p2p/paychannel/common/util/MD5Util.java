package com.zb.p2p.paychannel.common.util;

import com.zb.p2p.paychannel.common.enums.AppCodeEnum;
import com.zb.p2p.paychannel.common.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;


/**
 * MD5工具类
 * @author zhuss
 * 2016年9月29日下午3:58:42
 */
public class MD5Util {

	private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

	private static final String MD5 = "MD5";
	private static final char ZERO = '0';

	/**
	 * MD5加密 :32位加密
	 * @param text 不能为null
	 * @return
	 */
	public static String encrypt(String text) {
		try {
			if(StringUtils.isBlank(text)) throw new AppException(AppCodeEnum.RESPONSE_FAIL.getMessage());
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(text.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer();
			//buf.append("p2p");//增加前缀
			int len = b.length;
			for (int offset = 0; offset < len; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append(ZERO);
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toLowerCase();
		} catch (Exception e) {
			log.error("MD5加密失败 = {}", e);
			throw new AppException(AppCodeEnum.RESPONSE_FAIL);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(encrypt("123456"));
	}
}
