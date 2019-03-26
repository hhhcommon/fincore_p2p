package com.zb.p2p.trade.service.common.impl;

import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.persistence.dao.InterfaceRetryMapper;
import com.zb.p2p.trade.persistence.entity.InterfaceRetryEntity;
import com.zb.p2p.trade.service.common.InterfaceRetryService;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    private InterfaceRetryMapper interfaceRetryMapper;

    @Override
    public void saveInterfaceRetryRecord(InterfaceRetryEntity interfaceRetryEntity) {
        try {
            interfaceRetryMapper.insertSelective(interfaceRetryEntity);
        } catch (Exception e) {
            logger.error("插入重试记录失败", e);
        }
    }

    @Override
    @ReadOnlyConnection
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByType(InterfaceRetryEntity interfaceRetryEntity) throws BusinessException {
        return interfaceRetryMapper.queryWaitRetryRecordListByType(interfaceRetryEntity);
    }


    @Override
    @ReadOnlyConnection
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByBizType(String bizList) throws BusinessException {
        return interfaceRetryMapper.queryWaitRetryRecordListByBizType(bizList);
    }

    @Override
    @ReadOnlyConnection
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByBizTypeEnd(String bizList) throws BusinessException {
        return interfaceRetryMapper.queryWaitRetryRecordListByBizTypeEnd(bizList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void updateByPrimaryKeySelective(InterfaceRetryEntity record) {
        interfaceRetryMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void updateRetryTimesAndStatusByKey(InterfaceRetryEntity record) {
        interfaceRetryMapper.updateRetryTimesAndStatusByKey(record);
    }

    @Override
    public InterfaceRetryEntity selectByBusinessNoAndBizType(String businessNo,String businessType) {
        return interfaceRetryMapper.selectByBusinessNo(businessNo,businessType);
    }

    @Override
    @ReadOnlyConnection
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByParams(Map<String, Object> params) {
        return interfaceRetryMapper.queryWaitRetryRecordListByParams(params);
    }
}
