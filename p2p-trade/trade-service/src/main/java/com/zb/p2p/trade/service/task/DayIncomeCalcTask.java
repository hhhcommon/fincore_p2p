package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.trade.common.enums.LoanPaymentStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.cash.IncomeCalcService;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import com.zb.p2p.trade.service.order.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p> 每日利息计算跑批任务 </p>
 *
 * (0 14/30 * * * ?)
 */
@Component("incomeCalcTask")
public class DayIncomeCalcTask extends AbstractScheduleTask<LoanRequestEntity> {

    @Autowired
    private IncomeCalcService incomeCalcService;
    @Autowired
    private BasicDataService basicDataService;


    @Override
    public List<LoanRequestEntity> selectProcessItems(String taskParameter, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {

        logger.info("开始进行任务扫描 任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);

        Map<String, Object> params = new HashMap<>();
        List<String> loanStatusList = new ArrayList<>();
        loanStatusList.add(LoanPaymentStatusEnum.LOAN_WAIT_PAY.getCode());
        params.put("loanStatusList", loanStatusList);
        params.put("limit", eachFetchDataNum);

        List<LoanRequestEntity> resultList = basicDataService.queryLoanByUnCalcIncome(params);

        if (CollectionUtils.isEmpty(resultList)) {
            return Collections.emptyList();
        }
        logger.info("本次处理任务量:{}", resultList.size());

        return resultList;
    }

    @Override
    protected boolean process(LoanRequestEntity loanRequestEntity, String s) throws BusinessException {
        incomeCalcService.incomeCalc(loanRequestEntity);
        return true;
    }

    @Override
    protected String getTaskName() {
        return "每日利息计算跑批任务";
    }
}
