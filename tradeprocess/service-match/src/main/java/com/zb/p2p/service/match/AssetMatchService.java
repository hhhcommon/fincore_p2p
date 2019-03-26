package com.zb.p2p.service.match;

import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.facade.service.internal.dto.OrderDTO;
import com.zb.p2p.service.callback.api.req.NotifyMsdAssetMatchResultReq;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 资产匹配服务接口
 * @author zhangxin
 * @create 2017-08-31 11:24
 */
@Service
public interface AssetMatchService {

    /**
     * 单个资产匹配
     * @param loanRequestDTO
     * @param i
     * @return
     */
    Map<String, Object> dealAssetMatchProcess(LoanRequestDTO loanRequestDTO, int i) throws Exception;

    /**
     * 资产匹配业务处理
     * @param loanRequestDTO
     * @param orderDTOList
     * @throws Exception
     */
    List<MatchRecordEntity> doAssetMatchProcess(LoanRequestDTO loanRequestDTO, List<OrderDTO> orderDTOList) throws Exception;

    /**
     * 匹配结果通知马上贷
     * @param loanRequestDTO
     * @param matchRecordList
     * @throws Exception
     */
    void notifyMsdAssetMatchResult(LoanRequestDTO loanRequestDTO, List<MatchRecordEntity> matchRecordList, String status) throws Exception;

    NotifyResp doNotifyMsdAssetMatchResult(NotifyMsdAssetMatchResultReq req) throws Exception;

}
