package com.zb.p2p.service.order;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.common.exception.BusinessException;
import com.zb.p2p.service.callback.api.req.NotifyLoanStatusReq;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.facade.AssetForP2PFacade;
import com.zb.fincore.ams.facade.BorrowerInfoFacade;
import com.zb.fincore.common.exception.CommonException;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.fincore.pms.facade.product.ProductCacheServiceForP2PFacade;
import com.zb.p2p.GlobalVar;
import com.zb.p2p.dao.master.InterfaceRetryDAO;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.enums.InterfaceRetryStatusEnum;
import com.zb.p2p.enums.LoanPaymentStatusEnum;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.enums.SequenceEnum;
import com.zb.p2p.enums.SourceIdEnum;
import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.facade.api.req.OrderReq;
import com.zb.p2p.service.callback.MSDCallBackService;
import com.zb.p2p.service.callback.TXSCallBackService;
import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.service.common.DistributedSerialNoService;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.contract.ContractService;
import com.zb.p2p.service.message.MessageSender;
import com.zb.p2p.service.message.api.LoanWithdrawMessageInfo;
import com.zb.p2p.service.message.api.MessageInfo;
import com.zb.p2p.service.message.api.MessageReq;
import com.zb.p2p.service.message.api.MessageResp;
import com.zb.p2p.service.message.service.MessageService;
import com.zb.payment.msd.cashier.facade.TradeFacade;
import com.zb.payment.msd.cashier.facade.dto.req.LoanTransferInfoDTO;
import com.zb.payment.msd.cashier.facade.dto.req.LoanWithdrawalReqDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.CashierRspDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.TradeBaseRspDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by limingxin on 2017/11/22.
 */
@Service
@Slf4j
public class OrderAsyncService {
    @Autowired
    private TXSCallBackService txsCallBackService;
    @Autowired
    private MSDCallBackService msdCallBackService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private DistributedLockService distributedLockService;
    @Autowired
    private AssetForP2PFacade assetForP2PFacade;
    @Autowired
    private ProductCacheServiceForP2PFacade productCacheServiceForP2PFacade;
    @Autowired
    private TradeFacade tradeFacade;
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;
    @Autowired
    private InterfaceRetryService interfaceRetryService;
    @Autowired
    private BorrowerInfoFacade borrowerInfoFacade;
     
    @Value("${ext.gateway.enable:true}")
    private boolean enableExtGateWay;
    
    @Autowired
    private MatchRecordDAO matchRecordDAO;
    
    @Autowired
    private LoanRequestDAO loanRequestDAO;
    
    @Autowired
    private InterfaceRetryDAO interfaceRetryDAO;
    
    @Autowired
    private ContractService contractService;
    
    @Autowired
    private AccountService accountService ;
    
    @Autowired
    private LoanRequestDAO loanRequestDao;
    
    @Autowired
    private MessageService messageService;
    
     
    private ExecutorService executorService = new ThreadPoolExecutor(16, 32, 0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(2 << 16));

    @Transactional(rollbackFor = Exception.class)
    public void handlerEvent(final String loanNo) throws Exception {
    	
    	long startTime = System.currentTimeMillis();
    	
    	log.info("处理匹配后的逻辑       loanNo:" + loanNo );
    	
    	String lockKey = GlobalVar.GLOBAL_ASSETMATCHCOMPLETENOTIFY_LOCK_KEY ;
    	
		try {
			distributedLockService.tryLock(lockKey );
			
			//查询借款单
	    	final LoanRequestEntity loanRequestEntity = loanRequestDao.selectByLoanNo(loanNo);
	    	
	    	if("TRUE".equals( loanRequestEntity.getContractStatus())){
	    		return;
	    	}
	    	
	    	final List<MatchRecordEntity> matchRecordEntityList = matchRecordDAO.selectListByLoanNo(loanNo);
	    	
	    	accountService.updateAccountByLoanNo(loanNo, matchRecordEntityList); //更新持仓
	    	
	    	contractService.generateContract(  loanRequestEntity,  matchRecordEntityList);//生成合同
	    	
	    	loanWithdrawal(loanRequestEntity, matchRecordEntityList);//放款
			 
	    	long endTime = System.currentTimeMillis() - startTime;
	    	
	    	log.info("处理匹配后的逻辑       loanNo:" + loanNo + ",执行时间：" + endTime);
	    	
		}catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			 log.error("处理匹配后的逻辑异常       loanNo:" + loanNo , e ); 
			 throw e;
		} finally {
			try {
				distributedLockService.unLock(lockKey);
			} catch (Exception e) {
				 log.error("",e ); 
			}
		}
    	
    	 
    }
 

    private void notifyAsset(OrderReq orderReq, LoanRequestEntity loanRequest) {
        /*RelateAssetProductForP2PRequest relateAssetProductForP2PRequest = null;
        try {
            //设置借款表资产和产品的关系
            relateAssetProductForP2PRequest = new RelateAssetProductForP2PRequest();
            relateAssetProductForP2PRequest.setAssetCodes(Lists.newArrayList(orderReq.getAssetCode()));
            relateAssetProductForP2PRequest.setProductCode(orderReq.getProductCode());
            relateAssetProductForP2PRequest.setPoolCode(loanRequest.getAssetPoolCode());
            //通知资产方维护资产和产品关系
            log.info("调用ams接口saveAssetProductRelation，请求参数{}", JSON.toJSONString(relateAssetProductForP2PRequest));
            assetForP2PFacade.saveAssetProductRelation(relateAssetProductForP2PRequest);
        } catch (Exception e) {
            log.error("调用资产接口失败", e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.ASSET_RELATION_NOTIFY.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(relateAssetProductForP2PRequest));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
                log.error("", e1);
            }
        }*/
    }

    /**
     * 调用产品库存接口
     *
     * @param orderReq
     * @param type
     * @return
     */

    private void changeProductStock(OrderReq orderReq, Integer type) {
        /*ChangeProductStockForP2PRequest changeProductStockRequest = null;
        try {
            changeProductStockRequest = new ChangeProductStockForP2PRequest();
            changeProductStockRequest.setRefNo(orderReq.getReservationNo());
            changeProductStockRequest.setChangeAmount(orderReq.getMatchedAmount());
            changeProductStockRequest.setProductCode(orderReq.getProductCode());
            changeProductStockRequest.setChangeType(type);
            log.info("调用pms接口changeProductStockForP2P，请求参数{}", JSON.toJSONString(changeProductStockRequest));
            ChangeProductStockResponse changeProductStockResponse = productCacheServiceForP2PFacade.changeProductStockForP2P(changeProductStockRequest);
            log.info("调用pms接口changeProductStockForP2P，响应结果{}", JSON.toJSONString(changeProductStockResponse));
        } catch (Exception e) {
            log.error("调用产品占用库存接口失败", e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.PRODUCT_STOCK_NOTIFY.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(changeProductStockRequest));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
                log.error("", e1);
            }
        }*/
    }


    /**
     * 调用支付  放款
     * @param loanRequest
     * @param orderReq
     */
//    @Transactional
    private void loanWithdrawal(LoanRequestEntity loanRequest,List<MatchRecordEntity> matchRecordList) {
    	 
        //调用放款代付接口
        LoanWithdrawalReqDTO loanWithdrawalReqDTO = null;
        CashierRspDTO<TradeBaseRspDTO> cashierRspDTO = null;
        
        try {
        	//去重
        	if(LoanPaymentStatusEnum.LOAN_PAYMENT_PROCESSING.getCode().equals(loanRequest.getLoanPaymentStatus() )
        			|| LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode().equals(loanRequest.getLoanPaymentStatus() )){
        		return;
        	}

            //通知MSD放款状态为 "放款中"
            notifyMsdLoanPaymentProcessing(loanRequest);

        	BigDecimal loanRequestMatchedAmount = loanRequest.getMatchedAmount().setScale(2, BigDecimal.ROUND_DOWN);
        	BigDecimal matchedAmountTotal = new BigDecimal(0);
        	 
            loanWithdrawalReqDTO = new LoanWithdrawalReqDTO();
            loanWithdrawalReqDTO.setOrderNo(loanRequest.getLoanNo());// 
            loanWithdrawalReqDTO.setOrderTime(new Date());
            loanWithdrawalReqDTO.setMemberId(loanRequest.getMemberId());
            loanWithdrawalReqDTO.setBranchBankProvince(loanRequest.getBranchBankProvince());
            loanWithdrawalReqDTO.setBranchBankCity(loanRequest.getBranchBankCity());
            loanWithdrawalReqDTO.setBranchBankInst(loanRequest.getBranchBankInst());
            loanWithdrawalReqDTO.setBankName(loanRequest.getBankName());
            loanWithdrawalReqDTO.setBankAccountNo(loanRequest.getBankAccountNo());
            loanWithdrawalReqDTO.setMemberName(loanRequest.getMemberName());
            loanWithdrawalReqDTO.setTradeAmount( loanRequestMatchedAmount);
            loanWithdrawalReqDTO.setLoanOrderNo(loanRequest.getLoanNo());
            loanWithdrawalReqDTO.setTradeType("02" );//01-个人借款放款代付，02-企业借款人放款代付（2.3-限定02）
            loanWithdrawalReqDTO.setSourceId( SourceIdEnum.MSD.getCode());
              
            ArrayList<LoanTransferInfoDTO> loanTransferList = new ArrayList<>();
            for (MatchRecordEntity matchRecordEntity : matchRecordList) {
            	LoanTransferInfoDTO loanTransferInfoDTO = new LoanTransferInfoDTO();
            	loanTransferInfoDTO.setInvestOrderNo( matchRecordEntity.getOrderNo());
            	loanTransferInfoDTO.setLoanTransferAmount(matchRecordEntity.getMatchedAmount());
            	loanTransferInfoDTO.setMemberId(matchRecordEntity.getMemberId() );
            	
            	matchedAmountTotal = matchedAmountTotal.add(matchRecordEntity.getMatchedAmount());
            	loanTransferList.add(loanTransferInfoDTO);
			}
              
            loanWithdrawalReqDTO.setLoanTransferList(loanTransferList);
            loanWithdrawalReqDTO.setLoanTransferCount(loanTransferList.size());
            
            if(loanRequestMatchedAmount.compareTo(matchedAmountTotal) != 0){
            	String msStr = "【放款资金校验不通过】借款表和匹配表的匹配金额不相等   loanRequest.getLoanNo():" + loanRequest.getLoanNo();
            	log.error(msStr);
            	throw new CommonException( msStr );
            }
             
            log.info("支付网关处理开始,req={}", JSON.toJSONString(loanWithdrawalReqDTO));
            
            cashierRspDTO = tradeFacade.loanWithdrawal(loanWithdrawalReqDTO);
            
            log.info("支付网关处理结束,resp={}", JSON.toJSONString(cashierRspDTO));
            
            String loanPaymentStatus = "";
            
            if (cashierRspDTO == null || cashierRspDTO.getData() == null
                    || !(ResponseCodeEnum.RESPONSE_PARAM_PROCESSING.getCode().equals(cashierRspDTO.getCode()) && "P".equals(cashierRspDTO.getData().getTradeStatus()))) {
//                
            	loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode(); 
            } else { 
                //注意：这里最终结果在task中读取 
                loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_PROCESSING.getCode();
            }
            
            //更新放款状态
            Date loanPaymentTime = new Date();
            loanRequestDAO.updateLoanPaymentStatus(loanPaymentStatus, loanRequest.getId(), loanPaymentTime);
             
        }catch (CommonException e) {
			throw e;
		} catch (Exception e) {
            log.error("放款失败", e);
            
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.LOAN_NOTIFY_PAYMENT.getCode());
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            interfaceRetryEntity.setBusinessNo(loanRequest.getLoanNo());//放款以借款单为维度
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(loanWithdrawalReqDTO));
            interfaceRetryDAO.insertSelective(interfaceRetryEntity);
        }
    }
    
    /**
     * 调用支付  放款     重试接口
     * @param loanRequest
     * @param orderReq
     */
    public  void loanWithdrawalRetry(InterfaceRetryEntity interfaceRetryEntity ) {
    	
    	LoanWithdrawalReqDTO loanWithdrawalReqDTO = JSON.parseObject(interfaceRetryEntity.getRequestParam(), LoanWithdrawalReqDTO.class);
    	
    	LoanRequestEntity loanRequest = loanRequestDAO.selectByLoanNo( loanWithdrawalReqDTO.getLoanOrderNo());
    	
    	//去重
    	if(LoanPaymentStatusEnum.LOAN_PAYMENT_PROCESSING.getCode().equals(loanRequest.getLoanPaymentStatus() )
    			|| LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode().equals(loanRequest.getLoanPaymentStatus() )){
    		return;
    	}
           
    	log.info("支付网关处理开始,req={}", JSON.toJSONString(loanWithdrawalReqDTO));
            
            CashierRspDTO<TradeBaseRspDTO> cashierRspDTO = tradeFacade.loanWithdrawal(loanWithdrawalReqDTO);
            
            log.info("支付网关处理结束,resp={}", JSON.toJSONString(cashierRspDTO));
            
            String loanPaymentStatus = "";
            
            if (cashierRspDTO == null || cashierRspDTO.getData() == null
                    || !(ResponseCodeEnum.RESPONSE_PARAM_PROCESSING.getCode().equals(cashierRspDTO.getCode()) && "P".equals(cashierRspDTO.getData().getTradeStatus()))) {
            	loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode();
            } else { 
                //注意：这里最终结果在task中读取 
                loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_PROCESSING.getCode();
            }
            
            //更新放款状态
            Date loanPaymentTime = new Date();
            loanRequestDAO.updateLoanPaymentStatus(loanPaymentStatus, loanRequest.getId(), loanPaymentTime);
             
    }
    
    /**
     * 放款回调
     * @param resp
     * @throws Exception 
     */
    @Transactional
    public void loanWithdrawCallBack(NotifyTradeStatusReq resp) throws Exception{
    	

    	log.info( "放款回调开始resp.getOrderNo():" + resp.getOrderNo());
    	
    	LoanRequestEntity loanRequest = loanRequestDAO.selectByLoanNo(resp.getOrderNo() );
    	
    	//幂等
    	if(LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode().equals(loanRequest.getLoanPaymentStatus() )){
    		log.info( "放款回调重复resp.getOrderNo():" + resp.getOrderNo());
    		return;
    	}
    	
    	String tradeStatus = resp.getTradeStatus();
    	String tradeCode = resp.getTradeCode() ;
    	log.info("tradeCode:" + tradeCode + ",tradeStatus:" + tradeStatus);
        
        String loanPaymentStatus = "";
        
        if ("0000".equals( tradeCode) && "S".equals(tradeStatus)) {
        	loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode();
        	
        	try {
        		//发送短信
        		String[] timeArr = StringUtils.split( DateUtils.format(new Date(), "MM-dd-HH-mm") , "-");
            	String[] loanTimeArr = StringUtils.split( DateUtils.format(loanRequest.getLoanTime(), "yyyy-MM-dd") , "-");
            	String bankNo = loanRequest.getBankAccountNo();
            	
            	LoanWithdrawMessageInfo loanWithdrawMessageInfo = new LoanWithdrawMessageInfo();
            	
            	DecimalFormat decimalFormat = new DecimalFormat("###.00");
            	
            	loanWithdrawMessageInfo.setBankNo(bankNo.substring( bankNo.length() - 4 , bankNo.length()) );
            	loanWithdrawMessageInfo.setTotalAmount(decimalFormat.format( loanRequest.getMatchedAmount())   );
            	//loanWithdrawMessageInfo.setTotalAmount(NumberUtils.numberFormat(loanRequest.getMatchedAmount(), NumberUtils.DECIMAL_FORMAT_N2)  );
            	//loanWithdrawMessageInfo.setUserName(loanRequest.getMemberName());
            	loanWithdrawMessageInfo.setMonth(timeArr[0]);
            	loanWithdrawMessageInfo.setDay(timeArr[1]);
            	loanWithdrawMessageInfo.setHour(timeArr[2]); 
            	loanWithdrawMessageInfo.setMins(timeArr[3]);
            	loanWithdrawMessageInfo.setLoanDay(loanTimeArr[2]);
            	loanWithdrawMessageInfo.setLoanMonth(loanTimeArr[1]);
            	loanWithdrawMessageInfo.setLoanYear(loanTimeArr[0]);
            	
            	String mobile = loanRequest.getFinanceSubjectTel();
            	//String mobile = "15102106229";
            	
            	boolean isSendSuccess = messageService.send(GlobalVar.LOAN_WITHDRAW_MESSAGE_TEMPLATE_CODE, mobile, JSON.toJSONString(loanWithdrawMessageInfo ));
            	
            	if(isSendSuccess){
            		log.info("放款短信发送成功");
            	}else{
            		log.info("放款短信发送失败");
            	}
			} catch (Exception e) {
				log.error("放款短信发送异常",e);
			}
        	 
        	
        } else {//支付失败
        	loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode();
        }
        
      //更新放款状态
        Date loanPaymentTime = new Date();
        loanRequestDAO.updateLoanPaymentStatus(loanPaymentStatus, loanRequest.getId(), loanPaymentTime);

        //通知MSD放款状态
        notifyMSDLoanPaymentStatus(loanRequest, loanPaymentStatus, loanPaymentTime);
        log.info( "放款回调结束resp.getOrderNo():" + resp.getOrderNo());
    	
    }

    /**
     * 通知马上贷更新资产放款状态为“放款成功或放款失败”
     * @param loanRequestEntity
     */
   public void notifyMSDLoanPaymentStatus(LoanRequestEntity loanRequestEntity, String loanPaymentStatus, Date loanPaymentTime) {
        NotifyLoanStatusReq notifyLoanStatusReq = null;
        try {
            //生成流水号
            String loanPaymentNotifyStatusSerialNo = distributedSerialNoService.generatorSerialNoByIncrement(
                    SequenceEnum.PAYMENT_NOTIFY, loanRequestEntity.getSaleChannel(), 1);

            List<MatchRecordEntity> list = matchRecordDAO.selectListByLoanNo(loanRequestEntity.getLoanNo());
            List<Object> dataList = new ArrayList<>();
            for (MatchRecordEntity entity : list){
                dataList.add(entity.getCreditorNo());
            }

            //通知马上贷
            notifyLoanStatusReq = new NotifyLoanStatusReq();
            notifyLoanStatusReq.setTransactionNo(loanPaymentNotifyStatusSerialNo);
            notifyLoanStatusReq.setOrderNo(loanRequestEntity.getLoanNo());
            notifyLoanStatusReq.setNotifyType("1");//放款通知
            if(LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode().equals(loanPaymentStatus)){
                notifyLoanStatusReq.setStatus("2");
            }else if(LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode().equals(loanPaymentStatus)){
                notifyLoanStatusReq.setStatus("1");
            }else{
                notifyLoanStatusReq.setStatus(loanPaymentStatus);
            }
            notifyLoanStatusReq.setTradeAmount(String.valueOf(loanRequestEntity.getMatchedAmount()));
            notifyLoanStatusReq.setRequestTime(DateUtils.format(loanPaymentTime, DateUtils.DEFAULT_DATETIME_FORMAT));
            notifyLoanStatusReq.setData(dataList);
            log.debug("通知MSD放款状态请求参数：" + notifyLoanStatusReq);
            NotifyResp resp = msdCallBackService.notifyLoanStatus(notifyLoanStatusReq);
            log.debug("通知MSD放款状态响应参数：" + resp);
            // 判断远程URl调用是否成功
            if (null != resp && !"0000".equals(resp.getCode())) {
                throw new BusinessException(ResultCodeEnum.FAIL.code(), "通知MSD放款状态失败:" + resp.getMsg());
            }
        } catch (Exception e) {
            log.error("", e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.LOAN_SUCCESS_NOTIFY_MSD.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(notifyLoanStatusReq));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
                log.error("", e1);
            }
        }
    }

   /* protected void notifyTXS(OrderReq orderReq) {
        NotifyOrderReq notifyOrderReq = null;
        try {
            if (enableExtGateWay) {
                //通知唐小僧更新订单信息
                notifyOrderReq = new NotifyOrderReq();
                notifyOrderReq.setExtOrderNo(orderReq.getExtOrderNo());
//                notifyOrderReq.setExtReservationNo(orderReq.getExtReservationNo());
//                notifyOrderReq.setAmount(orderReq.getMatchedAmount());
                notifyOrderReq.setMemberId(orderReq.getMemberId());
                notifyOrderReq.setSaleChannel(SaleChannelEnum.ZD.getCode());
                txsCallBackService.callbackInvest(notifyOrderReq);
            }
        } catch (Exception e) {
            log.error("", e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.ACCOUNT_NOTIFY_TXS.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(notifyOrderReq));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
                log.error("", e1);
            }
        }
    }*/

    /**
     * 通知马上贷更新资产放款状态为“放款中”
     *
     * @param
     * @throws Exception
     */
    public void notifyMsdLoanPaymentProcessing(LoanRequestEntity loanRequestEntity) {
        NotifyLoanStatusReq notifyLoanStatusReq = null;
        try {
            //生成流水号
            String assetMatchNotifySerialNo = distributedSerialNoService.generatorSerialNoByIncrement(
                    SequenceEnum.PAYMENT_NOTIFY, loanRequestEntity.getSaleChannel(), 1);

            List<MatchRecordEntity> list = matchRecordDAO.selectListByLoanNo(loanRequestEntity.getLoanNo());
            List<Object> dataList = new ArrayList<>();
            for (MatchRecordEntity entity : list){
                dataList.add(entity.getCreditorNo());
            }

            //请求参数封装
            notifyLoanStatusReq = new NotifyLoanStatusReq();
            notifyLoanStatusReq.setTransactionNo(assetMatchNotifySerialNo);
            notifyLoanStatusReq.setOrderNo(loanRequestEntity.getLoanNo());
            notifyLoanStatusReq.setNotifyType("1");
            notifyLoanStatusReq.setStatus("3");//放款中
            notifyLoanStatusReq.setTradeAmount(String.valueOf(loanRequestEntity.getMatchedAmount()));
            notifyLoanStatusReq.setRequestTime(DateUtils.format(new Date(), DateUtils.DEFAULT_DATETIME_FORMAT));
            notifyLoanStatusReq.setData(dataList);
            log.debug("通知MSD放款处理中请求参数：" + notifyLoanStatusReq);
            NotifyResp resp = msdCallBackService.notifyLoanStatus(notifyLoanStatusReq);
            log.debug("通知MSD放款处理中响应参数：" + resp);
            // 判断远程URl调用是否成功
            if (null != resp && !"0000".equals(resp.getCode())) {
                throw new BusinessException(ResultCodeEnum.FAIL.code(), "通知MSD放款处理中失败:" + resp.getMsg());
            }
        } catch (Exception e) {
            log.error("", e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.LOAN_PAYMENT_PROCESS_NOTIFY_MSD.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(notifyLoanStatusReq));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
                log.error("", e1);
            }
        }
    }
}
