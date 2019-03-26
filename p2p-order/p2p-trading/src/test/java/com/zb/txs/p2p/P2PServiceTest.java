/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Function:   p2p service单元测试 <br/>
 * Date:   2017年09月25日 下午2:53 <br/>
 *
 * @author liguoliang@zillionfortune.com
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = P2PTradingBootstrap.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class P2PServiceTest {
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private ProductClient productClient;
//
//    @Autowired
//    private ProductCutdayQueryRefoundJob productCutdayQueryRefoundJob;
//
//    @Autowired
//    private ProductService productService;
//
//    @Test
//    public void test() {
//        AssetMatchRecord assetMatchRecord = new AssetMatchRecord();
//        assetMatchRecord.setAssetAmount(new BigDecimal(1));
//        List<AssetMatchRecord> list = Arrays.asList(assetMatchRecord);
//        ResponseEntity rep = restTemplate.postForObject("/p2p/order/callback/matchinvest", list, ResponseEntity.class);
//        System.out.println("rep = " + rep.getMessage());
//    }
//
//    @Test
//    public void testSendReoundMethod()
//    {
//        try
//        {
//            List<String> code=new ArrayList<>();
//            code.add("77777777777");
//            ProductCutDayRecord pinfo= new ProductCutDayRecord();
//            pinfo.setProductCodes(code);
//            pinfo.setSerialNo("111111111");
//            productService.productCutDay(pinfo);
////            RefoundJinhe refoundJinhe = new RefoundJinhe();
////            refoundJinhe.setOrderNo("111111111");
////            refoundJinhe.setOrderTime(new Date());
////            refoundJinhe.setMemberId("22222222222");
////            refoundJinhe.setInstMemberId("");
////            refoundJinhe.setSignId("33333333333333333");
////            refoundJinhe.setTradeAmount(BigDecimal.valueOf(100));
////            Call<Result<RefoundResponse>> resultCall = productClient.sendRefound(refoundJinhe);
////            Result<RefoundResponse> responseEntity = resultCall.execute().body();
//
//            //String code=responseEntity.getCode().toString();
////            RefoundResponse response=responseEntity.getData();
//
//            //Call<ResponseBody> resultCall = productClient.sendRefound(refoundJinhe);
//            //ResponseBody responseEntity = resultCall.execute().body();
//        }
//        catch (Exception ex)
//        {
//
//        }
//
//    }
//
//    @Test
//    public void CheckRefoundInfo() throws InterruptedException {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://localhost:8999/")
//                .addConverterFactory(JacksonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2
//                .build();
//
//        OrderClient orderClient = retrofit.create(OrderClient.class);
//        try {
//            //orderClient.test().execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//            CountDownLatch countDownLatch = new CountDownLatch(1);
//            countDownLatch.await();
//        }
//    }
}
