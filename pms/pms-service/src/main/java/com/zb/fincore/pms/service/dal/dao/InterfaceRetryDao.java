package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.InterfaceRetry;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InterfaceRetryDao {
    int deleteByPrimaryKey(Long id);

    int insert(InterfaceRetry record);

    int insertSelective(InterfaceRetry record);

    InterfaceRetry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InterfaceRetry record);

    int updateByPrimaryKey(InterfaceRetry record);

    List<InterfaceRetry> queryWaitRetryRecordListByType(InterfaceRetry record);

    List<InterfaceRetry> queryWaitRetryRecordListByBizType(@Param("businessType") String bizList);

    List<InterfaceRetry> queryWaitRetryRecordListByParams(Map<String, Object> params);

    List<InterfaceRetry> queryWaitRetryRecordListByBizTypeEnd(@Param("bizList") String bizList);

    InterfaceRetry selectByBusinessNo(@Param("businessNo") String businessNo, @Param("businessType") String businessType);

    InterfaceRetry selectByProductCodes(@Param("ProductCode") String ProductCode);

    int updateRetryTimesAndStatusByKey(InterfaceRetry record);

    int queryRetryFailureCountByProdCodeForDayCut(InterfaceRetry record);
}