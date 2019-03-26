package com.zb.fincore.pms.web.test.p2p;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.service.txs.TXSService;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
@ActiveProfiles("dev")
public class TXSServiceTest {

    @Autowired
    private TXSService txsService ;


    /**
     * 募集结束产品下线通知唐小僧产品同步产品状态
     */
    @Test
    public void assetMatch() throws Exception{
    	StringBuffer productCodeSB = new StringBuffer();
    	productCodeSB.append("0218060004").append(",");
    	String productCodes = productCodeSB.toString();
		productCodes = productCodes.substring(0,productCodes.length()-1);

        System.out.println(JsonUtils.object2Json(productCodes));
        BaseResponse resp = txsService.syncStatusNoticeHttp(productCodes);
        System.out.println(JsonUtils.object2Json(resp));
    }



}
