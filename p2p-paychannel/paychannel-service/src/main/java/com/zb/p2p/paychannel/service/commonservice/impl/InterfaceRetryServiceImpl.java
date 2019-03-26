package com.zb.p2p.paychannel.service.commonservice.impl;

import com.zb.p2p.paychannel.common.enums.InterfaceRetryStatusEnum;
import com.zb.p2p.paychannel.dao.InterfaceRetryDAO;
import com.zb.p2p.paychannel.dao.domain.InterfaceRetryEntity;
import com.zb.p2p.paychannel.service.commonservice.InterfaceRetryService;
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
    private InterfaceRetryDAO interfaceRetryDAO;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void saveInterfaceRetryRecord(InterfaceRetryEntity interfaceRetryEntity) throws Exception {
        interfaceRetryDAO.insertSelective(interfaceRetryEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void saveInterfaceRetryRecord(String businessType, String businessNo, String requestParam, String responseParam,
                                         String productCode, String memo) throws Exception {
        InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
        interfaceRetryEntity.setBusinessType(businessType);
        interfaceRetryEntity.setBusinessNo(businessNo);
        interfaceRetryEntity.setRequestParam(requestParam);
        interfaceRetryEntity.setResponseParam(responseParam);
        interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
        interfaceRetryEntity.setRetryTimes(0);
        interfaceRetryEntity.setMemo(memo);
        interfaceRetryEntity.setProductCode(productCode);
        saveInterfaceRetryRecord(interfaceRetryEntity);
    }

    @Override
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByType(InterfaceRetryEntity interfaceRetryEntity) throws Exception {
        return interfaceRetryDAO.queryWaitRetryRecordListByType(interfaceRetryEntity);
    }


    @Override
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByBizType(String bizList) throws Exception {
        return interfaceRetryDAO.queryWaitRetryRecordListByBizType(bizList);
    }

    @Override
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByBizTypeEnd(String bizList) throws Exception {
        return interfaceRetryDAO.queryWaitRetryRecordListByBizTypeEnd(bizList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void updateByPrimaryKeySelective(InterfaceRetryEntity record) {
        interfaceRetryDAO.updateByPrimaryKeySelective(record);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void updateRetryTimesAndStatusByKey(InterfaceRetryEntity record) {
        interfaceRetryDAO.updateRetryTimesAndStatusByKey(record);
    }

    @Override
    public InterfaceRetryEntity selectByBusinessNoAndBizType(String businessNo,String businessType) {
        return interfaceRetryDAO.selectByBusinessNo(businessNo,businessType);
    }

    @Override
    public List<InterfaceRetryEntity> queryWaitRetryRecordListByParams(Map<String, Object> params) {
        return interfaceRetryDAO.queryWaitRetryRecordListByParams(params);
    }
}
