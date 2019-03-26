package com.zb.p2p.customer.service.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.dao.CustomerInfoMapper;
import com.zb.p2p.customer.dao.NotifyMapper;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.NotifyInfo;
import com.zb.p2p.customer.service.bo.UserInfoRequest;
//import com.zb.p2p.customer.service.bo.UserInfoResponse;
import com.zb.p2p.customer.service.rpc.OpenApiClient;

/**
 * 目前用mq同步
 * @author tangqingqing
 *
 */
//@Component
public class TxsUserInfoSyn {
	/*private static final Logger logger = LoggerFactory.getLogger(TxsUserInfoSyn.class);
	@Autowired
	OpenApiClient openApiClient;
	@Autowired
	CustomerInfoMapper customerInfoMapper;
	@Autowired
	NotifyMapper notifyMapper;
	@Value("${env.delayTime}")
    private Long delayTime;
	
	@Scheduled(cron="${env.synTxsInfoTime}")
	public void synUserInfo(){
		logger.info("==同步 用户信息定时器启动==");
		Long createTimeBefore=System.currentTimeMillis()-(delayTime*1000);
		Date date=new Date(createTimeBefore);
		logger.info("==同步 用户信息定时器启动=="+date);
		NotifyInfo n=new NotifyInfo();
		n.setCreateTime(date);
		List<NotifyInfo> list=notifyMapper.selectNotifyInfo(n);
		if(list==null||list.size()<=0){
			logger.info("木有需要同步的信息");
		}else{
			for(NotifyInfo info:list){ 
				int a = pushToTxs(info.getNotifyKey());
				logger.info("同步信息结果为："+a+"===txsId="+info.getNotifyKey()+"=cusId=="+info.getCustomerId());
			}
		}
	}
	
	public int pushToTxs(String txsAccountId){
		 UserInfoRequest backRequest=new UserInfoRequest();
		 RegisteredBackReq registeredBackReq=new RegisteredBackReq();
		 CustomerInfo newCusInfo=customerInfoMapper.selectByAccountId(txsAccountId,"TXS");//查询信息 用于判断是否绑卡 是否注册等信息
		 registeredBackReq.setP2pAccountId(String.valueOf(newCusInfo.getCustomerId()));
		 registeredBackReq.setMobile(newCusInfo.getMobile());
		 registeredBackReq.setOperationTime(DateUtil.format(newCusInfo.getRegisterTime(), "yyyy-MM-dd HH:mm:ss"));
		 registeredBackReq.setRequestTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 registeredBackReq.setTxsAccountId(txsAccountId);
		 String registeredBackReqStr= JsonUtil.printStrFromObj(registeredBackReq);
		 backRequest.setClientNo("TXS");
		 backRequest.setTxnCode("B0111");
		 backRequest.setContent(registeredBackReqStr);
		 
		 logger.info("开始回调txsid:"+txsAccountId);
		 UserInfoResponse registeredBackResp= openApiClient.dispatch(backRequest);
	      logger.info("===========registeredBackResp回调txs============="+registeredBackResp.getRespMsg());
	      
	  	if ("0000".equals(registeredBackResp.getRespCode())) {
			logger.info("回调成功TXN TXNCODE:B0111");
			
			RegisteredBackReq content=(RegisteredBackReq) JsonUtil.getObjectByJsonStr(backRequest.getContent(), RegisteredBackReq.class);
			logger.info("获取回调信息：：：:"+backRequest.getContent());
			NotifyInfo n=new NotifyInfo();
			n.setNotifyResult(1);
			n.setCustomerId(Long.parseLong(content.getP2pAccountId()));
			n.setUpdateTime(new Date());
			
			logger.info("回调成功TXN 开始更新 回调状态 QJScusId:"+content.getP2pAccountId());
			int a=notifyMapper.updateBycustomerId(n);
			logger.info("回调成功TXN 开始更新 回调状态结果："+a);
			
			return 1;
		} else {
			logger.info("回调失败TXN TXNCODE:B0111");
			return 0;
		}
		 
		 
	}*/
	
	
	


}
