package com.zb.fincore.pms.service.product;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.service.dal.model.InterfaceRetry;
import org.apache.ibatis.annotations.Param;

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
     * @param interfaceRetry
     * @throws Exception
     */
    void saveInterfaceRetryRecord(InterfaceRetry interfaceRetry) throws Exception;

    /**
     * 根据businessNo businessType 查询
     *
     * @param businessNo
     * @return
     */
    InterfaceRetry selectByBusinessNoAndBizType(String businessNo, String businessType);

    /**
     * 根据 ProductCodes 查询
     *
     * @param ProductCode
     * @return
     */
    InterfaceRetry selectByProductCodes(@Param("ProductCode") String ProductCode);

    /**
     * 更新调用接口失败记录
     *
     * @param interfaceRetry
     */
    void updateByPrimaryKeySelective(InterfaceRetry interfaceRetry);


    /**
     * 更新调用接口失败记录
     *
     * @param interfaceRetry
     */
    void updateRetryTimesAndStatusByKey(InterfaceRetry interfaceRetry);

    /**
     * 根据条件查询 调用接口失败的记录
     *
     * @param interfaceRetry
     * @return
     * @throws Exception
     */
    List<InterfaceRetry> queryWaitRetryRecordListByType(InterfaceRetry interfaceRetry) throws Exception;

    List<InterfaceRetry> queryWaitRetryRecordListByBizType(String bizList) throws Exception;

    List<InterfaceRetry> queryWaitRetryRecordListByBizTypeEnd(String bizList) throws Exception;

    List<InterfaceRetry> queryWaitRetryRecordListByParams(Map<String, Object> params);

    /**
     * 通知重试
     * description :
     * @return
     */
    BaseResponse putNotifyRetry();
    /**
     * 通知重试 JOB调用 实际处理逻辑
     * description :
     * @return
     */
    void doPutNotifyRetry(InterfaceRetry interfaceRetry) throws Exception;


}
