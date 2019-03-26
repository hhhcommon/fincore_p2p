//package com.zb.p2p.cash.schedule;
//
//import com.google.common.base.Joiner;
//import com.google.common.collect.Lists;
//import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
//import com.zb.fincore.ams.facade.LoanRepayPlanServiceFacade;
//import com.zb.fincore.ams.facade.dto.req.UpdateCashStatusRequest;
//import com.zb.fincore.ams.facade.model.UpdateLoanStatusModel;
//import com.zb.p2p.cash.service.CashRecordService;
//import com.zb.p2p.dao.master.CashRecordDAO;
//import com.zb.p2p.enums.CashStatusEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Comparator;
//import java.util.List;
//
///**
// * 兑付失败通知资管任务
// * <p>
// * Created by limingxin on 2018/1/10.
// */
//@Component("cashFailNotifyTask")
//@Slf4j
//public class CashFailNotifyTask implements IScheduleTaskDealSingle {
//    @Autowired
//    private CashRecordDAO cashRecordDAO;
//    @Autowired
//    private CashRecordService cashRecordService;
//    @Autowired
//    private LoanRepayPlanServiceFacade loanRepayPlanServiceFacade;
//
//    @Override
//    public boolean execute(Object o, String s) throws Exception {
//        return false;
//    }
//
//    @Override
//    public List selectTasks(String s, String s1, int i, List list, int i1) throws Exception {
//        log.info("通知资产端兑付失败的任务处理中...");
//        //一直通知直到后续人工重试完成 条件是：当前时间减去更新时间大于一小时(即每小时通知一次)
//        List<String> loanNoList = cashRecordDAO.notifyAssetPartyCashFail();
//        List<UpdateLoanStatusModel> loanList = Lists.newArrayList();
//        //通知资管兑付转账失败
//        cashRecordService.notifyAssetParty(loanNoList,CashStatusEnum.CASH_FAIL);
//        if (!CollectionUtils.isEmpty(loanNoList)) {
//            String updateCashPlanByLoanList = Joiner.on(",").join(loanList);
//            //刷新时间戳
//            cashRecordDAO.updateUpdateTime(updateCashPlanByLoanList);
//        }
//        return null;
//    }
//
//    @Override
//    public Comparator getComparator() {
//        return null;
//    }
//}
