package com.zb.p2p.trade.service.task;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.trade.common.enums.LoanPaymentStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.persistence.dao.LoanRequestMapper;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import com.zb.p2p.trade.service.order.OrderAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 *  待放款状态借款订单进行T+1放款
 *  (0 0/30 8-18 * * ?)
 */
@Slf4j
@Component("loanPaymentBalanceTask")
public class LoanPaymentBalanceTask extends AbstractScheduleTask<LoanRequestEntity> {

    @Autowired
    private LoanRequestMapper loanRequestMapper;
    @Autowired
    private OrderAsyncService orderAsyncService;

    @Override
    public List<LoanRequestEntity> selectProcessItems(String taskParameter, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {
        log.info("LoanPaymentBalanceTask start");

        List<LoanRequestEntity> list = loanRequestMapper.selectForLoanWithdrawal(LoanPaymentStatusEnum.LOAN_WAIT_PAY.getCode() ,
                DateUtils.format(new Date(), DateUtils.DEFAULT_DATA_FORMAT) + " 23:59:59",taskItemList,eachFetchDataNum);

        log.info("LoanPaymentBalanceTask本次处理任务量:{}", list.size());
        return list;
    }

    @Override
    protected boolean process(LoanRequestEntity loanRequestEntity, String s) throws BusinessException {
        String transferCode = loanRequestEntity.getTransferCode();
        String loanNo = loanRequestEntity.getLoanNo();
        log.info("放款到余额开始，LoanNo=[{}]", loanNo);

//        String lockKey = GlobalVar.GLOBAL_LOANWITHDRAWAL_LOCK_KEY  + ":" + transferCode;

        try {
//            distributedLockService.tryLock(lockKey );

            LoanRequestEntity loanRequestEntityTemp = loanRequestMapper.selectByLoanNo(loanNo);
            orderAsyncService.loanWithdrawal(loanRequestEntityTemp);

            log.info("放款结束  assetNo：{}", transferCode);
        } catch (Exception e){
            log.error("放款失败  assetNo：" + transferCode,e );
        }finally {
//            try {
//                distributedLockService.unLockAndDel(lockKey);
//            } catch (Exception e) {
//                log.error("",e );
//            }
        }
        return true;
    }

    @Override
    protected String getTaskName() {
        return "执行放款支付Job";
    }
}
