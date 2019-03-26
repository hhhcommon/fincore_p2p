package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import com.zb.p2p.trade.service.message.api.RepaymentNoticeMessageInfo;
import com.zb.p2p.trade.service.message.service.MessageService;
import com.zb.p2p.trade.service.order.BasicDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 还款通知短信发送job
 * Updated by Vinson on 2018/6/6.
 */
@Slf4j
@Component("repaymentNoticeMessageTask")
public class RepaymentNoticeMessageTask extends AbstractScheduleTask<LoanRequestEntity> {

    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private MessageService messageService;

    @Value("${message.notice.preDays}")
    private Integer preDays;

    @Override
    public List<LoanRequestEntity> selectProcessItems(String taskParameter, List<TaskItemDefine> taskItemList, int eachFetchDataNum)
            throws BusinessException {
        // 执行开始和结束时间，偏移量
        final Date now = new Date();
        final Date beginTime = DateUtil.addDays(now, -1 * preDays);
        final Date endTime = DateUtil.addMinutes(now, -1 * 5);

        List<LoanRequestEntity> loanRequestList = basicDataService.queryLoanForRepaySms(beginTime, endTime, eachFetchDataNum);

        if (CollectionUtils.isNullOrEmpty(loanRequestList)) {
            return Collections.EMPTY_LIST;
        }

        return loanRequestList;
    }

    @Override
    protected boolean process(LoanRequestEntity loanRequest, String s) throws BusinessException {

        BigDecimal repaymentAmount ;
        repaymentAmount = NumberUtils.sum(true, loanRequest.getMatchedAmount(), loanRequest.getActualLoanInterests(), loanRequest.getTotalLoanCharge());

        String[] loanTimeArr = StringUtils.split(DateUtils.format(loanRequest.getLoanTime(), "yyyy-MM-dd") , "-");
        String[] repayTimeArr = StringUtils.split(DateUtils.format(loanRequest.getValueEndTime(), "yyyy-MM-dd") , "-");

        RepaymentNoticeMessageInfo messageInfo = new RepaymentNoticeMessageInfo();
        messageInfo.setLoanYear(loanTimeArr[0]);
        messageInfo.setLoanMonth(loanTimeArr[1]);
        messageInfo.setLoanDay(loanTimeArr[2]);
        messageInfo.setTotalAmount(String.valueOf(repaymentAmount));
        messageInfo.setMonth(repayTimeArr[1]);
        messageInfo.setDay(repayTimeArr[2]);
        boolean flag = messageService.send(GlobalVar.REPAYMENT_NOTICE_MESSAGE_TEMPLATE_CODE,
                loanRequest.getFinanceSubjectTel(), JSON.toJSONString(messageInfo ));
        logger.info("借款单号为[{}]的还款短信发送结果：[{}]", loanRequest.getLoanNo(), flag);
        return true;
    }

    @Override
    protected String getTaskName() {
        return "还款通知短信任务";
    }

}
