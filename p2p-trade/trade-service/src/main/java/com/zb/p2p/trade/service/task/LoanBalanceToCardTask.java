package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.trade.common.enums.LoanPaymentStatusEnum;
import com.zb.p2p.trade.common.enums.PayChannelEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.dao.LoanRequestMapper;
import com.zb.p2p.trade.persistence.dao.MatchRecordMapper;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import com.zb.p2p.trade.service.order.BasicDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *  放款余额账户到卡跑批(目前只针对宝付通道)
 *  预防  MQ为未通知的场景
 *  (0 1/10 * * * ?)
 */
@Slf4j
@Component("loanBalanceToCardTask")
public class LoanBalanceToCardTask extends AbstractScheduleTask<LoanRequestEntity> {
   
    @Autowired
    private BasicDataService basicDataService;

    @Override
    protected boolean process(LoanRequestEntity transferCode, String ownSign) throws BusinessException {
    	try{
            //查询借款单
//    		orderAsyncService.handleCreditorAndLoan(loanRequestMapper.selectByAssetCode(transferCode));
    	}catch (BusinessException e) {
			log.info(e.getMessage());
		} 
        return true;
    }

    @Override
    public List<LoanRequestEntity> selectProcessItems(String taskParameter, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {

        log.info("LoanBalanceToCardTask配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);

        // 执行开始和结束时间，偏移量
        final Date now = new Date();
        final Date beginTime = DateUtil.addDays(now, -1 * 3);
        final Date endTime = DateUtil.addMinutes(now, -1 * 6);

        List<LoanRequestEntity> loanRequestList = basicDataService.selectByPaymentStatus(LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS,
                PayChannelEnum.BAOFU, beginTime, endTime, eachFetchDataNum);

        return loanRequestList;
    }

    @Override
    protected String getTaskName() {
        return "放款余额账户到卡任务";
    }
}
