package com.zb.fincore.pms.web.test;

import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.common.enums.ProductJoinChannelEnum;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import com.zb.fincore.pms.facade.product.ProductServiceFacade;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockChangeFlowModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProductTest {

    @Autowired
    ProductServiceFacade productServiceFacade;
    
    @Autowired
    ProductServiceForP2PFacade productServiceForP2PFacade;

    @Autowired
    ProductJobServiceFacade productJobServiceFacade;
    

    /**
     * 产品上线(销售状态)
     * description : 将产品状态为 已部署 设置为上线状态
     */
//    @Test
    public void putProductOnLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0417040004");
        BaseResponse response = productServiceFacade.putProductOnLine(req);
        System.out.println(response.getRespMsg());
    }

    /**
     * 产品下线(销售状态)
     * description : 将产品状态为 上线 设置为下线状态
     */
//    @Test
    public void putProductOffLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0417040004");
        BaseResponse response = productServiceFacade.putProductOffLine(req);
        System.out.println(response.getRespMsg());
    }


    /**
     * 产品待成立确认成立接口
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/confirmProductEstablished.json
     */
//    @Test
    public void confirmProductEstablished() {
        UpdateProductCollectStatusRequest req = new UpdateProductCollectStatusRequest();
        req.setProductCode("0417040004");
        BaseResponse response = productServiceFacade.confirmProductEstablished(req);
        System.out.println(response.getRespMsg());
    }

    /**
     * 产品上架接口，用于C端显示
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductDisplay.json
     */
//    @Test
    public void putProductDisplay() {
        UpdateProductDisplayStatusRequest req = new UpdateProductDisplayStatusRequest();
        req.setProductCode("0417040004");
        BaseResponse response = productServiceFacade.putProductDisplay(req);
        System.out.println(response.getRespMsg());
    }

    /**
     * 产品下架接口，用于C端显示
     */
//    @Test
    public void putProductUnDisplay() {
        UpdateProductDisplayStatusRequest req = new UpdateProductDisplayStatusRequest();
        req.setProductCode("0417040004");
        BaseResponse response = productServiceFacade.putProductUnDisplay(req);
    }

    /**
     * 货架系统 产品注册
     */
    @Test
    public void registerProduct() {
        RegisterProductRequest req = new RegisterProductRequest();
        //----------产品主信息表
        req.setProductName("产品0425001");
        req.setProductDisplayName("产品展示名称0425001");
        req.setProductLineId(4l);
        req.setAssetPoolCode("123456");
        req.setPatternCode("02");//产品的类型代码(01:现金管理, 02:定期类, 03:净值型,04:阶梯收益)
        req.setSaleChannelCode("MSD");
        req.setJoinChannelCode(ProductJoinChannelEnum.ZB_PERIODIC.getCode());//接入渠道(01:资邦阶梯, 02:资邦定期)
        req.setTotalAmount(new BigDecimal(2000000));
        req.setRiskLevel("R3");
        req.setFundSettleParty("sdf");//资金结算方
        req.setCalendarMode(1);//日历模式(10:自然日)
        //----------产品期限信息表
        req.setSaleStartTime(DateUtils.parse("2017-08-13 10:00:00", "yyyy-MM-dd HH:mm:ss"));//募集起始时间
        req.setSaleEndTime(DateUtils.parse("2017-04-14 10:00:00", "yyyy-MM-dd HH:mm:ss"));//募集截止时间
        req.setValueTime(DateUtils.parse("2017-04-16 00:00:00", "yyyy-MM-dd HH:mm:ss"));//起息时间
        req.setExpectExpireTime(DateUtils.parse("2017-04-30 00:00:00", "yyyy-MM-dd HH:mm:ss"));//预期到期日期
        req.setInvestPeriodLoopUnit(7);//投资循环周期
        req.setMinInvestAmount(new BigDecimal("1000"));//起投金额
        req.setSingleMaxInvestAmount(new BigDecimal("50000"));//单笔投资限额
        req.setMaxInvestAmount(new BigDecimal("100000"));//个人最大投资限额
        req.setIncreaseAmount(new BigDecimal("1000"));//递增金额(步长)
        //----------产品投资收益信息表
        req.setMinHoldAmount(new BigDecimal("1000"));//最低可持有金额
        req.setBasicInterestsPeriod(360);//年基础计息周期(360, 365, 366三种枚举值)
        req.setUnit(2);//计量单位(1:份额, 2:人民币元)
        req.setProfitType(2);//收益方式:1:T+N,2:固定起息日
        req.setCalculateInvestType(1);//计息方式:1:一次性还本付息；2:按季付息 到期还本；3:按月付息，到期还本；4:等额本息
        req.setMinYieldRate(new BigDecimal("0.05"));//最低预期收益率
        req.setMaxYieldRate(new BigDecimal("0.055"));//最高预期收益率
        
        ProductContractModel p = new ProductContractModel();
        p.setContractType(1);
        p.setContractName("bbbbbbbb");
        p.setContractDisplayName("aaaaaaaaa");
        p.setContractFileUrl("http://192.168.0.75/contract/aa.txt");

        List<ProductContractModel> list = new ArrayList<ProductContractModel>();
        list.add(p);
        req.setProductContractList(list);


        RegisterProductResponse response = productServiceFacade.registerProduct(req);
    }

    /**
     * 产品列表查询
     */
//    @Test
    public void queryProductList() {
        QueryProductListRequest req = new QueryProductListRequest();
        PageQueryResponse<ProductModel> response = productServiceFacade.queryProductList(req);
    }

    /**
     * 货架系统 产品详情查询
     */
//    @Test
    public void queryProductInfo() {
        QueryProductInfoRequest req = new QueryProductInfoRequest();
        req.setProductCode("");
        QueryProductInfoResponse response = productServiceFacade.queryProductInfo(req);
    }

    /**
     * 货架系统 产品信息更新
     * <p/>
     *
     * @param req
     * @return
     */
//    @Test
    public BaseResponse updateProductInfo(@RequestBody UpdateProductInfoRequest req) {
        BaseResponse response = productServiceFacade.updateProductInfo(req);
        return response;
    }

    /**
     * 货架系统 产品审核
     */
//    @Test
    public void approveProduct() {
        ApproveProductRequest req = new ApproveProductRequest();
        req.setProductCode("0417040008");
        req.setApprovalStatus(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        req.setApprovalSuggestion("aaa");
        req.setApprovalBy("1");
        req.setSign("A");
        BaseResponse response = productServiceFacade.approveProduct(req);
    }

//    /**
//     * 产品计息开始 --》产品存续期
//     * description : 将产品状态为 已成立的产品 设置为存续期状态
//     */
////    @Test
//    public void putProductInValue() {
//        UpdateProductCollectStatusRequest req = new UpdateProductCollectStatusRequest();
//        req.setProductCode("");
//        BaseResponse response = productServiceFacade.putProductInValue(req);
//    }

    /**
     * 产品募集期 开始
     * description : 将产品状态为 待募集的产品 设置为募集期状态
     *
     * @return
     */
//    @Test
    public void startProductRaising() {
        BaseResponse response = productJobServiceFacade.startProductRaising();
    }

//    /**
//     * 募集期结束 --》产品待成立
//     * description : 将产品状态为 募集期的产品 设置为待成立状态
//     *
//     * @return
//     */
////    @Test
//    public void putProductWaitingEstablish() {
//        BaseResponse response = productJobServiceFacade.putProductWaitingEstablish();
//    }

//    /**
//     * 产品存续期结束 --》产品到期  产品到期JOB调用
//     * description : 将产品状态为 存续期的产品 设置为到期状态
//     *
//     * @return
//     */
////    @Test
//    public void putProductOutValue() {
//        productJobServiceFacade.putProductOutValue();
//    }

//    /**
//     * 产品计息开始 --》产品存续期
//     * description : 将产品状态为 已成立的产品 设置为存续期状态
//     */
////    @Test
//    public void putProductInValueOfJob() {
//        productJobServiceFacade.putProductInValueOfJob();
//    }

    /**
     * 查询产品库存变更流水
     */
//    @Test
    public void queryProductStockChangeFlowList() {
        QueryProductStockChangeFlowRequest req = new QueryProductStockChangeFlowRequest();
        PageQueryResponse<ProductStockChangeFlowModel> response = productServiceFacade.queryProductStockChangeFlowList(req);
    }

//    /**
//     * 产品到期 --》产品待兑付
//     * description : 产品到期TA计算收益生成对付文件后，产品接收 修改状态“待兑付”
//     */
////    @Test
//    public void putProductWaitRedeem() {
//        UpdateProductCollectStatusRequest req = new UpdateProductCollectStatusRequest();
//        productServiceFacade.putProductWaitRedeem(req);
//    }

//    /**
//     * JOB 阶梯信息更新
//     * description : 阶梯产品每个收益阶段结束，更新为新的阶段阶梯信息
//     */
////    @Test
//    public void updateProductLadderInfo() {
//        productJobServiceFacade.updateProductLadderInfo();
//    }
}
