package com.zb.p2p.service.topic.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.zb.p2p.queue.Action;
import com.zb.p2p.queue.MnsMsgHandler;
import com.zb.p2p.service.order.OrderAsyncService;

import lombok.extern.slf4j.Slf4j;

 
/**
 * 资产匹配完成   通知
 * @author tangqingqing
 *
 */
@Slf4j
public class AssetMatchCompleteNotifyListener implements MnsMsgHandler {
	
    @Autowired
    private OrderAsyncService orderAsyncService;

    @Override
    public Action consume(String jsonStr) throws Exception {
    	
    	try {

        	String str = JSON.parseObject(jsonStr, String.class);
        	
    		orderAsyncService.handlerEvent(str);
		} catch (Exception e) {
			return Action.ReconsumeLater;
		}
        
        return Action.CommitMessage;
    }
}
