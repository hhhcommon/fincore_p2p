package com.zb.fincore.pms.web.test.p2p;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.service.trade.TradeService;
import com.zb.p2p.match.api.req.AssetMatchReq;
import com.zb.p2p.match.api.req.ProductAssetMatchDTO;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
@ActiveProfiles("dev")
public class TradeTest {

    @Autowired
    private TradeService tradeService ;
    
    
    /**
     * 资产预匹配
     */
    @Test
    public void assetMatch() throws Exception{
    	AssetMatchReq assetMatchReq = new AssetMatchReq();
    	List<ProductAssetMatchDTO> productAssetMatchDTOList = new ArrayList<ProductAssetMatchDTO>();
    	
    	ProductAssetMatchDTO dto1 = new ProductAssetMatchDTO();
    	dto1.setProductCode("0518040007");
    	dto1.setProductSource(PatternCodeTypeEnum.N_LOOP_PLAN.getCode());
        productAssetMatchDTOList.add(dto1);
        
        ProductAssetMatchDTO dto2 = new ProductAssetMatchDTO();
        dto2.setProductCode("0518040015");
        dto2.setProductSource(PatternCodeTypeEnum.N_LOOP_PLAN.getCode());
        productAssetMatchDTOList.add(dto2);
        
        assetMatchReq.setProductAssetMatchDTOList(productAssetMatchDTOList);
        
        System.out.println("资产预匹配请求对象：" + JsonUtils.object2Json(assetMatchReq));
        BaseResponse resp = tradeService.assetMatchHttp(assetMatchReq);
        System.out.println("资产预匹配产品响应对象：" + JsonUtils.object2Json(assetMatchReq));
    }
    
    

}
