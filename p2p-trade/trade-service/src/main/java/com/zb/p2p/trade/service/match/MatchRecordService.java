package com.zb.p2p.trade.service.match;

import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.exception.BusinessException;

import java.util.List;

/**
 * 预匹配记录业务接口
 * @author zhangxin
 * @create 2017-08-31 11:00
 */
public interface MatchRecordService {

    /**
     *
     * 拉取未生成兑付计划的债权信息
     *
     * @param taskItemList
     * @param eachFetchDataNum
     * @return
     */
    List<MatchRecord> listMatchRecords(List taskItemList, int eachFetchDataNum);

    /**
     * 更新
     * @param matchRecord
     * @throws Exception
     */
    void updateMatchRecord(MatchRecord matchRecord) throws BusinessException;

    /**
     * 根据资产编号查询
     * @param assetNo
     * @return
     */
    List<MatchRecord> listMatchRecordsByAssetNo(String assetNo);
}
