package com.zb.txs.p2p;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author nibaoshan
 * @create 2017-09-26 15:59
 * @desc 测试
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = P2PTradingBootstrap.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class NeilServiceTest {
//    @Autowired
//    private ProductMapper productMapper;
//    @Autowired
//    private ProductBaseMapper productBaseMapper;
//    @Autowired
//    private ProductSynchronizationJob productSynchronizationJob;
//    @Autowired
//    private ProductNetClient productNetClient;
//    @Autowired
//    private ApiClient apiClient;
//
//    @Autowired
//    private NetMemcache netMemcache;
//
//    @Autowired
//    private MembersClient membersClient;
//
//    @Autowired
//    private AppointmentRecordMapper appointmentRecordMapper;
//
//    @Test
//    public void ProductSynchronizationJobTestMethod() {
//        productSynchronizationJob.operateSynchronization();
//    }
//
//
//
//    @Test
//    public void  updateProductSynStatusTestMethod(){
//        ProductSynStatus model=new ProductSynStatus();
//        model.setProductCode("0217090025");
//        //如果存在了，则需要将此数据同步给金核，下次拉取不显示
//        Call<ResponseBody> updateSynStatusCall = apiClient.updateProductSynStatus(model);
//        try {
////            Result updateResult = null;
////            updateResult = updateSynStatusCall.execute().body();
//            String result=updateSynStatusCall.execute().body().string();
//            log.info(result);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public  void  ProductIntroductionRespTestMethod(){
//        String d="{\"repayWay\":\"到期一次性还本付息\",\"project\":\"该产品计划是马上贷平台向出借人提供的本金出借和到期自动回款的投标服务。该计划投资标的的借款人是经过马上贷平台多层风控审核的个人，单笔借款金额1000-5000元，投资标的具有小额分散的特点，安全系数较高。\",\"riskTip\":\"理财有风险，投资需谨慎\",\"investTargetIntroduction\":\"定向投资标的简介：投资标的为合作机构推荐的优质企业应收账款融资项目。该融资项目由GTSY保理（天津）有限公司进行严格风控审核。应收账款的付款方均为GTSY保理有限公司认可的优质企业，主要包括但不限于国有企业、上市公司、上市公司子公司或上市公司的母公司、知名国际企业中国子公司或分公司，知名大型民营企业集团等，融资主要用于满足企业在生产经营过程中正常的资金需求。还款来源为应收账款到期回款等。\",\"repaySource\":\"借款人到期还款\",\"cooperationOrgName\":\"发行方展示名称1；经营范围：阿斯顿发给他\",\"trusteeName\":\"受托方方006；公司地址：北京市房山区；经营范围：哇哇哇\"}";
//        ProductIntroductionResp productIntroductionResp=JsonUtils.fromJson(d,ProductIntroductionResp.class);
//        productIntroductionResp.getProject();
//        log.info(productIntroductionResp+"");
//    }
//
//    @Test
//    public void GetProductItemTestMethod() {
//        try {
//            String productCode="918800682042134528";
//            ProductItem productItem=new ProductItem();
//
//            productItem.setProductid(productCode);
//            Call<ResponseBody> resultCall = productNetClient.getProductItem(productItem);
//            String responseResult = resultCall.execute().body().string();
//            ProductItemResp productItemResp= JsonUtils.fromJson(responseResult,ProductItemResp.class);
//            log.info(ResponseEntity.success(productItemResp)+"");
////            if (productItemResp == null || productItemResp.isResult()==false || productItemResp.getDate() == null) {
////                log.debug("get txs product detail error -[productCode:{}]",productCode);
////            }
////            Productinfo productinfo = new Productinfo();
////            productinfo = productItemResp.getProductinfo();
////
////            Product product=productMapper.selectByPrimaryKey(productinfo.getProductid());
////
////          ProductBase productBaseModel = productBaseMapper.selectByPrimaryKey(productCode);
//            //ProductDetailResp resp = new ProductDetailResp();
////            resp.setProductCode(product.getProductcode());
////            resp.setProductTitle(productBaseModel.getTitle());
////            resp.setRate(String.valueOf(productinfo.getRate()) );
////            resp.setRateActivite(String.valueOf(product.getRateactivity()));
////            resp.setDuration(String.valueOf(productinfo.getDuration()));
////            resp.setAmountMin(String.valueOf(productinfo.getAmountmin()));
////            resp.setAmountMax(String.valueOf(productinfo.getAmountmax()));
////            resp.setAmountStep(String.valueOf(productinfo.getStep()));
////            resp.setBidCount(String.valueOf(productinfo.getBidcount()));
////            resp.setProfitStartDay(String.valueOf(product.getStartprofittime()));
////            resp.setRepayTime(String.valueOf(product.getPaymenttime()));
////            //TODO:回款去向 String repayWhere
////            //TODO:回款方式 String repayType
////            resp.setRepayType(productinfo.getPaytype() + "");
////            resp.setIsTransfer(false);
////            //TODO:还款来源 String repaymentSource
////            //TODO:产品等级文字没有 resp.setRiskLevelText(productModel.);
////            //TODO:适合投资客户类型 riskLevelDesc
////            resp.setProjectDesc(productinfo.getDescription());
//            //TODO:投资原理 investTheory
//            //TODO:合作伙伴 partner
//              //ResponseEntity.success(resp);
//            //log.info(ResponseEntity.success(resp)+"");
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.info(e+"");
//        }
//    }
//
//    @Test
//    public void GetProductCodeTestMethod(){
////        Product product=productMapper.selectByProductCode("91327315954382028874561");
////        log.info(product.toString());
////        @Value("${configuration.paymentdirection}")
////        private String configurationpaymentdirection;
////
////        log.info(configurationpaymentdirection);
//
//    }
//
//    @Test
//    public void RisklevelTest(){
//        //TODO:暂时传值，不传值则取全部
////        model.setPageNo(1);
////        model.setPageSize(100000);
//        try {
////            if (resultCall.execute().body()== null) {
////                log.debug("ProductSynchronizationJob is null");
////                return;
////            }
//            ProductOnShelves model = new ProductOnShelves();
//            Call<ResponseBody> resultCall = apiClient.getOnShelvesProduct(model);
//            String responseResult = resultCall.execute().body().string();
//            if (responseResult.isEmpty()) {
//                log.info("ProductSynchronizationJob->responseResult is null");
//                return;
//            }
//            ProductShelvesResp shelvesProductResp = JsonUtils.fromJson(responseResult, ProductShelvesResp.class);
//            if (shelvesProductResp.getCode().equals(Result.CodeManager.SUCCESS.getCode())) {
//                if (shelvesProductResp.getDataList() == null ||
//                        shelvesProductResp.getDataList().size() == 0) {
//                    log.info("同步产品Job,没有上架没有数据");
//                    return;
//                }
//                for (DataList productItem : shelvesProductResp.getDataList()) {
//                    //只取approvalStatus=20 已审核通过的，插入到本地库中
//                    ShelvesProductApprovalStatusEnum approvalStatusEnum = ShelvesProductApprovalStatusEnum.AUDITED;
//                    if (productItem.getApprovalStatus() == approvalStatusEnum.getValue()) {
//                        //TODO  校验
//
//                        if (productItem.getProductCode().isEmpty()) {
//                            log.error("ProductSynchronizationJob->productCode is empty");
//                            continue;
//                        }
//                        String fincoreproductcode = productItem.getProductCode();
//                        //查询产品中是否有该产品信息
//                        Product product = productMapper.selectByProductCode(productItem.getProductCode());
//                        //本地不存在，则需要添加进来
//
//
//                        ProductSynStatus synStatusModel = new ProductSynStatus();
//                        synStatusModel.setProductCode(fincoreproductcode);
//                        //如果存在了，则需要将此数据同步给金核，下次拉取不显示
//
//                        Call<ResponseBody> updateSynStatusCall = apiClient.updateProductSynStatus(synStatusModel);
//                        String result = updateSynStatusCall.execute().body().string();
//                        ProductSynStatusResp productSynStatusResp = JsonUtils.fromJson(result, ProductSynStatusResp.class);
//                        if (!productSynStatusResp.getCode().equals(Result.CodeManager.SUCCESS.getCode())) {
//                            log.error("ProductSynchronizationJob->jinhe updateProductSynStatus error:{} ", String.valueOf(productItem));
//                            continue;
//                        }
//                        log.debug("同步产品Job->[FinCoreProductCode:{}] 已同步到数据库中", productItem.getProductCode());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("产品同步报错 start group ProductSynchronizationJob error {}", e);
//        }
//    }
//
//    @Test
//    public void  TestMemcache(){
//        String name="memcache";
////
//       // netMemcache.RemoveKey("neil");
////        Date date=new Date();
////        log.info((new Date(date.getTime() + 30 * 24 * 60 * 60 * 1000))+"");
////        log.info( date.getTime()+"");
//        MembersInfoRequest membersInfoRequest=new MembersInfoRequest();
//        membersInfoRequest.setMemberId("765875544947691521");
//        membersInfoRequest.setSourceId("MSDTZ");
//        Result<MembersInfoResponse>  results =membersClient.getMemberInfoConvertToResult(membersInfoRequest);
//        System.out.println(results);
//
//        List<AppointmentRecord> list=  appointmentRecordMapper.selectByProductPage("920834132341321728",1,10);
//        System.out.println(list);
//
//           String name1="天鼋";
//           String name2="少林寺";
//        name1=chineseName(name1);
//        name2=chineseName(name2);
//    }
//
//    public String chineseName(String fullName) {
//        if (StringUtils.isBlank(fullName)) {
//            return "";
//        }
//        String name = StringUtils.left(fullName, 1);
//        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
//    }
//    public static void main(String[] args) {
////        ProductShelvesResp resp = new ProductShelvesResp();
////        resp.setCode("123");
////        resp.setMsg("123");
////
////        DataList data = new DataList();
////        data.setId(123);
////
////        ProductPeriodModel model = new ProductPeriodModel();
////        model.setCreateBy("123");
////        data.setProductPeriodModel(model);
////        data.setProductProfitModel(new ProductProfitModel());
////        data.setProductStockModel(new ProductStockModel());
////
////        List list = new ArrayList();
////        list.add(data);
////        resp.setDataList(list);
////
////        System.out.println(JsonUtils.objToJson(resp));
////
////        ProductShelvesResp shelvesProductResp = JsonUtils.fromJson(JsonUtils.objToJson(resp),ProductShelvesResp.class);
////        System.out.println(shelvesProductResp.toString());
//    }
}
