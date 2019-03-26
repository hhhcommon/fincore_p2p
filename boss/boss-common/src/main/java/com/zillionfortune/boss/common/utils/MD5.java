/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: MD5 <br/>
 * Function: MD5操作. <br/>
 * Date: 2016年12月20日 下午1:58:09 <br/>
 *
 * @author zb@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class MD5 {

    private static Logger LOGGER = LoggerFactory.getLogger(MD5.class);

    /**
     * 使用ThreadLocal以空间换时间解决SimpleDateFormat线程安全问题
     */
    @SuppressWarnings("rawtypes")
    private static ThreadLocal threadLocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            MessageDigest messagedigest = null;
            try {
                messagedigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("初始化失败，MessageDigest不支持MD5Util", e);
            }
            return messagedigest;
        }
    };

    public static MessageDigest getMessageDigest() {
        return (MessageDigest) threadLocal.get();
    }

    public static String digest(String s, String charset) throws UnsupportedEncodingException {
        getMessageDigest().update(s.getBytes(charset));
        return HexUtil.bytes2Hexstr(getMessageDigest().digest());
    }
    
    
    public static void main(String[] args) {
		try {
			System.out.println(MD5.digest("admin", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
