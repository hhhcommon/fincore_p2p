package com.zb.fincore.pms.topic.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.zb.fincore.pms.common.enums.ChangeProductStockStatusEnum;
import com.zb.fincore.pms.facade.product.ProductCacheServiceFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能: 占用/释放/赎回/取消 库存
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/11 0011 11:32
 * 版本: V1.0
 */
public class ChangeProductStockListener implements MessageListener {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(ChangeProductStockListener.class);

    /**
     * 产品缓存服务接口
     */
    @Autowired
    private ProductCacheServiceFacade productCacheServiceFacade;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
        	String messageBody=new String(message.getBody(), "UTF-8");
        	logger.info("ChangeProductStockListener message body:"+messageBody);
            JSONObject jsonObject = JSONObject.fromObject(messageBody);
            ChangeProductStockRequest request = (ChangeProductStockRequest) JSONObject.toBean(jsonObject, ChangeProductStockRequest.class);
//            ChangeProductStockResponse response = productCacheServiceFacade.changeProductStockWithoutFreezeRecord(request);
            ChangeProductStockResponse response = productCacheServiceFacade.changeProductStock(request);
            if (response.isSuccess()) {
                if (response.getStatus() == ChangeProductStockStatusEnum.SUCCESS.getCode() ||
                        response.getStatus() == ChangeProductStockStatusEnum.FAIL.getCode()) {
                    return Action.CommitMessage;
                } else {
                    return Action.ReconsumeLater;
                }
            } else {
                return Action.ReconsumeLater;
            }
        } catch (Exception e) {
            logger.error("处理消息异常", e);
            return Action.ReconsumeLater;
        }
    }
}
