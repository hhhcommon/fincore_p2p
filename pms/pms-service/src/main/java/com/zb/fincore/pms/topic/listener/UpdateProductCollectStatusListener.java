package com.zb.fincore.pms.topic.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.ProductCollectStatusEnum;
import com.zb.fincore.pms.facade.product.ProductServiceFacade;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectStatusRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能: 更新产品募集状态
 * 创建: mabiao
 * 日期: 2017/4/17 0011 11:32
 * 版本: V1.0
 */
public class UpdateProductCollectStatusListener implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(UpdateProductCollectStatusListener.class);

    @Autowired
    private ProductServiceFacade productServiceFacade;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
        	String messageBody=new String(message.getBody(), "UTF-8");
        	logger.info("UpdateProductCollectStatusListener message body:"+messageBody);
            JSONObject jsonObject = JSONObject.fromObject(messageBody);
            UpdateProductCollectStatusRequest request = (UpdateProductCollectStatusRequest) JSONObject.toBean(jsonObject, UpdateProductCollectStatusRequest.class);
            if(null == request.getCollectStatus() || null == ProductCollectStatusEnum.getEnumItem(request.getCollectStatus())){
                return Action.ReconsumeLater;
            }

            BaseResponse response = productServiceFacade.updateProductCollectStatus(request);
            if (response.isSuccess()) {
                return Action.CommitMessage;
            } else {
                return Action.ReconsumeLater;
            }
        } catch (Exception e) {
            logger.error("处理消息异常", e);
            return Action.ReconsumeLater;
        }
    }
}
