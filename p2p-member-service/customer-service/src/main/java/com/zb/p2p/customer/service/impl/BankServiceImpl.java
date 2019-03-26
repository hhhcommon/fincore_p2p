/**
 * 
 */
package com.zb.p2p.customer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.card.Card;
import com.zb.p2p.customer.common.util.HttpClientUtils;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.service.BankService;
import com.zb.p2p.customer.service.bo.PaymentResponse;


/**
 * @author guolitao
 *
 */
@CacheConfig(cacheNames = "banks")
@Service
public class BankServiceImpl implements BankService {

	private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);
	@Value("${env.paymentHost}")
	private String paymentHost;
	@Resource
    private RedisTemplate<String,Card> redisTemplate;
	/* (non-Javadoc)
	 * @see com.zb.p2p.customer.service.BankService#getCardByBankCode(java.lang.String,java.lang.String)
	 */
	@Override
//	@Cacheable(key ="#p0+'_'+#p2+'_'+#p1") 
	public Card getCardByBankCode(final String channelCode,final String bankCode,final String cardType) {
		List<Card> cardList = listCard(channelCode);
		if(cardList == null || cardList.isEmpty()){
			return null;
		}
		for(Card card : cardList){
			if(bankCode.equalsIgnoreCase(card.getBankCode()) && cardType.equalsIgnoreCase(card.getCardType())){
				return card;
			}
		}
		return null;
	}
	
	@Override
	public List<Card> listCard(final String channelCode) {
		String url = paymentHost + "/queryBankQuota";
		String json = "{\"sourceId\":\""+channelCode+"\"}";
		String response = null;
		try {
			logger.info("url:" + url + ",      json: " + json );
			response = HttpClientUtils.doPost(url, json);	
			logger.info("请求返回值：" + response);
		} catch (IOException e) {
			logger.error("请求"+url+"报错!参数为"+json,e);
			return null;
		}
		PaymentResponse pr = (PaymentResponse)JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
		List<Card> list = null;
		if(pr != null && pr.getData() != null){
			JSONArray ja = (JSONArray)pr.getData();
			list = new ArrayList<>(ja.size());
			JSONObject job = null;
			Card card = null;
			for(int i = 0; i < ja.size();i++){
				job = ja.getJSONObject(i);
			    card = new Card();
			    card.setBankCode(job.getString("bankCode"));
			    card.setBankName(job.getString("bankName"));
			    card.setCardType(job.getString("cardType"));
			    card.setPayMax(job.getString("payMax"));
			    card.setPayDayMax(job.getString("payDayMax"));
			    list.add(card);
			}				
		}
		return list;
	}

	public static void main(String[] args) {
		//假数据：
		String response = "{\"code\": \"0000\",\"msg\": \"处理成功\",\"data\":"
				+ " [{\"bankCode\":\"ICBC\",\"bankName\":\"工商银行\",\"cardType\":\"D\",\"payMax\":\"50000\",\"payDayMax\":\"5000\"}]}";
		PaymentResponse pr = (PaymentResponse)JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
		System.out.println(pr.getData());
		if(pr != null && pr.getData() != null){
			JSONArray ja = (JSONArray)pr.getData();
			List<Card> list = new ArrayList<>(ja.size());
			JSONObject job = null;
			Card card = null;
			for(int i = 0; i < ja.size();i++){
				job = ja.getJSONObject(i);
			    card = new Card();
			    card.setBankCode(job.getString("bankCode"));
			    card.setBankName(job.getString("bankName"));
			    card.setCardType(job.getString("cardType"));
			    card.setPayMax(job.getString("payMax"));
			    card.setPayDayMax(job.getString("payDayMax"));
			    list.add(card);
			}
			logger.info(list.get(0).getPayMax());
			
		}
	}
}
