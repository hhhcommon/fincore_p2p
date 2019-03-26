package com.zb.p2p.trade.service.match.impl;

import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.persistence.converter.MatchRecordConverter;
import com.zb.p2p.trade.persistence.dao.MatchRecordMapper;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import com.zb.p2p.trade.service.match.MatchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预匹配处理业务操作类
 *
 * @author zhangxin
 * @create 2017-08-31 11:24
 */
@Service
public class MatchRecordServiceImpl implements MatchRecordService {


    @Autowired
    private MatchRecordMapper matchRecordMapper;

    @Override
    @ReadOnlyConnection
    public List<MatchRecord> listMatchRecords(List taskItemList, int eachFetchDataNum) {
        return MatchRecordConverter.convertToList(matchRecordMapper.listMatchRecords(taskItemList, eachFetchDataNum));
    }

    @Override
    public void updateMatchRecord(MatchRecord matchRecordDTO) throws BusinessException {
        matchRecordMapper.updateByPrimaryKeySelective(MatchRecordConverter.convert(matchRecordDTO));
    }

    @Override
    @ReadOnlyConnection
    public List<MatchRecord> listMatchRecordsByAssetNo(String assetNo) {
        return MatchRecordConverter.convertToList(matchRecordMapper.selectListByTransferCode(assetNo));
    }

}
