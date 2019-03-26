package com.zb.p2p.service.match.facade.impl;

import com.zb.fincore.common.utils.BeanUtils;
import com.zb.p2p.common.exception.ExceptionHandler;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.facade.api.req.AssetMatchReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.service.MatchRecordFacade;
import com.zb.p2p.facade.service.internal.dto.MatchRecordDTO;
import com.zb.p2p.service.match.MatchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 资产预匹配
 */
@Service
public class MatchRecordFacadeImpl implements MatchRecordFacade {

    @Autowired
    private MatchRecordService matchRecordService;

    @Autowired
    private ExceptionHandler exceptionHandler;
    @Autowired
    private MatchRecordDAO matchRecordDAO;

    /**
     * 资产预匹配
     *
     * @return
     */
    @Override
    public CommonResp<String> assetMatch(AssetMatchReq assetMatchReq) {
        try {
            return matchRecordService.assetMatch(assetMatchReq);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    @Override
    public List<MatchRecordDTO> listMatchRecords(List taskItemList, int eachFetchDataNum) {
        try {
            return BeanUtils.copyAs(matchRecordDAO.listMatchRecords(taskItemList, eachFetchDataNum), MatchRecordDTO.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void updateMatchRecord(MatchRecordDTO matchRecordDTO) throws Exception {
            matchRecordDAO.updateByPrimaryKeySelective(BeanUtils.copyAs(matchRecordDTO, MatchRecordEntity.class));
    }
}
