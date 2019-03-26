package com.zb.p2p.paychannel.service.commonservice.impl;

import com.zb.p2p.paychannel.dao.OperationRecordDAO;
import com.zb.p2p.paychannel.dao.domain.OperationRecordEntity;
import com.zb.p2p.paychannel.service.commonservice.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OperationServiceImpl implements OperationService {
    @Autowired
    OperationRecordDAO operationRecordDAO;

    /**
     * 记录操作，做幂等控制
     * <br/>字段:operation_type 操作类型
     * <br/>字段:refer_id 流水号
     *
     * @param operationRecord
     */
    public boolean opRecord(OperationRecordEntity operationRecord) throws Exception {
        try {
            return operationRecordDAO.insert(operationRecord) == 1;
        } catch (DuplicateKeyException e) {
            log.error("幂等控制错误,操作={}", operationRecord, e);
            return false;
        }
    }
}
