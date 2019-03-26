package com.zb.fincore.pms.web.test.p2p;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zb.fincore.pms.facade.product.dto.req.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zb.fincore.ams.common.enums.PoolTypeEnum;
import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.enums.product.ProductRiskLevelEnum;
import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.facade.product.ProductServiceFacade;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.ProductServiceNFTFacade;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
@ActiveProfiles("dev")
public class ProductNFTTest {

    @Autowired
    private ProductServiceNFTFacade productServiceNFTFacade;
    
    @Autowired
    private ProductServiceForP2PFacade productServiceForP2PFacade;
    
    @Autowired
    private ProductServiceFacade productServiceFacade;


    /**
     * 货架系统 产品注册
     */
    @Test
    public void registerProductTest() {
    	RegisterProductRequestNFT req = new RegisterProductRequestNFT();
        
        //----------产品主信息表
        req.setProductName("P2PN复投计划定期产品");
        req.setProductDisplayName("P2PN复投计划定期产品");
        req.setProductLineId(1L);					//必填项
        req.setAssetPoolType(PoolTypeEnum.P2P_PLAN.getCode());					//资产池类型，交易结构，必填项
        req.setAssetPoolCode("AMP180400002");		//必填项
        req.setAssetPoolName("N计划类");			//必填项
        req.setPatternCode(PatternCodeTypeEnum.N_LOOP_PLAN.getCode());//产品的类型代码，必填项
        req.setSaleChannelCode(ChannelEnum.MSD.getCode());//必填项
//        req.setJoinChannelCode(ProductJoinChannelEnum.ZB_PERIODIC.getCode());//接入渠道(01:资邦阶梯, 02:资邦定期)
        req.setTotalAmount(new BigDecimal("1000"));//人工注册时必填项，自动注册不是必填项
        req.setRiskLevel(ProductRiskLevelEnum.PRODUCT_RISK_LEVEL_1.getCode());//必填项
        req.setFundSettleParty("sdf");//资金结算方,必填项
        req.setCalendarMode(10);//日历模式(程序默认10:自然日),必填项
//        req.setIntroduction("产品介绍信息");//产品介绍信息,非必填项
        //----------产品期限信息表
//        req.setSaleStartTime(DateUtils.parse("2018-1-15 00:00:00", "yyyy-MM-dd HH:mm:ss"));//募集起始时间，非必填项
//        req.setSaleEndTime(DateUtils.parse("2018-1-20 23:00:00", "yyyy-MM-dd HH:mm:ss"));//募集截止时间，非必填项
        req.setLockPeriod(30);//锁定期，必填项
        
        
        //----------产品投资收益信息表
        req.setMinInvestAmount(new BigDecimal("100"));//起投金额，必填项
        req.setSingleMaxInvestAmount(new BigDecimal("500"));//单笔投资限额,必填项
        req.setMaxInvestAmount(new BigDecimal("1000"));//个人最大投资限额,非必填
        req.setIncreaseAmount(new BigDecimal("100"));//递增金额(步长),必填项
        req.setMinYieldRate(new BigDecimal("0.05"));//最低预期收益率,必填项
        req.setMaxYieldRate(new BigDecimal("0.055"));//最高预期收益率,必填项
        
//        req.setProfitType(ProductProfitTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode());//收益方式:3:等额本息
//        req.setCalculateInvestType(ProductInvestTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode());//计息方式:5:等额本息
        
        System.out.println("定期产品注册请求对象：" + JsonUtils.object2Json(req));
        RegisterProductResponse response = productServiceNFTFacade.registerProductNFT(req);
        System.out.println("定期产品注册响应对象：" + JsonUtils.object2Json(response));
    }
    
    /**
     * 产品审核
     */
    @Test
    public void approveProduct() {
    	ApproveProductRequest req = new ApproveProductRequest();
    	req.setProductCode("0518050062");
        req.setApprovalStatus(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        req.setApprovalSuggestion("aaa");
        req.setApprovalBy("kaiyun");
        req.setSign("A");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.approveProduct(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品上线
     */
    @Test
    public void productOnLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0518050062");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.putProductOnLine(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品下线
     */
    @Test
    public void productOffLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0218010009");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.putProductOffLine(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品列表【供运营台】
     */
    @Test
    public void queryProductListForBoss() {
    	QueryProductListRequest req = new QueryProductListRequest();
    	System.out.println("请求对象：" + JsonUtils.object2Json(req));
        PageQueryResponse<ProductModel> response = productServiceFacade.queryProductListForBoss(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 待上架产品列表【供唐小僧】
     */
//    @Test
    public void queryProductList() {
        QueryProductListRequest req = new QueryProductListRequest();
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        PageQueryResponse<ProductModel> response = productServiceForP2PFacade.queryProductList(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 产品列表【供交易】
     */
//    @Test
    public void queryProductListForTrade() {
        QueryProductListRequest req = new QueryProductListRequest();
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        PageQueryResponse<ProductModel> response = productServiceForP2PFacade.queryProductListForTrade(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 在售/售罄产品列表查询（供马上贷）
     */
    @Test
    public void queryProductListForMSD() {
        QueryProductListRequestForP2P req = new QueryProductListRequestForP2P();
        req.setSaleFlag("ON_SALE"); //在售/售罄区分
        req.setOrderByYieldRate("");//按照预期年化收益率排序(ASC:预期年化收益率升序，DESC：预期年化收益率降序)
        req.setOrderByInvestPeriod("");//按照产品期限排序(ASC:产品期限升序，DESC：产品期限降序)
        req.setOrderByMinInvestAmount("");//按照起投金额排序(ASC:起投金额升序，DESC：起投金额降序)
        req.setPageNo(1);//页码(当前页)
        req.setPageSize(10);//分页大小(每页数量)
        req.setSortField(null);//排序字段
        req.setSortType(null);//排序方式,1.升序,2.降序
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        PageQueryResponse<ProductModel> response = productServiceForP2PFacade.queryProductListForMSD(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 货架系统 产品详情查询
     */
//    @Test
    public void queryProductInfo() {
        QueryProductInfoRequest req = new QueryProductInfoRequest();
        req.setProductCode("0518040007");
        req.setStatus("");
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfo(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(req));
    }
    
    /**
     * 货架系统 产品详情查询【供交易】
     */
    @Test
    public void queryProductInfoForTrade() {
    	QueryProductInfoForTradeRequest req = new QueryProductInfoForTradeRequest();
        req.setAssetPoolCode("");//资产池编号
        req.setInvestPeriod(0);//投资期限
        req.setValueTime(new Date());//产品起息时间
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfoForTrade(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(response));
    }

    
    /**
     * 供订单系统调用 库存售完通知产品
     */
    @Test
    public void noticeProductStockSellout() {
    	NoticeProductStockSelloutRequest req = new NoticeProductStockSelloutRequest();
        List<String> list = new ArrayList<>();
        list.add("0218060005");
        list.add("0518050074");
        req.setProductCodeList(list);
        System.out.println("供订单系统调用 库存售完通知产品请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.noticeProductStockSellout(req);
        System.out.println("供订单系统调用 库存售完通知产品响应对象：" + JsonUtils.object2Json(req));
    }
    
    

}
