package com.zb.p2p.facade.service;

import com.zb.p2p.facade.api.req.AssetMatchReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.service.internal.dto.MatchRecordDTO;

import java.util.List;

/**
 * 资产预匹配facade
 */
public interface MatchRecordFacade {

    /**
     * 资产预匹配
     *
     * @return
     */
    CommonResp<String> assetMatch(AssetMatchReq assetMatchReq);

    /**
     *
     * 拉取未生成兑付计划的债权信息
     *
     * @param taskItemList
     * @param eachFetchDataNum
     * @return
     */
    List<MatchRecordDTO> listMatchRecords(List taskItemList, int eachFetchDataNum);

    void updateMatchRecord(MatchRecordDTO matchRecordDTO) throws Exception;
}
