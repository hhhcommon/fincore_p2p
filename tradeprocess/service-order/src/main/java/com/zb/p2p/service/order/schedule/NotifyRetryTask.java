package com.zb.p2p.service.order.schedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.zb.p2p.service.callback.MSDCallBackService;
import com.zb.p2p.service.callback.api.req.NotifyLoanStatusReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.enums.InterfaceRetryStatusEnum;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.order.OrderAsyncService;

import lombok.extern.slf4j.Slf4j;

/**
 * 通知重试JOB
 */
@Slf4j
@Component("notifyRetryTask")
public class NotifyRetryTask implements IScheduleTaskDealSingle<InterfaceRetryEntity> {

    @Autowired
    private InterfaceRetryService interfaceRetryService; 
    
    @Autowired
    private OrderAsyncService orderAsyncService;

    @Autowired
    private MSDCallBackService msdCallBackService;

    @Override
    public boolean execute(InterfaceRetryEntity interfaceRetryEntity, String s) throws Exception {
        String reqStr = interfaceRetryEntity.getRequestParam();
        String bizType = interfaceRetryEntity.getBusinessType();
        if (StringUtils.isBlank(reqStr) || StringUtils.isBlank(bizType)) {
            return false;
        }
        NotifyResp resp = null;
        try {
            //根据业务类型做不同的重试操作
            switch (InterfaceRetryBusinessTypeEnum.convertFromCode(bizType)) {
                /*
                 * case ACCOUNT_NOTIFY_TXS:
                    txsCallBackService.callbackInvest(JSON.parseObject(reqStr, NotifyOrderReq.class));
                    break; 
                case PRODUCT_FROZEN_STOCK_NOTIFY:
                    productCacheServiceFacade.freezeProductStock(JSON.parseObject(reqStr, FreezeProductStockRequest.class));
                    break;
                case PRODUCT_STOCK_NOTIFY:
                    productCacheServiceForP2PFacade.changeProductStock(JSON.parseObject(reqStr, ChangeProductStockForP2PRequest.class));
                    break;
                case ASSET_RELATION_NOTIFY:
                    assetForP2PFacade.saveAssetProductRelation(JSON.parseObject(reqStr, RelateAssetProductForP2PRequest.class));
                    break;
                //下面2个case逻辑相同*/
                case LOAN_PAYMENT_PROCESS_NOTIFY_MSD:
                case LOAN_SUCCESS_NOTIFY_MSD:
                    msdCallBackService.notifyLoanStatus(JSON.parseObject(reqStr, NotifyLoanStatusReq.class));
                    break;
                case LOAN_NOTIFY_PAYMENT: //放款
                	orderAsyncService.loanWithdrawalRetry( interfaceRetryEntity );;
                    break;
            }
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.SUCCESS.getCode());
            interfaceRetryEntity.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetryEntity);
        } catch (Exception e) {
            log.error("重试失败 bizType={}", bizType, e);
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.FAILURE.getCode());
            interfaceRetryEntity.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetryEntity);
        }
        return true;
    }

    @Override
    public List<InterfaceRetryEntity> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1) throws Exception {
        log.info("OrderNotifyRetryTask start running ....");
        // 做重试
//        String bizList = Joiner.on(",").join( "'LOAN_NOTIFY_PAYMENT'","'LOAN_NOTIFY_PAYMENT'"
//        		/*"LOAN_NOTIFY_PAYMENT", "PRODUCT_FROZEN_STOCK_NOTIFY",
//                "PRODUCT_STOCK_NOTIFY", "ASSET_RELATION_NOTIFY",*/
//        		 );
        
        String bizList = "'LOAN_NOTIFY_PAYMENT','LOAN_SUCCESS_NOTIFY_MSD','LOAN_PAYMENT_PROCESS_NOTIFY_MSD'";
        
        List<InterfaceRetryEntity> interfaceRetryEntityList = interfaceRetryService.queryWaitRetryRecordListByBizType(bizList);
        if (CollectionUtils.isNullOrEmpty(interfaceRetryEntityList)) {
            return new ArrayList<>(0);
        }
        log.info("OrderNotifyRetryTask本次处理任务量:{}", interfaceRetryEntityList.size());
        return interfaceRetryEntityList;
    }

    @Override
    public Comparator<InterfaceRetryEntity> getComparator() {
        return null;
    }
     
     
}
