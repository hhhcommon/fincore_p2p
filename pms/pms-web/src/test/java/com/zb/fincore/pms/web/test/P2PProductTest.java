package com.zb.fincore.pms.web.test;

import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.common.enums.ProductJoinChannelEnum;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
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
public class P2PProductTest extends AbstractJunitTest {

    @Autowired
    ProductServiceForP2PFacade productServiceForP2PFacade;

    @Autowired
    ProductJobServiceFacade productJobServiceFacade;
    

    /**
     * 货架系统 产品注册
     */
//    @Ignore
    @Test
    public void registerProductTest() {
        RegisterProductRequest req = new RegisterProductRequest();
//        产品募集起始时间必须小于等于募集截止时间
//        产品募集截止时间必须小于等于起息时间 
//        产品起息时间必须小于预期到期日期
//        产品预期到期日期必须小于等于产品预期结清日期
        //----------产品主信息表
        req.setProductName("P2P定期产品0425007");
        req.setProductDisplayName("P2P定期产品展示名称0425007");
        req.setProductLineId(6L);
        req.setAssetPoolType(2);
        req.setAssetPoolCode("AMP17P2P0001");
        req.setAssetPoolName("p2p-21天");
        req.setPatternCode("02");//产品的类型代码(01:现金管理, 02:定期类, 03:净值型,04:阶梯收益)
        req.setSaleChannelCode("MSD");
        req.setJoinChannelCode(ProductJoinChannelEnum.ZB_PERIODIC.getCode());//接入渠道(01:资邦阶梯, 02:资邦定期)
        req.setTotalAmount(new BigDecimal(2000000));
        req.setRiskLevel("1");
//        req.setFundSettleParty("sdf");//资金结算方,这期没有
        req.setIsOpenHMT(0);
        req.setCalendarMode(10);//日历模式(默认10:自然日)
        //----------产品期限信息表
        req.setSaleStartTime(DateUtils.parse("2017-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));//募集起始时间
        req.setSaleEndTime(DateUtils.parse("2017-10-21 10:00:00", "yyyy-MM-dd HH:mm:ss"));//募集截止时间
        req.setValueTime(DateUtils.parse("2017-10-21 10:00:00", "yyyy-MM-dd HH:mm:ss"));//起息时间
        req.setExpectExpireTime(DateUtils.parse("2017-10-22 00:00:00", "yyyy-MM-dd HH:mm:ss"));//预期到期日期
        req.setExpectClearTime(DateUtils.parse("2017-10-22 10:00:00", "yyyy-MM-dd HH:mm:ss"));//预期结清时间=注册也中到期还款日
        //----------产品投资收益信息表
        req.setMinInvestAmount(new BigDecimal("1000"));//起投金额
        req.setSingleMaxInvestAmount(new BigDecimal("50000"));//单笔投资限额,不是必填
        req.setMaxInvestAmount(new BigDecimal("100000"));//个人最大投资限额
        req.setIncreaseAmount(new BigDecimal("1000"));//递增金额(步长)
        req.setMinYieldRate(new BigDecimal("0.05"));//最低预期收益率
//        req.setMaxYieldRate(new BigDecimal("0.055"));//最高预期收益率，这期只取最低预期收益率
        
        req.setMinHoldAmount(new BigDecimal("1000"));//最低可持有金额
        req.setBasicInterestsPeriod(360);//年基础计息周期(360, 365, 366三种枚举值)
        req.setUnit(2);//计量单位(1:份额, 2:人民币元)
        req.setProfitType(2);//收益方式:1:T+N,2:固定起息日
        req.setCalculateInvestType(1);//计息方式:1:一次性还本付息；2:按季付息 到期还本；3:按月付息，到期还本；4:等额本息
        
        ProductContractModel p = new ProductContractModel();
        p.setContractType(1);
        p.setContractName("合同名称");
        p.setContractDisplayName("合同展示名称");
        p.setContractFileUrl("http://192.168.0.75/contract/aa.txt");

        List<ProductContractModel> list = new ArrayList<ProductContractModel>();
        list.add(p);
        req.setProductContractList(list);


        System.out.println("定期产品注册请求对象：" + JsonUtils.object2Json(req));
        
        RegisterProductResponse response = productServiceForP2PFacade.registerProduct(req);
        
        System.out.println("定期产品注册响应对象：" + JsonUtils.object2Json(response));
    }
    
    /**
     * 货架系统 产品详情查询
     */
//    @Test
    public void queryProductInfo() {
        QueryProductInfoRequest req = new QueryProductInfoRequest();
        req.setProductCode("0217090005");
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfo(req);
    }
    
    /**
     * 货架系统 产品审核
     */
//    @Test
    public void approveProduct() {
        ApproveProductRequest req = new ApproveProductRequest();
        req.setProductCode("0217090005");
        req.setApprovalStatus(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        req.setApprovalSuggestion("审核通过");
        req.setApprovalBy("1");
        req.setSign("A");
        BaseResponse response = productServiceForP2PFacade.approveProduct(req);
    }
    

}
