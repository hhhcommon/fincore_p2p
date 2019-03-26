/*
 * Copyright (C), 2002-2016
 * FileName: BeanConvertUtil.java
 * Author:   luwanchuan
 * Date:     2016年4月20日 上午11:27:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.zillionfortune.boss.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: OutOrderNoGenerateUtil <br/>
 * Function: 业务流水号生成器. <br/>
 * Date: 2017年6月27日 下午2:38:32 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class BussinessNoGenerateUtil {

	/**
	 * generateOutOrderNo:存管会员用外部流水号生成. <br/>
	 *
	 * @param name
	 * @return
	 */
	public static String generateOutOrderNo(String name) {
		SimpleDateFormat yyyyMMddHHmmsss = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return (name + yyyyMMddHHmmsss.format(new Date()));
	}
	
	
	public static void main(String[] args) {
		System.out.println(generateOutOrderNo("sendMSG"));
	}

}
