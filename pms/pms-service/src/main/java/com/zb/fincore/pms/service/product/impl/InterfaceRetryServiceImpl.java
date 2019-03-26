package com.zb.fincore.pms.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.fincore.pms.common.enums.InterfaceRetryStatusEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.service.dal.dao.InterfaceRetryDao;
import com.zb.fincore.pms.service.dal.model.InterfaceRetry;
import com.zb.fincore.pms.service.order.OrderService;
import com.zb.fincore.pms.service.product.InterfaceRetryService;
import com.zb.fincore.pms.service.trade.TradeService;
import com.zb.fincore.pms.service.txs.TXSService;
import com.zb.p2p.match.api.req.AssetMatchReq;

/**
 * 调用接口失败重试业务实现类
 *
 * @author
 * @create 2017-10-12 9:55
 */
@Service
public class InterfaceRetryServiceImpl implements InterfaceRetryService {

    private static Logger logger = LoggerFactory.getLogger(InterfaceRetryServiceImpl.class);

    @Autowired
    private InterfaceRetryDao interfaceRetryDao;

    @Autowired
    private TradeService tradeService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private TXSService txsService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void saveInterfaceRetryRecord(InterfaceRetry interfaceRetry) throws Exception {
        interfaceRetryDao.insertSelective(interfaceRetry);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void updateByPrimaryKeySelective(InterfaceRetry record) {
        interfaceRetryDao.updateByPrimaryKeySelective(record);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void updateRetryTimesAndStatusByKey(InterfaceRetry record) {
        interfaceRetryDao.updateRetryTimesAndStatusByKey(record);
    }

    @Override
    public List<InterfaceRetry> queryWaitRetryRecordListByType(InterfaceRetry interfaceRetry) throws Exception {
        return interfaceRetryDao.queryWaitRetryRecordListByType(interfaceRetry);
    }

    @Override
    public List<InterfaceRetry> queryWaitRetryRecordListByBizType(String bizList) throws Exception {
        return interfaceRetryDao.queryWaitRetryRecordListByBizType(bizList);
    }

    @Override
    public List<InterfaceRetry> queryWaitRetryRecordListByBizTypeEnd(String bizList) throws Exception {
        return interfaceRetryDao.queryWaitRetryRecordListByBizTypeEnd(bizList);
    }

    @Override
    public InterfaceRetry selectByBusinessNoAndBizType(String businessNo,String businessType) {
        return interfaceRetryDao.selectByBusinessNo(businessNo,businessType);
    }

    @Override
    public InterfaceRetry selectByProductCodes(String ProductCode) {
        return interfaceRetryDao.selectByProductCodes(ProductCode);
    }

    @Override
    public List<InterfaceRetry> queryWaitRetryRecordListByParams(Map<String, Object> params) {
        return interfaceRetryDao.queryWaitRetryRecordListByParams(params);
    }

    @Override
    public BaseResponse putNotifyRetry() {
        try {
            String bizList = "'ASSET_MATCH_NOTIFY_TRADE','OPEN_PRODUCT_PLAN_NOTIFY_ORDER','TXS_SYNC_STATUS_NOTIFY'";
            List<InterfaceRetry> interfaceRetryList = this.queryWaitRetryRecordListByBizType(bizList);
            if (CollectionUtils.isNullOrEmpty(interfaceRetryList)) {
                return BaseResponse.build();
            }
            for (InterfaceRetry interfaceRetry : interfaceRetryList) {
                ((InterfaceRetryService)(AopContext.currentProxy())).doPutNotifyRetry(interfaceRetry);
            }
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
        return BaseResponse.build();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void doPutNotifyRetry(InterfaceRetry interfaceRetry) throws Exception {
        String reqStr = null;
        String bizType = null;
        BaseResponse baseResp = null;
        try {
                reqStr = interfaceRetry.getRequestParam();
                bizType = interfaceRetry.getBusinessType();
                if (StringUtils.isBlank(reqStr) || StringUtils.isBlank(bizType)) {
                    return ;
                }
                //根据业务类型做不同的重试操作
                switch (InterfaceRetryBusinessTypeEnum.convertFromCode(bizType)) {
                    case ASSET_MATCH_NOTIFY_TRADE:
                    	baseResp = tradeService.assetMatchHttp(JSON.parseObject(reqStr, AssetMatchReq.class));
                        break;
                    case OPEN_PRODUCT_PLAN_NOTIFY_ORDER:
                    	baseResp = orderService.newProductHttp(JSON.parseObject(reqStr, String.class));
                    case TXS_SYNC_STATUS_NOTIFY:
                    	baseResp = txsService.syncStatusNoticeHttp(JSON.parseObject(reqStr, String.class));
                }
                String respCode = baseResp.getRespCode();
                String respMsg = baseResp.getRespMsg();
                if (!Constants.SUCCESS_RESP_CODE.equals(respCode)) {
                	throw new BusinessException(respCode, respMsg);
	            }
                interfaceRetry.setStatus(InterfaceRetryStatusEnum.SUCCESS.getCode());
                interfaceRetry.setResponseParam(null != baseResp ? JSONObject.toJSONString(baseResp) : null);
                this.updateRetryTimesAndStatusByKey(interfaceRetry);
        } catch (Exception e) {
            logger.error("重试失败 bizType={}", bizType, e.getMessage());
            interfaceRetry.setStatus(InterfaceRetryStatusEnum.FAILURE.getCode());
            interfaceRetry.setResponseParam(null != baseResp ? JSONObject.toJSONString(baseResp) : null);
            interfaceRetry.setRetryTimes(interfaceRetry.getRetryTimes()+1);
            interfaceRetryDao.updateRetryTimesAndStatusByKey(interfaceRetry);
        }
    }
}
