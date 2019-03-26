package com.zb.txs.p2p;


import com.zb.txs.p2p.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by liguoliang on 2017/9/26.
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = P2PTradingBootstrap.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class P2PServiceOrderTest {

//    @Autowired
//    private TradeprocessClient tradeprocessClient;
//
    @Autowired
    private OrderService orderService;
//
//    @Autowired
//    private IdWorkerService idWorkerService;
//
//    @Autowired
//    private HoldAssetsMapper holdAssetsMapper;
//    private Result<Boolean> result;
//
//    @Test
//    public void test() throws IOException {
//        /*Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(currentTime);
//        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
//        calendar.add(Calendar.DATE, -1);    //得到前一天
//        String  yesDayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
//        try {
//            Date date2 = formatter.parse(yesDayDate);
//            long days=(currentTime.getTime()-date2.getTime())/(1000*60*60*24);
//            System.out.println(days);
//        }catch (Exception ex){
//        }*/
//        /*BigDecimal b1 = new BigDecimal(Double.toString(5));
//        BigDecimal b2 = new BigDecimal(Double.toString(1));
//        double b3 = b1.subtract(b2).doubleValue();*/
//
////        IncomeRecord incomeRecord = new IncomeRecord();
////        incomeRecord.setProductCode("1111");
////        incomeRecord.setMemberId("2222");
////        incomeRecord.setIncomeDate(DateUtil.getNow());
////
////        Call<Result<IncomeResp>> resultCall = tradeprocessClient.queryIncome(incomeRecord);
////        Result<IncomeResp> resultIncome = resultCall.execute().body();
////        if (resultIncome == null || !resultIncome.getCode().equals(Result.CodeManager.SUCCESS)) {
////            log.error("调用金核查询每日收益接口失败, Request:{}, Response:{}", incomeRecord.toString(), resultIncome == null ? "" : resultIncome.toString());
////        } else {
////            System.out.println(resultIncome.toString());
////        }
//
//
//    }
//
//    /**
//     * 添加持仓记录
//     */
//    @Test
//    public void testInsertOrUpdate() {
//
//        /*HoldAssets holdAssets = new HoldAssets();
//        holdAssets.setId(idWorkerService.getId());
//        holdAssets.setAccountId(123L);
//        holdAssets.setMemberId(123L);
//        holdAssets.setProductCode("0217100006");
//        holdAssets.setProductId("917659211911217152");
//        holdAssets.setProductTitle("马上贷222期");
//        holdAssets.setDuration(21);
//        holdAssets.setProductRate(new BigDecimal(12));
//        holdAssets.setStartDate(new Date());
//        holdAssets.setEndDate(DateUtil.getMonthAfterNum(1,new Date()));
//        holdAssets.setAmount(new BigDecimal(10000));
//        holdAssets.setMatchAmount(new BigDecimal(1000));
//        holdAssets.setRefundsAmount(new BigDecimal(0));
//        holdAssets.setProfit(new BigDecimal(50));
//        holdAssets.setRepayDay(DateUtil.getNow());
//        holdAssets.setStatus(HoldAssetsStatusEnum.PAYMENTS.getValue());
//        holdAssets.setCreated(DateUtil.getNow());
//        holdAssets.setCreatedBy(11111111111L);
//        holdAssets.setModified(DateUtil.getNow());
//        holdAssets.setModifiedBy(1111111111L);*/
//
//        /*ResponseEntity<Integer> resu = orderService.insertOrUpdateHoldAssets(holdAssets);
//        System.out.print(resu.toString());*/
//
//
//    }
//
//
//    /**
//     * 兑付回调
//     */
//    @Test
//    public void testRedemptionNotify() {
//        RedemptionRecord redemptionRecord = new RedemptionRecord();
//
//        redemptionRecord.setSerialNo("123456789");
//        redemptionRecord.setAmount("3000");
//        redemptionRecord.setMemberId("22222222");
//        redemptionRecord.setRedemptionTime(new Date().toString());
//        redemptionRecord.setProductCode("3333333");
//        redemptionRecord.setProfit("120");
//
//        Result<Object> responseEntity = orderService.redemptionNotify(redemptionRecord);
//        System.out.print(responseEntity.toString());
//
//    }
//
//    /**
//     * 查询用户的持仓列表
//     */
//    @Test
//    public void testGetHoldAssetsList() {
//
////        HoldAssets holdAssets=holdAssetsMapper.selectByPrimaryKey(913298489957732352L);
//
//        ResponseEntity<HoldAssetsResp> respResponseEntity = orderService.getHoldAssetsList("222222", 0L);
//
//        System.out.print(respResponseEntity);
//    }
//
    /**
     * 查询用户的交易记录
     */
    @Test
    public void testGetTransactionLogList() {

//        //0:全部 3：投资 4：赎回
//
//        ResponseEntity<List<TransactionlogResp>> responseEntity = orderService.getTransactionLogList(22222222L, 0, 0L);
//        System.out.print(responseEntity.toString());
    }
//


}
