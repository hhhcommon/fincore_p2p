package com.zb.fincore.pms.topic.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.ProductCacheServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能: 刷新产品缓存主题消息监听器
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/12 0012 09:45
 * 版本: V1.0
 */
public class RefreshProductCacheListener implements MessageListener {

    @Autowired
    private ProductCacheServiceFacade productCacheServiceFacade;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        BaseResponse baseResponse = productCacheServiceFacade.refreshProductCache();
        if (baseResponse.isSuccess()) {
            return Action.CommitMessage;
        } else {
            return Action.ReconsumeLater;
        }
    }
}
