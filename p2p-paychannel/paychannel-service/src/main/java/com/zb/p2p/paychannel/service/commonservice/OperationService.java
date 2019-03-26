package com.zb.p2p.paychannel.service.commonservice;


import com.zb.p2p.paychannel.dao.domain.OperationRecordEntity;

public interface OperationService {
    boolean opRecord(OperationRecordEntity operationRecord) throws Exception;
}
