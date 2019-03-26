package com.zb.p2p.paychannel.service.commonservice;


import com.zb.p2p.paychannel.dao.domain.InterfaceRetryEntity;

import java.util.List;
import java.util.Map;

/**
 * 调用接口失败重试业务接口
 * Created by zhangxin on 2017/8/31.
 */
public interface InterfaceRetryService {

    /**
     * 保存接口调用失败重试记录
     *
     * @param interfaceRetryEntity
     * @throws Exception
     */
    void saveInterfaceRetryRecord(InterfaceRetryEntity interfaceRetryEntity) throws Exception;


    void saveInterfaceRetryRecord(String businessType, String businessNo, String requestParam, String responseParam,
                                          String productCode, String memo) throws Exception;

    /**
     * 根据条件查询 调用接口失败的记录
     *
     * @param interfaceRetryEntity
     * @return
     * @throws Exception
     */
    List<InterfaceRetryEntity> queryWaitRetryRecordListByType(InterfaceRetryEntity interfaceRetryEntity) throws Exception;

    List<InterfaceRetryEntity> queryWaitRetryRecordListByBizType(String bizList) throws Exception;

    List<InterfaceRetryEntity> queryWaitRetryRecordListByBizTypeEnd(String bizList) throws Exception;

    /**
     * 更新调用接口失败记录
     *
     * @param record
     */
    void updateByPrimaryKeySelective(InterfaceRetryEntity record);

    /**
     * 根据businessNo businessType 查询
     *
     * @param businessNo
     * @return
     */
    InterfaceRetryEntity selectByBusinessNoAndBizType(String businessNo, String businessType);

    /**
     * 更新调用接口失败记录
     *
     * @param record
     */
    void updateRetryTimesAndStatusByKey(InterfaceRetryEntity record);

    List<InterfaceRetryEntity> queryWaitRetryRecordListByParams(Map<String, Object> params);

}
