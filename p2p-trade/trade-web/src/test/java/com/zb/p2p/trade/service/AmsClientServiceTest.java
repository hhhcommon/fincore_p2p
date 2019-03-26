package com.zb.p2p.trade.service;

import com.zb.p2p.trade.api.req.RepaymentReq;
import com.zb.p2p.trade.client.ams.AmsClientService;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.persistence.dao.CashBillPlanMapper;
import com.zb.p2p.trade.persistence.dao.CashRecordMapper;
import com.zb.p2p.trade.persistence.entity.CashBillPlanEntity;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import com.zb.p2p.trade.service.cash.CashBillPlanService;
import com.zb.p2p.trade.service.common.DistributedLockService;
import com.zb.p2p.trade.service.task.CashPlanPayTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version AmsClientServiceTest.java v1.0 2018/4/28 15:05 Zhengwenquan Exp $
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"log.base=${TRADE_LOG_CONF_PATH}/log", "spring.config.location=${TRADE_APP_CONF_PATH}"})
public class AmsClientServiceTest {

    @Autowired
    private AmsClientService amsClientService;
    @Autowired
    private CashBillPlanMapper cashBillPlanMapper;
    @Autowired
    private CashRecordMapper cashRecordMapper;

    @Autowired
    private CashPlanPayTask cashPlanPayTask;
    @Autowired
    private CashBillPlanService cashBillPlanService;
    @Autowired
    private DistributedLockService distributedLockService;

    @Test
    public void testMatchCompleteHandle() throws Exception {

        List list = amsClientService.queryRepayPlan("AMA180400020", 2);
        System.out.println(list);
    }

    @Test
    public void testBillMapper() {
        cashBillPlanMapper.selectByKey("11", 1, "313", true);
    }

    @Test
    public void testCashPlanMapper() {
        List<CashRecordEntity> list = cashRecordMapper.selectByBillPlanId(172L, CashStatusEnum.CASH_WAIT_PERFORM.getCode());
        System.out.println(list);
    }

    @Test
    public void testCashPlanPayTask() {
        CashBillPlanEntity billPlanEntity = cashBillPlanMapper.selectByPrimaryKey(172L);
        try {
            cashPlanPayTask.execute(billPlanEntity, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRepayment() {
        RepaymentReq repaymentReq = new RepaymentReq();
        repaymentReq.setAssetCode("0218050010");
        cashBillPlanService.repaymentTradeResultHandler(repaymentReq);
    }

    @Test
    public void testLock() {
        ExecutorService es = Executors.newFixedThreadPool(8);
        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                String key = "lockTest_" + atomicInteger.getAndIncrement();
                try {
                    distributedLockService.tryLock(key);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        distributedLockService.unLockAndDel(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            es.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
