package com.zb.p2p.cash.schedule;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.zb.fincore.ams.facade.LoanRepayPlanServiceFacade;
import com.zb.fincore.ams.facade.dto.req.UpdateCashStatusRequest;
import com.zb.fincore.ams.facade.model.UpdateLoanStatusModel;
import com.zb.p2p.CashNotify;
import com.zb.p2p.dao.master.AccountDao;
import com.zb.p2p.dao.master.CashRecordDAO;
import com.zb.p2p.entity.CashRecordEntity;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.enums.CashCommandEnum;
import com.zb.p2p.enums.CashStatusEnum;
import com.zb.p2p.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.service.callback.TXSCallBackService;
import com.zb.p2p.service.callback.api.req.NotifyTxsAssetMatchResultReq;
import com.zb.p2p.service.common.InterfaceRetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * 兑付到卡处理
 * Created by limingxin on 2018/1/10.
 */
@Component("notifyOtherPartyTask")
@Slf4j
public class NotifyOtherPartyTask implements IScheduleTaskDealSingle {
    @Autowired
    private CashRecordDAO cashRecordDAO;
    @Autowired
    private LoanRepayPlanServiceFacade loanRepayPlanServiceFacade;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TXSCallBackService txsCallBackService;
    @Autowired
    private InterfaceRetryService interfaceRetryService;

    /**
     * 提现到卡完成后通知投资端（订单） 资产端（资产）更新兑付状态
     */
    public void notifyOtherParty() {
        List<CashNotify> assets = cashRecordDAO.queryAssetsByCashEnd();
        List<NotifyTxsAssetMatchResultReq> cashNotifyList = Lists.newArrayList();
        List<UpdateLoanStatusModel> loanList = Lists.newArrayList();
        for (CashNotify cashNotify : assets) {
            String loanNo = cashNotify.getLoanNo();
            //通知资产端的数据
            UpdateLoanStatusModel updateLoanStatusRequest = new UpdateLoanStatusModel();
            updateLoanStatusRequest.setLoanOrderNo(loanNo);
            updateLoanStatusRequest.setLoadStatus(CashStatusEnum.CASH_SUCCESS.getValue());//资管根据value传
            loanList.add(updateLoanStatusRequest);
        }
        List<CashNotify> cashNotifies = cashRecordDAO.queryOrdersByCashEnd();
        for (CashNotify cashNotify_ : cashNotifies) {
            //投资端的兑付成功数据
            NotifyTxsAssetMatchResultReq notifyTxsAssetMatchResultReq = new NotifyTxsAssetMatchResultReq();
            notifyTxsAssetMatchResultReq.setExtOrderNo(cashNotify_.getExtOrderNo());
            notifyTxsAssetMatchResultReq.setStatus(CashStatusEnum.CASH_SUCCESS.getCode());//订单根据code传
            notifyTxsAssetMatchResultReq.setType(NotifyTxsAssetMatchResultReq.Status.CASH.name());
            notifyTxsAssetMatchResultReq.setAmount(cashNotify_.getCashAmount().add(cashNotify_.getCashIncome()));
            cashNotifyList.add(notifyTxsAssetMatchResultReq);
        }
        try {
            if (!CollectionUtils.isEmpty(cashNotifyList)) {
                txsCallBackService.tradeNotifyOrder(cashNotifyList);
            }
        } catch (Exception e) {
            log.error("通知订单兑付结果失败", e);
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(cashNotifyList));
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_TXS.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
            }
        }
        UpdateCashStatusRequest updateLoanStatusRequests = new UpdateCashStatusRequest();
        try {
            if (!CollectionUtils.isEmpty(loanList)) {
                updateLoanStatusRequests.setLoanList(loanList);
                log.info("cash end notify asset req={}", JSON.toJSONString(updateLoanStatusRequests));
                loanRepayPlanServiceFacade.updateCashStatus(updateLoanStatusRequests);
            }
        } catch (Exception e) {
            log.error("通知资管兑付结果失败", e);
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(updateLoanStatusRequests));
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_ASSET.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
            }
        }

        //更新状态为兑付通知完成
        cashRecordDAO.updateCashByCashCard();
    }

    @Override
    public boolean execute(Object o, String s) throws Exception {
        return false;
    }

    @Override
    public List selectTasks(String s, String s1, int i, List list, int i1) throws Exception {
        log.info("通知其他端兑付结果任务处理中...");
        //注意这个任务不能分片执行
        notifyOtherParty();
        return null;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
