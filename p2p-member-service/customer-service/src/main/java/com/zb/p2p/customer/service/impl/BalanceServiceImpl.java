/**
 * 
 */
package com.zb.p2p.customer.service.impl;

import java.io.IOException;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.currency.CustomerAcountDetail;
import com.zb.p2p.customer.api.entity.currency.CustomerEaccountBalance;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.HttpClientUtils;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.service.BalanceService;
import com.zb.p2p.customer.service.InfoService;
import com.zb.p2p.customer.service.bo.PaymentResponse;

/**
 * @author guolitao
 *
 */
@Service
public class BalanceServiceImpl implements BalanceService {

	private static final Logger logger = LoggerFactory.getLogger(BalanceServiceImpl.class);
	@Value("${env.paymentHost}")
	private String paymentHost;
	/*@Value("${env.isBalanceOpen}")
	private Integer isBalanceOpen;*/
	@Resource
	private InfoService infoService;
	/* (non-Javadoc)
	 * @see com.zb.p2p.customer.service.BalanceService#queryCustomerBalance(java.lang.Long)
	 */
	@Override
	public CustomerEaccountBalance queryCustomerBalance(Long customerId) {
		CustomerDetail cd = infoService.getPerDetail(customerId);
		if(cd == null || cd.getIsActiveEAccount() != 1){
			throw new AppException(AppCodeEnum._A303_ACCOUNT_INACTIVE);
		}
		String signId = cd.getBankCard()!= null ? cd.getBankCard().getSignId() : "";
		if(StringUtils.isBlank(signId)){
			throw new AppException(AppCodeEnum._A101_USER_NOT_BIND);
		}
		String url = paymentHost + "/queryEBalanceYST";
		String json = "{\"sourceId\":\"QJS_YST\",\"accountPurpose\":\"current\",\"memberId\":\""+customerId+"\",\"signId\":\""+signId+"\"}";
		String response = null;
		try {
			logger.info("请求"+url+"参数 为"+json);
			response = HttpClientUtils.doPost(url, json);
			logger.info("响应为 为"+json);
		} catch (IOException e) {
			logger.error("请求"+url+"报错!参数为"+json,e);
			return null;
		}
		PaymentResponse pr = (PaymentResponse)JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
		if(pr != null){
			if(!AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())){
				AppException ae = new AppException(pr.getMsg());
				ae.setCode(pr.getCode());
				ae.setMessage("3T05".equals(pr.getCode()) ? "交易处理中，请稍后重试":pr.getMsg());
				throw ae;
			}
			if(pr.getData() != null){
				JSONObject data = (JSONObject)pr.getData();
				BigDecimal totalBalance = data.getBigDecimal("totalBalance");
				BigDecimal preDayIncome = data.getBigDecimal("predayIncome");
				BigDecimal totalIncome = data.getBigDecimal("totalIncome");
				CustomerEaccountBalance cb = new CustomerEaccountBalance();
				if(totalBalance != null){
					cb.setTotalBalance(totalBalance.toString());
				}
				if(preDayIncome != null){
					cb.setPredayIncome(preDayIncome.toString());
				}
				if(totalIncome != null){
					cb.setTotalIncome(totalIncome.toString());
				}
				return cb;
			}
		}
		
		return null;
	}
	@Override
	public CustomerAcountDetail queryCustomerAcountDetail(Long customerId) {
		//查询详情
		CustomerDetail cd = infoService.getPerDetail(customerId);
		if(cd == null){
			throw new AppException(AppCodeEnum._A001_USER_NOT_EXISTS);
		}
		CustomerAcountDetail cad = new CustomerAcountDetail();
		BeanUtils.copyProperties(cd, cad);
		//查询活期余额
		if(cd.getIsActiveEAccount() == 1){
			CustomerEaccountBalance cb = queryCustomerBalance(customerId);
			cad.seteBalance(cb);
		}
		//查询账户余额
		BigDecimal balanceAmt = this.infoService.queryBanlanceByCustomerId(customerId);
		if(balanceAmt != null){
			cad.setAccountAmount(String.valueOf(balanceAmt));
		}
		return cad;
	}
	/*@Override
	public Integer queryBalanceFlag() {
		
		return isBalanceOpen;
	}*/

}
