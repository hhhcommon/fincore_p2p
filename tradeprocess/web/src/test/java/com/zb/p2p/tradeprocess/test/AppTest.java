package com.zb.p2p.tradeprocess.test;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.utils.HttpClientUtil;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.facade.product.ProductCacheServiceFacade;
import com.zb.fincore.pms.facade.product.ProductCacheServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockForP2PRequest;
import com.zb.fincore.pms.facade.product.dto.req.FreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.p2p.dao.master.*;
import com.zb.p2p.dao.slave.HoldDao;
import com.zb.p2p.entity.ContractEntity;
import com.zb.p2p.enums.SequenceEnum;
import com.zb.p2p.facade.api.req.LoanReq;
import com.zb.p2p.facade.api.req.OrderReq;
import com.zb.p2p.facade.service.internal.OrderInternalService;
import com.zb.p2p.service.callback.MSDCallBackService;
import com.zb.p2p.service.callback.TXSCallBackService;
import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.service.common.DistributedSerialNoService;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.order.OrderService;
import com.zb.p2p.tradeprocess.web.TradeProcessApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by limingxin on 2017/8/25.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeProcessApplication.class)
public class AppTest {

    @Autowired
    AccountDao accountMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    TXSCallBackService txsCallBackService;
    @Autowired
    DistributedSerialNoService distributedSerialNoService;
    @Autowired
    OrderInternalService orderInternalService;
    ExecutorService es = Executors.newFixedThreadPool(32);
    ExecutorService es_ = Executors.newFixedThreadPool(32);
    @Autowired
    ProductCacheServiceFacade productCacheServiceFacade;
    @Autowired
    ProductCacheServiceForP2PFacade productCacheServiceForP2PFacade;
    @Autowired
    DailyIncomeDAO dailyIncomeDAO;

    @Autowired
    MSDCallBackService msdCallBackService;
    @Autowired
    InterfaceRetryDAO interfaceRetryDAO;
    @Autowired
    InterfaceRetryService interfaceRetryService;
    @Autowired
    DistributedLockService distributedLockService;
    @Autowired
    ContractDAO contractDAO;
    @Autowired
    HoldDao holdDao;


    @Test
    public void test() {
        //accountMapper.selectByPrimaryKey(1L);
        //orderInternalService.getLoanRequestList(null);
        //ReservationOrderDTO orderDTO = new ReservationOrderDTO();
        //orderDTO.setProductCode("JG00001");
        //orderInternalService.getReservationList(orderDTO);
//        dailyIncomeDAO.queryTotalIncomeBatchByProductCode("test694", Lists.newArrayList("0217090025", "0217090026"), new Date());
//        msdCallBackService.notifyLoanStatus(new NotifyLoanStatusReq());
//        interfaceRetryDAO.queryWaitRetryRecordListByBizType("1,2,3");
//        interfaceRetryService.selectByBusinessNoAndBizType("1","2");
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        final CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.0.65:2181", retryPolicy);
//        client.start();
//        for (int i = 0; i < 1000; i++) {
//            es.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        distributedLockService.tryLock(GlobalVar.GLOBAL_MATCH_LOCK_KEY);
////                        InterProcessMutex lock = new InterProcessMutex(client, "/testLock");
////                        lock.acquire();
//                        log.info("--------1----------");
////                        lock.release();
////                        log.info("--------2----------");
//                    } catch (Exception e) {
//                        log.error("", e);
//                    } finally {
//                        try {
//                            distributedLockService.unLock(GlobalVar.GLOBAL_MATCH_LOCK_KEY);
//                            log.info("---------1 release--------- {}", GlobalVar.GLOBAL_MATCH_LOCK_KEY);
//                        } catch (Exception e) {
//                            log.error("", e);
//                        }
//                    }
//                }
//            });
//        }
//
//        try {
//            es.awaitTermination(10, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ReservationOrderEntity reservationOrderEntity = new ReservationOrderEntity();
//        reservationOrderEntity.setFrozenAmount(new BigDecimal(500));
//        reservationOrderEntity.setReservationStatus("1");
//        reservationOrderEntity.setId(1);
//        ReservationOrderEntity reservationOrderEntity_ = new ReservationOrderEntity();
//        reservationOrderEntity_.setFrozenAmount(new BigDecimal(500));
//        reservationOrderEntity_.setReservationStatus("1");
//        reservationOrderEntity_.setId(2);
//        List<ReservationOrderEntity> reservationOrderEntityList = com.google.common.collect.Lists.newArrayList(reservationOrderEntity, reservationOrderEntity_);
//        reservationOrderDAO.updateFrozenAmountForBatchById(reservationOrderEntityList);

        List<ContractEntity> contractEntities = com.google.common.collect.Lists.newArrayList();
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setId(12345678911L);
        contractEntities.add(contractEntity);
        ContractEntity contractEntity_ = new ContractEntity();
        contractEntity_.setId(12345678901L);
        contractEntities.add(contractEntity_);
//        contractDAO.insertBatch(contractEntities);
        holdDao.countByProduct("1");

    }


    @Test
    public void loanTest() {
        try {
            for (int i = 0; i < 1; i++) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        LoanReq reservationReq = new LoanReq();
                        reservationReq.setMemberId("test" + RandomUtils.nextInt(1, 500));
//                        reservationReq.setAssetCode("ZC888");
//                        reservationReq.setAssetName("贷贷贷");
//                        reservationReq.setAssetAmount(new BigDecimal(1000));
                        reservationReq.setLoanAmount(new BigDecimal(1000));
                        reservationReq.setSaleChannel("MSD");
                        reservationReq.setLoanTime(new Date());
//                        reservationReq.setAssetPoolCode("ZC_POOL_01");
                        reservationReq.setLoanFee(new BigDecimal(0.045));
                        reservationReq.setLoanInterests(new BigDecimal(0.42));
                        reservationReq.setLockDate(50);
                        try {
                            reservationReq.setExpireDate(DateUtils.parseDateStrictly("2018-12-12", "yyyy-MM-dd"));
                            reservationReq.setValueStartTime(DateUtils.parseDateStrictly("2017-09-12", "yyyy-MM-dd"));
                            reservationReq.setValueEndTime(DateUtils.parseDateStrictly("2017-09-25", "yyyy-MM-dd"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        reservationReq.setLoanNo(distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.RESERVATION, reservationReq.getSaleChannel(), 1));
                        try {
                            orderService.loan(reservationReq);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* @Test
    public void reservationTest() {
        try {
            for (int i = 0; i < 1; i++) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        ReservationReq reservationReq = new ReservationReq();
                        reservationReq.setMemberId("test" + RandomUtils.nextInt(500, 1000));
                        reservationReq.setProductCode("0217090005");
                        reservationReq.setReservationAmount(new BigDecimal(1000));
                        reservationReq.setSaleChannel("ZD");
                        reservationReq.setReservationTime(com.zb.fincore.common.utils.DateUtils.format(new Date(), com.zb.fincore.common.utils.DateUtils.DEFAULT_DATETIME_FORMAT));
                        reservationReq.setExtReservationNo(distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.RESERVATION, reservationReq.getSaleChannel(), 1));
                        reservationReq.setUserName("张三");
                        reservationReq.setCertNo("100001");
                        reservationReq.setTelNo("13966669999");
                        try {
                            orderService.reservation(reservationReq);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void orderTest() {
        try {
            for (int i = 0; i < 1000; i++) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        OrderReq orderReq = new OrderReq();
                        orderReq.setSaleChannel("TXS");
                        orderReq.setMemberId("test" + RandomUtils.nextInt(500, 1000));
//                        orderReq.setAssetCode("ZC888");
//                        orderReq.setProductCode("JG00001");
//                        orderReq.setAssetAmount(new BigDecimal(500));
//                        orderReq.setMatchedAmount(new BigDecimal(100));
                        orderReq.setExtOrderNo(distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.ORDER, orderReq.getSaleChannel(), 1));
                        try {
                            orderService.order(orderReq);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            es.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    DataSource dataSource;

    /**
     * 模拟真实测试投资（基于预匹配记录）
     */
    @Test
    public void mockOrderData() {
        try (ResultSet rs = dataSource.getConnection().prepareStatement("select * from p2p_match_record where product_code='0217100021'").executeQuery()) {
            while (rs.next()) {
                {
                    final OrderReq orderReq = new OrderReq();
                    orderReq.setSaleChannel("ZD");
                    orderReq.setMemberId(rs.getString("member_id"));
//                    orderReq.setReservationNo(rs.getString("reservation_no"));
//                    orderReq.setAssetCode(rs.getString("asset_code"));
//                    orderReq.setProductCode(rs.getString("product_code"));
//                    orderReq.setExtReservationNo(rs.getString("ext_reservation_no"));
//                    orderReq.setAssetAmount(rs.getBigDecimal("asset_amount").setScale(2));
//                    orderReq.setMatchedAmount(rs.getBigDecimal("matched_amount").setScale(2));
//                    orderReq.setOrderTime(com.zb.fincore.common.utils.DateUtils.format(new Date(), com.zb.fincore.common.utils.DateUtils.DEFAULT_DATETIME_FORMAT));
                    orderReq.setExtOrderNo(distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.ORDER, orderReq.getSaleChannel(), 1));
                    es.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                System.out.printf("req=%s \n", JSON.toJSONString(orderReq));
                                System.out.printf("resp=%s \n", HttpClientUtil.sendPostRequest("http://120.26.100.21/order/invest", JSON.toJSONString(orderReq)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testStock() {
        FreezeProductStockRequest freezeProductStockRequest = new FreezeProductStockRequest();
        freezeProductStockRequest.setProductCode("0217090018");
        freezeProductStockRequest.setChangeAmount(BigDecimal.valueOf(100));
        freezeProductStockRequest.setRefNo("fangyang-test-no-4");
        System.out.println("请求参数：" + JSON.toJSONString(freezeProductStockRequest));
        FreezeProductStockResponse freezeProductStockResponse = productCacheServiceFacade.freezeProductStock(freezeProductStockRequest);
        System.out.println("响应结果：" + JSON.toJSONString(freezeProductStockResponse));
    }

    @Test
    public void changeProductStockForP2P() {
        ChangeProductStockForP2PRequest changeProductStockRequest = new ChangeProductStockForP2PRequest();
        changeProductStockRequest.setRefNo("fangyang-test-no-4");
        changeProductStockRequest.setChangeAmount(BigDecimal.valueOf(51));
        changeProductStockRequest.setProductCode("0217090018");
        changeProductStockRequest.setChangeType(ChangeProductStockTypeEnum.CANCEL.getCode());
        System.out.println("请求参数：" + JSON.toJSONString(changeProductStockRequest));
        ChangeProductStockResponse changeProductStockResponse = productCacheServiceForP2PFacade.changeProductStock(changeProductStockRequest);
        System.out.println("响应结果：" + JSON.toJSONString(changeProductStockResponse));
    }

}