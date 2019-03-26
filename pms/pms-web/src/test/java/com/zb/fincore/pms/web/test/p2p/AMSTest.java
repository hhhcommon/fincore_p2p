package com.zb.fincore.pms.web.test.p2p;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.dto.p2p.req.CancelAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.CreateAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.SynAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.resp.CreateAssetProductRelationResponse;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zb.fincore.ams.facade.dto.resp.BaseP2PResponse;
import com.zb.fincore.ams.facade.model.PoolModel;
import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.service.ams.AMSService;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
@ActiveProfiles("dev")
public class AMSTest {

    @Autowired
    private AMSService amsService ;
    
    
    /**
     * 产品注册时，通知资管系统产品与借款订单关系 V3.0
     */
    @Test
    public void createAssetProductRelationHttp() throws Exception{
    	CreateAssetProductRelationRequest req = new CreateAssetProductRelationRequest();
		req.setPoolCode("MSD2017001");
		req.setProductAmount(new BigDecimal("1"));
		req.setProductCode("0518040007");
		req.setProductType("OUT");
    	
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        CreateAssetProductRelationResponse resp = amsService.createAssetProductRelationHttp(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(resp));
    }
    
    /**
     * 产品审核不通过时，通知资管系统 V3.0
     */
    @Test
    public void cancelAssetProductRelationHttp() throws Exception{
    	CancelAssetProductRelationRequest req = new CancelAssetProductRelationRequest();
    	req.setProductCode("0518040007");
    	
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse resp = amsService.cancelAssetProductRelationHttp(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(resp));
    }
    
    /**
     * 产品审核通过时，通知资管系统 V3.0
     */
    @Test
    public void synAssetProductRelation() throws Exception{
    	SynAssetProductRelationRequest req = new SynAssetProductRelationRequest();
    	req.setProductCode("0518040007");
    	req.setProductName("P2PN复投计划定期产品-0518040002");
    	
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        BaseResponse resp = amsService.synAssetProductRelation(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(resp));
    }
    
    /**
     * 查询资产池 V3.0
     */
    @Test
    public void queryPoolInfoHttp() throws Exception{
    	QueryPoolRequest req = new QueryPoolRequest();
    	req.setPoolCode("AMP180400002");
    	
        System.out.println("请求对象：" + JsonUtils.object2Json(req));
        QueryResponse<PoolModel> resp = amsService.queryPoolInfoHttp(req);
        System.out.println("响应对象：" + JsonUtils.object2Json(resp));
    }
    
    

}
