package com.zb.p2p.tradeprocess.web.controller;

import java.math.BigDecimal;
import java.text.Bidi;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zb.p2p.enums.LoanPaymentStatusEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.p2p.GlobalVar;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.product.ProductDTO;
import com.zb.p2p.facade.service.internal.ProductInternalService;
import com.zb.p2p.service.callback.DubboCallBackService;
import com.zb.p2p.service.incomecalc.IncomeCalcService;
import com.zb.p2p.service.message.api.LoanWithdrawMessageInfo;
import com.zb.p2p.service.message.api.MessageResp;
import com.zb.p2p.service.message.service.MessageService;
import com.zb.p2p.service.order.OrderAsyncService;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常业务处理(人工调用)
 * @author tangqingqing
 *
 */
@RestController
@RequestMapping("/exceptionBusiness/")
@Slf4j
public class CompensateForExceptionBusinessController {
    
	@Autowired
    private OrderAsyncService orderAsyncService;
    
	@Autowired
    private LoanRequestDAO loanRequestDAO;
    
    @Autowired
    private ProductServiceForP2PFacade productServiceForP2PFacade;
    
    @Autowired
    private IncomeCalcService incomeCalcService;
    
    @Autowired
    private ProductInternalService productInternalService;
    
    @Autowired
    private DubboCallBackService dubboCallBackService;
    
    @Autowired
    private MessageService messageService;
 
    /**
     * http://localhost:8080/exceptionBusiness/assetMatchCompleteNotify?loanNo=112233&productCode=dddddd
     * 匹配成功的通知
     * @param loanNo   借款单    (两个参数二选一)
     * @param productCode   
     * @throws Exception 
     */
    @RequestMapping(value = "assetMatchCompleteNotify" )
    public String batchLoan(@RequestParam(required = false) String loanNo,@RequestParam(required = false)  String productCode) throws Exception {
    	log.info("=---------------loanNo:" + loanNo + ",productCode:" + productCode);
    	if(StringUtils.isNotEmpty(loanNo  )){
    		orderAsyncService.handlerEvent(loanNo); 
    	}else if(StringUtils.isNotEmpty(productCode  )){
    		Map<String, Object> params = new HashMap<>();
    		ArrayList<String> list = new ArrayList<>();
    		list.add(productCode );
    		params.put("productCodeList", list);
    		
    		 List<LoanRequestEntity> loanRequestEntityList = loanRequestDAO.queryLoanListByParams(params);
    		
    		for (LoanRequestEntity loanRequestEntity : loanRequestEntityList) {
    			orderAsyncService.handlerEvent(loanRequestEntity.getLoanNo()); 
			}
    	}else{
    		return "failed";
    	}
	 
        return "success";
    }
    
    /**
     * 收益计算
     * @param productCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "incomeCalc" )
    public String incomeCalc(@RequestParam(required = false)  String productCode) throws Exception {
    	
    	CommonResp<ProductDTO> resp = productInternalService.queryProductInfoByProductCode(productCode);
    	
    	ProductDTO productDTO = resp.getData();
    	
    	incomeCalcService.incomeCalc(productDTO);
    	
    	return "success";
    }
    
    /**
     * 放款回调
     * @param orderNo
     * @param tradeStatus
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "loanCallback" )
    public String loanCallback(@RequestParam(required = false)  String orderNo, @RequestParam(required = false)  String tradeStatus) throws Exception {
    	 
    	NotifyTradeStatusReq notifyTradeStatusReq = new NotifyTradeStatusReq();
    	notifyTradeStatusReq.setBusiType("20");
    	notifyTradeStatusReq.setTradeType("02");
    	notifyTradeStatusReq.setOrderNo(orderNo);
    	notifyTradeStatusReq.setTradeStatus(tradeStatus );
    	dubboCallBackService.callback(notifyTradeStatusReq);
    	
    	return "success";
    }

    /**
     * 放款中
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "notifyMsdLoanPaymentProcessing")
    public String notifyMsdLoanPaymentProcessing(@RequestParam(required = false)  String loanNo) throws Exception {
        LoanRequestEntity loanRequest = loanRequestDAO.selectByLoanNo(loanNo);
        orderAsyncService.notifyMsdLoanPaymentProcessing(loanRequest);
        return "success";
    }

    /**
     * 放款成功
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "notifyMSDLoanPaymentStatus")
    public String notifyMSDLoanPaymentStatus(@RequestParam(required = false)  String loanNo) throws Exception {
        LoanRequestEntity loanRequest = loanRequestDAO.selectByLoanNo(loanNo);
        orderAsyncService.notifyMSDLoanPaymentStatus(loanRequest, LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode(), new Date());
        return "success";
    }
    
    
    @RequestMapping(value = "sendMsg" )
    public String sendMsg() throws Exception {
    	 
    	String[] timeArr = StringUtils.split( DateUtils.format(new Date(), "MM-dd-HH-mm") , "-");
    	String[] loanTimeArr = StringUtils.split( DateUtils.format(new Date(), "yyyy-MM-dd") , "-");
    	
    	LoanWithdrawMessageInfo loanWithdrawMessageInfo = new LoanWithdrawMessageInfo();
    	String bankNo = "6229222233335555";  
    	loanWithdrawMessageInfo.setBankNo(bankNo.substring( bankNo.length() - 4 , bankNo.length()) );
    	loanWithdrawMessageInfo.setTotalAmount(NumberUtils.numberFormat(new BigDecimal(10000), NumberUtils.DECIMAL_FORMAT_N2)  );
    	//loanWithdrawMessageInfo.setUserName("唐庆庆");
    	loanWithdrawMessageInfo.setMonth(timeArr[0]);
    	loanWithdrawMessageInfo.setDay(timeArr[1]);
    	loanWithdrawMessageInfo.setHour(timeArr[2]); 
    	loanWithdrawMessageInfo.setMins(timeArr[3]);
    	loanWithdrawMessageInfo.setLoanDay(loanTimeArr[2]);
    	loanWithdrawMessageInfo.setLoanMonth(loanTimeArr[1]);
    	loanWithdrawMessageInfo.setLoanYear(loanTimeArr[0]);
    	
    	//String mobile = loanRequest.getFinanceSubjectTel();
    	String mobile = "15102106229";
    	
    	 messageService.send(GlobalVar.LOAN_WITHDRAW_MESSAGE_TEMPLATE_CODE, mobile, JSON.toJSONString(loanWithdrawMessageInfo ));
    	 
    	return "success";
    }
     
     public static void main(String[] args) {
//    	 String bankNo = "1111222233334444";
//    	 
//    	 bankNo.substring( bankNo.length() - 4 , bankNo.length());
//    	 System.out.println(bankNo.substring( bankNo.length() - 4 , bankNo.length()));
    	 
//    	 BigDecimal loanRequestMatchedAmount = new BigDecimal(1000);
//     	BigDecimal matchedAmountTotal = new BigDecimal(1000);
//     	
//     	System.out.println(loanRequestMatchedAmount.compareTo(matchedAmountTotal) != 0);
    	 
    	 String bizList = Joiner.on(",").join( "'LOAN_NOTIFY_PAYMENT'","'LOAN_NOTIFY_PAYMENT'"
         		/*"LOAN_NOTIFY_PAYMENT", "PRODUCT_FROZEN_STOCK_NOTIFY",
                 "PRODUCT_STOCK_NOTIFY", "ASSET_RELATION_NOTIFY",*/ 
         		 );
    	 DecimalFormat decimalFormat = new DecimalFormat("###.00");
    	 System.out.println(NumberUtils.numberFormat(new BigDecimal(100000.12), NumberUtils.DECIMAL_FORMAT_N2) );
    	 System.out.println(decimalFormat.format(new BigDecimal(100000) )  );
    	 
     }
   
}
