/**
 * 
 */
package com.zb.p2p.customer.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zb.qjs.common.client.SMSClient;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.service.SmsService;

/**
 * @author guolitao
 *
 */
@Service
public class SmsServiceImpl implements SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	private static final String regExp = "^1[34578]\\d{9}$";
	private static final Pattern p = Pattern.compile(regExp);  

	
	/* (non-Javadoc)
	 * @see com.zb.p2p.customer.service.SmsService#sendSms(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendSms(String mobile, String content) {
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(content)){
			logger.warn("【短信服务】手机号和发送内容均不能为空");
			return false;
		}else if(!isValidMobile(mobile)){
			logger.warn("【短信服务】手机号不合法");
			return false;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("【短信服务】开始向").append(mobile).append("发送短信，短信内容为").append(content);
		try{
			int ret = SMSClient.sendNotifyMsg(mobile, content);
			boolean flag = ret == 1 ? true : false;
			sb.append(flag ? "，发送成功" : "，发送失败");
			logger.info(sb.toString());
			return flag;
		}catch(Exception e){
			sb.append("，发送发生异常！");
			logger.error(sb.toString(),e);
			return false;
		}
	}
	
	/**
	 * 校验手机号
	 * @param mobile
	 * @return
	 */
	@Override
	public boolean isValidMobile(String mobile){
	    Matcher m = p.matcher(mobile);
	    return m.matches();
	}

}
