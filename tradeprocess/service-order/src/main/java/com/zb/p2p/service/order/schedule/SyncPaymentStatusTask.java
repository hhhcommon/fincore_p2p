package com.zb.p2p.service.order.schedule;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.enums.LoanPaymentStatusEnum;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.enums.SourceIdEnum;
import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.service.order.BasicDataService;
import com.zb.p2p.service.order.OrderAsyncService;
import com.zb.payment.msd.cashier.facade.QueryFacade;
import com.zb.payment.msd.cashier.facade.dto.req.QueryTradeStatusReqDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.CashierRspDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.QueryTradeStatusRspDTO;
import com.zb.payment.msd.cashier.facade.enums.BusiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 查询支付放款结果job
 * Created by limingxin on 2017/10/16.
 */
@Slf4j
//@Component("syncPaymentStatusTask")
public class SyncPaymentStatusTask implements IScheduleTaskDealSingle<LoanRequestEntity> {
    @Autowired
    private QueryFacade queryFacade;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private LoanRequestDAO loanRequestDAO;
    @Autowired
    private OrderAsyncService orderAsyncHandler;

    private void queryLoanPaymentStatus(LoanRequestEntity loanRequest) {
        QueryTradeStatusReqDTO req = new QueryTradeStatusReqDTO();
        try {
            //用id做支付订单号
//            String orderNo = String.valueOf(loanRequest.getId());
            req.setOrderNo(loanRequest.getLoanNo());
            req.setMemberId(loanRequest.getMemberId());
            req.setBusiType(BusiTypeEnum._21.getCode());
            req.setSourceId(SourceIdEnum.MSD.getCode());
            
            log.info("queryTradeStatus请求的参数为:{}", JSON.toJSONString(req));
            CashierRspDTO<QueryTradeStatusRspDTO> resp = queryFacade.queryTradeStatus(req);
            log.info("queryTradeStatus返回的结果为:{}", JSON.toJSONString(resp));
            
            if (resp != null && ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(resp.getCode())) {
                String tradeStatus = resp.getData().getTradeStatus();
                
                String loanPaymentStatus = "";
                
                if ("S".equals(tradeStatus)) {
//                    LoanRequestEntity loanRequestEntity_ = new LoanRequestEntity();
//                    loanRequestEntity_.setId(loanRequest.getId());
//                    //记录状态为放款完成
//                    loanRequestEntity_.setLoanPaymentStatus(LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode());
//                    basicDataService.updateLoanRequest(loanRequestEntity_);
//                    //通知马上贷放款完成
//                    orderAsyncHandler.notifyMSD(loanRequest.getLoanNo(), orderNo, "1", ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
                	
                	loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode();
                	
                	
                } else if ("F".equals(tradeStatus)) {//支付失败
//                    LoanRequestEntity loanRequestEntity_ = new LoanRequestEntity();
//                    loanRequestEntity_.setId(loanRequest.getId());
//                    loanRequestEntity_.setLoanPaymentStatus(LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode());
//                    basicDataService.updateLoanRequest(loanRequestEntity_);
                	
                	loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode();
                }
                
              //更新放款状态
                Date loanPaymentTime = new Date();
                loanRequestDAO.updateLoanPaymentStatus(loanPaymentStatus, loanRequest.getId(), loanPaymentTime);
                
                
            }
        } catch (Exception e) {
            log.error("查询交易状态异常", e);
        }
    }
    
   

    @Override
    public boolean execute(LoanRequestEntity task, String ownSign) throws Exception {
        queryLoanPaymentStatus(task);
        return true;
    }

    @Override
    public List<LoanRequestEntity> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        log.info("SyncPaymentStatusTask配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);
        return loanRequestDAO.selectByPaymentStatus(LoanPaymentStatusEnum.LOAN_PAYMENT_PROCESSING.getCode(), taskItemList, eachFetchDataNum);
    }

    @Override
    public Comparator<LoanRequestEntity> getComparator() {
        return null;
    }
}
