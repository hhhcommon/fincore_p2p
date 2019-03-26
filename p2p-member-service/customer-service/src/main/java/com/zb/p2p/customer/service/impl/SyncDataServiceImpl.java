package com.zb.p2p.customer.service.impl;

import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.customer.client.domain.*;
import com.zb.p2p.customer.client.txs.TxsClient;
import com.zb.p2p.customer.common.constant.AppConstant;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.dao.CustomerBindcardMapper;
import com.zb.p2p.customer.dao.CustomerInfoMapper;
import com.zb.p2p.customer.dao.OrgCustomerInfoMapper;
import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.OrgCustomerInfo;
import com.zb.p2p.customer.service.SyncDataService;
import com.zb.p2p.customer.service.convert.CustomerInfoConvert;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SyncDataServiceImpl implements SyncDataService{
	
	private static final Logger logger = LoggerFactory.getLogger(SyncDataServiceImpl.class);
	 
	@Autowired
	TxsClient txsClient;
	
	@Autowired
	CustomerInfoMapper customerInfoMapper;
    @Autowired
    OrgCustomerInfoMapper orgCustomerInfoMapper;
	@Autowired
	CustomerBindcardMapper customerBindcardMapper;
	 
	public void syncAccountInfo(String lastAccountId,String pageSize) {
		
		SyncAccountInfoReq syncAccountInfoReq = new SyncAccountInfoReq();
		syncAccountInfoReq.setLastAccountId(lastAccountId);
		syncAccountInfoReq.setPageSize(Integer.parseInt(pageSize));
		 
		while(true){
			TxsResponse txsResponse  = txsClient.syncAccountInfo(syncAccountInfoReq);
			
			 if("0000".equals(txsResponse.getCode())){ 
				    //logger.info("----------------------------:" + JsonUtil.printStrFromObj(txsResponse ) );
					TxsResponse txsResponse2 = (TxsResponse)JsonUtil.getObjectByJsonStr(JsonUtil.printStrFromObj(txsResponse ) ,TxsResponse.class);
					List<SyncAccountInfoResp> syncAccountInfoRespList = (List<SyncAccountInfoResp>)JsonUtil.convListByJsonStr(((com.alibaba.fastjson.JSONArray)txsResponse2.getData()).toJSONString(),SyncAccountInfoResp.class);
					
					ArrayList<CustomerInfo> customerInfoList = new ArrayList<>();
					String lastid = "";
					if(syncAccountInfoRespList != null && syncAccountInfoRespList.size() > 0){
						for (SyncAccountInfoResp syncAccountInfoResp : syncAccountInfoRespList) {
							
							lastid = syncAccountInfoResp.getAccountId(); 
							
							CustomerInfo customerInfo = new CustomerInfo();
							customerInfo.setChannelCode("TXS");
							customerInfo.setChannelCustomerId(String.valueOf(syncAccountInfoResp.getAccountId() ) );
							customerInfo.setIdCardNo( syncAccountInfoResp.getIdentityNo());
							customerInfo.setCreateTime(new Date());
							customerInfo.setCustomerId(Long.parseLong(syncAccountInfoResp.getAccountId() ) );
							
//							if(StringUtils.isEmpty(syncAccountInfoResp.getThirdPartId())){
//								continue;
//							}else{
//								customerInfo.setCustomerId(Long.parseLong(syncAccountInfoResp.getThirdPartId()));
//							}
							
							customerInfo.setIdCardType("01" );
							if(StringUtils.isNotEmpty(syncAccountInfoResp.getIdentityNo() )){
								customerInfo.setIsReal(1);
							}
							customerInfo.setMobile( syncAccountInfoResp.getMobile());
							customerInfo.setRealName( syncAccountInfoResp.getIdentityName());
							customerInfo.setRegisterTime( syncAccountInfoResp.getCreated());
							customerInfo.setAccountNo( syncAccountInfoResp.getPayUserId());
							 
							customerInfoList.add(customerInfo); 
						} 
						
						int insertCount = 0;
						if(customerInfoList.size() > 0){
							insertCount = customerInfoMapper.insertList(customerInfoList);
							logger.info("本次插入用户 insertCount：" + insertCount + "条，lastAccountId:" + syncAccountInfoReq.getLastAccountId() + ", pageSize:" + pageSize); 
						}
						
						syncAccountInfoReq.setLastAccountId(lastid); 
					}else {
						logger.info("查TXS用户信息   为空    lastAccountId:" + syncAccountInfoReq.getLastAccountId() + ", pageSize:" + pageSize);
						break;
					}
					  
			  }else{
				  logger.info("查TXS用户信息失败    lastAccountId:" + syncAccountInfoReq.getLastAccountId() + ", pageSize:" + pageSize);
				  break;
			  }
		}
		  
		  
	}
	
	public void syncCards(String lastId,String pageSize) { 
		SyncCardsReq syncCardsReq = new SyncCardsReq();
		syncCardsReq.setLastCardId(lastId);
		syncCardsReq.setPageSize(Integer.parseInt(pageSize));
		 
		while(true){
			TxsResponse txsResponse  = txsClient.syncCards(syncCardsReq);
			
			 if("0000".equals(txsResponse.getCode())){ 
					TxsResponse txsResponse2 = (TxsResponse)JsonUtil.getObjectByJsonStr(JsonUtil.printStrFromObj(txsResponse ) ,TxsResponse.class);
					List<SyncCardsResp> respList = (List<SyncCardsResp>)JsonUtil.convListByJsonStr(((com.alibaba.fastjson.JSONArray)txsResponse2.getData()).toJSONString(),SyncCardsResp.class);
					
					ArrayList<CustomerBindcard> cardList = new ArrayList<>();
					String lastid = "";
					if(respList != null && respList.size() > 0){
						for (SyncCardsResp syncCardsResp : respList) {
							 
							CustomerBindcard customerBindcard = new CustomerBindcard();
							customerBindcard.setBindId(Long.parseLong(syncCardsResp.getCardId() ) );
	                        customerBindcard.setCustomerId(Long.parseLong(syncCardsResp.getAccountId()));//客户ID
	                        customerBindcard.setBankCardNo(syncCardsResp.getCode());//银行卡号
	                        customerBindcard.setBankCode(syncCardsResp.getBankCode());//银行简称
	                        customerBindcard.setBankName(syncCardsResp.getBankName());//银行名称
	                        customerBindcard.setCardType("D");//卡类型D：借记卡，C：贷记卡
	                        customerBindcard.setStatus(CustomerConstants.BIND_STAUS_1);//状态[0：预绑卡、1：启用 2:解绑]
	                        customerBindcard.setIdCardType("01");//证件类型 01:身份证
	                        customerBindcard.setIdCardNo(syncCardsResp.getIdentityNo());//证件号
	                        customerBindcard.setIdCardName(syncCardsResp.getIdentityName());//证件姓名
	                        customerBindcard.setMobile(syncCardsResp.getPhoneNo());//银行绑定手机号
	                        customerBindcard.setSignId(syncCardsResp.getExtId());//签约协议号
	                        
	                        cardList.add(customerBindcard); 
							
							lastid =  syncCardsResp.getCardId(); 
						}
						
						int insertCount = customerBindcardMapper.insertList(cardList);
						logger.info("本次插入绑卡 insertCount：" + insertCount + "条，lastid:" + syncCardsReq.getLastCardId() + ", pageSize:" + pageSize); 
						
						if(insertCount <= 0){
							break;
						}
						syncCardsReq.setLastCardId( lastid + ""); 
					}else {
						logger.info("查TXS绑卡信息   为空    lastid:" + syncCardsReq.getLastCardId() + ", pageSize:" + pageSize);
						break;
					}
					  
			  }else{
				  logger.info("查TXS绑卡信息失败    lastid:" + syncCardsReq.getLastCardId() + ", pageSize:" + pageSize);
				  break;
			  }
		}
	}
	
	public void updateHistoryIsBindCard() {
		int count = customerInfoMapper.updateHistoryIsBindCard();
		
		logger.info("更新条数  updateHistoryIsBindCard:" + count);
	}

    @Override
    public void syncCorpMemberInfo(SyncCorpInfoReq req) {

        // 最大只支持200
        TxsSyncResponse<SyncCorpInfoRes> txsResponse  = txsClient.syncCorpInfo(req);

        if (txsResponse != null && AppConstant.RESP_CODE_SUCCESS.equals(txsResponse.getCode())) {
            List<SyncCorpInfoRes> corpInfos = txsResponse.getData();
            // 无数据时返回
            if (CollectionUtils.isNullOrEmpty(corpInfos)) {
            	throw new AppException("没有数据可同步");
			}
            List<OrgCustomerInfo> orgCustomerInfos = new ArrayList<>();
            // 循环获取同步数据
            for (SyncCorpInfoRes corpInfoRes : corpInfos) {

                OrgCustomerInfo orgInfo = CustomerInfoConvert.convertSyncCorpInfo2CustomerInfo(corpInfoRes);
                // Add
                orgCustomerInfos.add(orgInfo);
            }
            // 落库
            int batchSize = orgCustomerInfoMapper.batchInsert(orgCustomerInfos);
            Assert.isTrue(batchSize == orgCustomerInfos.size(), "插入数据数量[{}]和同步数据数量[{}]不一致");
            logger.info("Sync CorpMember data is success");
            // End
        }else {
            throw new AppException("同步查询用户数据失败，请稍后再试");
        }
    }
	
	public static void main(String[] a){
//		String temp = "[{\"memberId\":1231,\"identityType\":\"IDCARD\",\"identityNo\":\"430722199303297639\",\"identityName\":\"彭磊\",\"accountId\":918368536382930944,\"sourceId\":\"MSDJK\",\"mobile\":\"17080851603\",\"invitedBy\":null,\"invitedType\":null,\"referralCode\":\"0\",\"status\":\"NORMAL\",\"thirdPartId\":null,\"created\":\"2017-10-20T09:01:05.000+0000\"},{\"memberId\":1232,\"identityType\":\"IDCARD\",\"identityNo\":\"421124199005036536\",\"identityName\":\"肖行\",\"accountId\":12314,\"sourceId\":\"MSDJK\",\"mobile\":\"18512105871\",\"invitedBy\":null,\"invitedType\":null,\"referralCode\":\"0\",\"status\":\"NORMAL\",\"thirdPartId\":null,\"created\":\"2017-10-20T09:01:05.000+0000\"}]";
		String temp = "{\"code\":\"0000\",\"msg\":\"操作成功\",\"data\":[{\"memberId\":1233,\"identityType\":\"IDCARD\",\"identityNo\":\"32132219901007585X\",\"identityName\":\"陶刘成\",\"accountId\":12315,\"sourceId\":\"MSDJK\",\"mobile\":\"13681975404\",\"invitedBy\":null,\"invitedType\":null,\"referralCode\":\"0\",\"status\":\"NORMAL\",\"thirdPartId\":null,\"created\":\"2017-10-20T09:01:05.000+0000\"},{\"memberId\":918368536420679680,\"identityType\":\"IDCARD\",\"identityNo\":\"510823199304262412\",\"identityName\":\"王昌勇\",\"accountId\":918368536382930944,\"sourceId\":\"MSDJK\",\"mobile\":\"18512161231\",\"invitedBy\":null,\"invitedType\":null,\"referralCode\":\"0\",\"status\":\"NORMAL\",\"thirdPartId\":\"\",\"created\":\"2017-10-12T06:51:36.000+0000\"}]}";
//		String temp = "[{\"identityType\":\"IDCARD\",\"identityNo\":\"430722199303297639\",\"identityName\":\"彭磊\",\"accountId\":918368536382930944,\"mobile\":\"17080851603\",\"created\":\"2017-10-20T09:01:05.000+0000\"}]";
//		TxsResponse txsResponse2 = (TxsResponse)JsonUtil.getObjectByJsonStr(temp ,TxsResponse.class);
//		List<SyncAccountInfoResp> syncAccountInfoRespList = (List<SyncAccountInfoResp>)JsonUtil.convListByJsonStr(((com.alibaba.fastjson.JSONArray)txsResponse2.getData()).toJSONString(),SyncAccountInfoResp.class);
//		for (SyncAccountInfoResp syncAccountInfoResp : syncAccountInfoRespList) {
//			
//			System.out.println(syncAccountInfoResp.getAccountId());
//			System.out.println(String.valueOf(syncAccountInfoResp.getAccountId()));
//			
//		}
		SyncAccountInfoResp syncAccountInfoResp = new SyncAccountInfoResp();
		 
		System.out.println( JsonUtil.printStrFromObj(syncAccountInfoResp )); 
		
//		long b = Long.parseLong("918368536382930944" );
//		System.out.println(String.valueOf(b));
		
	}
 
}
