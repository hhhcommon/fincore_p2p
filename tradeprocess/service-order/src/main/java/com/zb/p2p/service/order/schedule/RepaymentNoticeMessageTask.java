package com.zb.p2p.service.order.schedule;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.p2p.GlobalVar;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.enums.LoanPaymentStatusEnum;
import com.zb.p2p.enums.LoanStatusEnum;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.enums.SourceIdEnum;
import com.zb.p2p.service.message.api.LoanWithdrawMessageInfo;
import com.zb.p2p.service.message.api.RepaymentNoticeMessageInfo;
import com.zb.p2p.service.message.service.MessageService;
import com.zb.p2p.service.order.BasicDataService;
import com.zb.p2p.service.order.OrderAsyncService;
import com.zb.payment.msd.cashier.facade.QueryFacade;
import com.zb.payment.msd.cashier.facade.dto.req.QueryTradeStatusReqDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.CashierRspDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.QueryTradeStatusRspDTO;
import com.zb.payment.msd.cashier.facade.enums.BusiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 还款通知短信发送job
 * Created by zhangxin on 2017/10/16.
 */
@Slf4j
@Component("repaymentNoticeMessageTask")
public class RepaymentNoticeMessageTask implements IScheduleTaskDealSingle<LoanRequestEntity> {
    @Autowired
    private LoanRequestDAO loanRequestDAO;

    @Autowired
    private MatchRecordDAO matchRecordDAO;

    @Autowired
    private MessageService messageService;

    @Value("${payback.message.notice.day}")
    private String payBackDay;

    private void sendPayBackMessageNotify(LoanRequestEntity loanRequest) {
        try {
            /*List<MatchRecordEntity> matchRecordEntityList = matchRecordDAO.selectListByLoanNo(loanRequest.getLoanNo());
            if(CollectionUtils.isNullOrEmpty(matchRecordEntityList)){
                return;
            }
            BigDecimal loanRate = (null == loanRequest.getLoanRate()) ? BigDecimal.ZERO : loanRequest.getLoanRate();
            BigDecimal lockDate = BigDecimal.valueOf(loanRequest.getLockDate());
            BigDecimal repaymentAmount = BigDecimal.ZERO;
            for(MatchRecordEntity matchRecordEntity : matchRecordEntityList){
                BigDecimal matchAmount = (null == matchRecordEntity.getMatchedAmount()) ? BigDecimal.ZERO : matchRecordEntity.getMatchedAmount();
                BigDecimal curRepaymentAmount = NumberUtils.divide(NumberUtils.multiply(matchAmount, loanRate, lockDate), 365);
                repaymentAmount = NumberUtils.sum(true, repaymentAmount, curRepaymentAmount,matchRecordEntity.getMatchedAmount(), matchRecordEntity.getTotalIncome());
            }*/
            BigDecimal repaymentAmount = BigDecimal.ZERO;
            repaymentAmount = NumberUtils.sum(true, loanRequest.getMatchedAmount(), loanRequest.getRealLoanFee(), loanRequest.getTotalLoanCharge());

            String[] loanTimeArr = StringUtils.split(DateUtils.format(loanRequest.getLoanTime(), "yyyy-MM-dd") , "-");
            String[] repayTimeArr = StringUtils.split(DateUtils.format(loanRequest.getValueEndTime(), "yyyy-MM-dd") , "-");

            RepaymentNoticeMessageInfo messageInfo = new RepaymentNoticeMessageInfo();
            messageInfo.setLoanYear(loanTimeArr[0]);
            messageInfo.setLoanMonth(loanTimeArr[1]);
            messageInfo.setLoanDay(loanTimeArr[2]);
            messageInfo.setTotalAmount(String.valueOf(repaymentAmount));
            messageInfo.setMonth(repayTimeArr[1]);
            messageInfo.setDay(repayTimeArr[2]);
            messageService.send(GlobalVar.REPAYMENT_NOTICE_MESSAGE_TEMPLATE_CODE, loanRequest.getFinanceSubjectTel(), JSON.toJSONString(messageInfo ));
        } catch (Exception e) {
            log.error("发送还款通知短信异常", e);
        }
    }
    
   

    @Override
    public boolean execute(LoanRequestEntity task, String ownSign) throws Exception {
        sendPayBackMessageNotify(task);
        return true;
    }

    @Override
    public List<LoanRequestEntity> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        log.info("PayBackMessageNoticeTask配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);
        Map<String, Object> params = new HashMap<>();
        params.put("payBackMsgNotifyDay", payBackDay);
        List<String> loanStatusList = new ArrayList<>();
        loanStatusList.add(LoanStatusEnum.LOAN_SUCCESS.getCode());
        loanStatusList.add(LoanStatusEnum.PARTLY_LOAN_SUCCESS.getCode());
        params.put("loanStatusList", loanStatusList);
        return loanRequestDAO.queryLoanListByParams(params);
    }

    @Override
    public Comparator<LoanRequestEntity> getComparator() {
        return null;
    }
}
