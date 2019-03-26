package com.zb.p2p.paychannel.dao;

import com.zb.p2p.paychannel.dao.domain.InterfaceRetryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InterfaceRetryDAO {
    int deleteByPrimaryKey(Long id);

    int insert(InterfaceRetryEntity record);

    int insertSelective(InterfaceRetryEntity record);

    InterfaceRetryEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InterfaceRetryEntity record);

    int updateByPrimaryKey(InterfaceRetryEntity record);

    List<InterfaceRetryEntity> queryWaitRetryRecordListByType(InterfaceRetryEntity record);

    List<InterfaceRetryEntity> queryWaitRetryRecordListByBizType(@Param("businessType") String bizList);

    List<InterfaceRetryEntity> queryWaitRetryRecordListByParams(Map<String, Object> params);

    List<InterfaceRetryEntity> queryWaitRetryRecordListByBizTypeEnd(@Param("bizList") String bizList);

    InterfaceRetryEntity selectByBusinessNo(@Param("businessNo") String businessNo, @Param("businessType") String businessType);

    int updateRetryTimesAndStatusByKey(InterfaceRetryEntity record);

    int queryRetryFailureCountByProdCodeForDayCut(InterfaceRetryEntity record);
}