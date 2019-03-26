package com.zb.p2p.service.order.schedule;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.service.order.OrderAsyncService;

import lombok.extern.slf4j.Slf4j;

/**
 *  持仓(目前是跑  持仓，合同，放款的逻辑)
 *  (0 1/10 * * * ?)
 */
@Slf4j
@Component("accountTask")
public class AccountTask implements IScheduleTaskDealSingle<String> {
   
    
    @Autowired
    private MatchRecordDAO matchRecordDAO;
    
    @Autowired
    private OrderAsyncService orderAsyncService;

     
    @Override
    public boolean execute(String loanNo, String ownSign) throws Exception {
    	orderAsyncService.handlerEvent(loanNo);
        return true;
    }

    @Override
    public List<String> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        log.info("配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);
        
        List<String> list = matchRecordDAO.selectLoanNoListForJob();
        
       return  list;
    }

    @Override
    public Comparator<String> getComparator() {
        return null;
    }
}
