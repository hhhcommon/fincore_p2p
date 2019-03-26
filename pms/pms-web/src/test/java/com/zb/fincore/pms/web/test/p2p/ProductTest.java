package com.zb.fincore.pms.web.test.p2p;

import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.enums.product.ProductRiskLevelEnum;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.*;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.CancelProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.NoticeProductStockSelloutRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.web.test.AbstractJunitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
public class ProductTest extends AbstractJunitTest {

    @Autowired
    ProductServiceForP2PFacade productServiceForP2PFacade;

    @Autowired
    ProductJobServiceFacade productJobServiceFacade;


    /**
     * 货架系统 产品注册
     */
    @Test
    public void registerProductTest() {
        RegisterProductRequest req = new RegisterProductRequest();
//        产品募集起始时间必须小于等于募集截止时间
//        产品募集截止时间必须小于等于起息时间
//        产品起息时间必须小于预期到期日期
//        产品预期到期日期必须小于等于产品预期结清日期

        //----------产品主信息表
        req.setProductName("P2P定期产品0425006");
        req.setProductDisplayName("P2P定期产品展示名称0425007");
//        req.setProductLineId(6L);					//p2p这期页面没有这个字段
//        req.setAssetPoolType(2);					//p2p这期页面没有这个字段
//        req.setAssetPoolCode("AMP17P2P0001");		//p2p这期页面没有这个字段
//        req.setAssetPoolName("p2p-21天");			//p2p这期页面没有这个字段
        	req.setLoanOrderNoSet("MSD013:MSD014");//借款订单编号集,以英文 “:”分割的
        	req.setCollectMode(CollectModeEnum.collect_package.getCode());//募集方式（01:募集包）
        req.setPatternCode(PatternCodeTypeEnum.PERIODIC_REGULAR.getCode());//产品的类型代码(01:现金管理, 02:定期类, 03:净值型,04:阶梯收益)
        req.setSaleChannelCode(ChannelEnum.MSD.getCode());
        req.setJoinChannelCode(ProductJoinChannelEnum.ZB_PERIODIC.getCode());//接入渠道(01:资邦阶梯, 02:资邦定期)
        req.setTotalAmount(new BigDecimal(2000000));
        req.setRiskLevel(ProductRiskLevelEnum.PRODUCT_RISK_LEVEL_1.getCode());
//        req.setFundSettleParty("sdf");//资金结算方,这期没有
        req.setIsOpenHMT(0);
        req.setCalendarMode(10);//日历模式(程序默认10:自然日),非必填
        req.setIntroduction("产品介绍信息");//产品介绍信息,非必填
        //----------产品期限信息表
        req.setSaleStartTime(DateUtils.parse("2018-1-15 00:00:00", "yyyy-MM-dd HH:mm:ss"));//募集起始时间
        req.setSaleEndTime(DateUtils.parse("2018-1-20 23:00:00", "yyyy-MM-dd HH:mm:ss"));//募集截止时间
        req.setValueTime(DateUtils.parse("2018-1-20 00:00:00", "yyyy-MM-dd HH:mm:ss"));//起息时间
        req.setExpectExpireTime(DateUtils.parse("2018-01-25 00:00:00", "yyyy-MM-dd HH:mm:ss"));//预期到期日期
        req.setExpectEstablishTime(DateUtils.parse("2018-1-25 00:00:00", "yyyy-MM-dd HH:mm:ss")); //预订成立日
        req.setExpectClearTime(DateUtils.parse("2018-01-26 00:00:00", "yyyy-MM-dd HH:mm:ss"));//预期结清时间=注册也中到期还款日
        req.setInvestPeriod(5);//投资期限


        //----------产品投资收益信息表
        req.setMinInvestAmount(new BigDecimal("1000"));//起投金额
        req.setSingleMaxInvestAmount(new BigDecimal("50000"));//单笔投资限额,不是必填
        req.setMaxInvestAmount(new BigDecimal("100000"));//个人最大投资限额
        req.setIncreaseAmount(new BigDecimal("1000"));//递增金额(步长)
        req.setMinYieldRate(new BigDecimal("0.05"));//最低预期收益率
        req.setMaxYieldRate(new BigDecimal("0.055"));//最高预期收益率

        req.setMinHoldAmount(new BigDecimal("1000"));//最低可持有金额
        req.setBasicInterestsPeriod(360);//年基础计息周期(360, 365, 366三种枚举值)
        req.setUnit(ProductUnitEnum.RMB.getCode());//计量单位(1:份额, 2:人民币元) 默认2
        req.setProfitType(ProductProfitTypeEnum.PERIODIC_VALUE.getCode());//收益方式:1:T+N,2:固定起息日
        req.setCalculateInvestType(ProductInvestTypeEnum.ONCE_PAY_ALL.getCode());//计息方式:1:一次性还本付息；2:按季付息 到期还本；3:按月付息，到期还本；4:等额本息

        System.out.println("定期产品注册请求对象：" + JsonUtils.object2Json(req));
        RegisterProductResponse response = productServiceForP2PFacade.registerProduct(req);
        System.out.println("定期产品注册响应对象：" + JsonUtils.object2Json(response));
    }


    /**
     * 产品上线
     */
    @Test
    public void productOnLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0218010009");
        System.out.println("产品上线请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.putProductOnLine(req);
        System.out.println("产品上线响应对象：" + JsonUtils.object2Json(req));
    }

    /**
     * 产品下线
     */
    @Test
    public void productOffLine() {
        UpdateProductSaleStatusRequest req = new UpdateProductSaleStatusRequest();
        req.setProductCode("0218010009");
        System.out.println("产品下线请求对象：" + JsonUtils.object2Json(req));
        BaseResponse response = productServiceForP2PFacade.putProductOffLine(req);
        System.out.println("产品下线响应对象：" + JsonUtils.object2Json(req));
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
