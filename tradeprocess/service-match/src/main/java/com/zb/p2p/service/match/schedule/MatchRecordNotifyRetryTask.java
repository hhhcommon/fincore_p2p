package com.zb.p2p.service.match.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.common.exception.BusinessException;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.enums.*;
import com.zb.p2p.facade.api.req.AssetMatchReq;
import com.zb.p2p.service.callback.api.req.*;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.match.AssetMatchService;
import com.zb.p2p.service.match.MatchRecordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 资产预匹配结果通知唐小僧重试JOB
 * @author zhangxin
 * @create 2017-09-07 17:23
 */
@Component("matchRecordNotifyRetryTask")
public class MatchRecordNotifyRetryTask implements IScheduleTaskDealSingle<InterfaceRetryEntity>{
    private static Logger logger = LoggerFactory.getLogger(MatchRecordNotifyRetryTask.class);

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private AssetMatchService assetMatchService;

    @Autowired
    private MatchRecordService matchRecordService;

    @Override
    public boolean execute(InterfaceRetryEntity interfaceRetryEntity, String s) throws Exception {

        String reqStr = interfaceRetryEntity.getRequestParam();
        String bizType = interfaceRetryEntity.getBusinessType();
        if (StringUtils.isBlank(reqStr) || StringUtils.isBlank(bizType)) {
            return false;
        }
        NotifyResp resp = null;
        BaseResponse baseResponse = null;
        try {
            //根据业务类型做不同的重试操作
            switch (InterfaceRetryBusinessTypeEnum.convertFromCode(bizType)) {
                case ASSET_MATCH_NOTIFY_TXS:
                    resp = matchRecordService.doNotifyTxsAssetMatchResult(JSON.parseObject(reqStr, AssetMatchReq.class));
                    if (null != resp && !"0000".equals(resp.getCode())) {
                        throw new BusinessException(ResultCodeEnum.FAIL.code(), "重试通知TXS投资匹配结果调用失败:" + resp.getMsg());
                    }
                    break;
                case ASSET_MATCH_NOTIFY_MSD:
                    //第一步失败的节点
                    resp = assetMatchService.doNotifyMsdAssetMatchResult(JSON.parseObject(reqStr, NotifyMsdAssetMatchResultReq.class));
                    if (null != resp && !"0000".equals(resp.getCode())) {
                        throw new BusinessException(ResultCodeEnum.FAIL.code(), "重试通知马上贷投资匹配结果调用失败:" + resp.getMsg());
                    }
                    break;
                case ASSET_MATCH_NOTIFY_AMS:
                    baseResponse = matchRecordService.doNotifyAmsAssetMatchResult(JSON.parseObject(reqStr, AssetMatchReq.class));
                    if (null != resp && !"0000".equals(baseResponse.getRespCode())) {
                        throw new BusinessException(ResultCodeEnum.FAIL.code(), "重试通知AMS投资匹配结果调用失败:" + baseResponse.getRespMsg());
                    }
                    break;
            }
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.SUCCESS.getCode());
            interfaceRetryEntity.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetryEntity);
        } catch (Exception e) {
            logger.error("重试失败 bizType={}", bizType, e);
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.FAILURE.getCode());
            interfaceRetryEntity.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryService.updateRetryTimesAndStatusByKey(interfaceRetryEntity);
        }
        return true;

    }

    @Override
    public List<InterfaceRetryEntity> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1) throws Exception {
        logger.info("MatchRecordNotifyRetryTask start running ....");

        Map<String, Object> params = new HashMap<>();
        List<String> businessTypeList= new ArrayList<>();
        businessTypeList.add("ASSET_MATCH_NOTIFY_AMS");
        businessTypeList.add("ASSET_MATCH_NOTIFY_TXS");
        businessTypeList.add("ASSET_MATCH_NOTIFY_MSD");
        params.put("businessTypeList", businessTypeList);
        List<InterfaceRetryEntity> interfaceRetryEntityList = interfaceRetryService.queryWaitRetryRecordListByParams(params);
        if (CollectionUtils.isNullOrEmpty(interfaceRetryEntityList)) {
            return new ArrayList<>(0);
        }
        logger.info("MatchRecordNotifyRetryTask本次处理任务量:{}", interfaceRetryEntityList.size());
        return interfaceRetryEntityList;
    }

    @Override
    public Comparator<InterfaceRetryEntity> getComparator() {
        return null;
    }
}
