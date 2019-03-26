package com.zb.p2p.service.match;

import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.p2p.facade.api.req.AssetMatchReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.service.callback.api.resp.NotifyResp;

/**
 * 预匹配记录业务接口
 * @author zhangxin
 * @create 2017-08-31 11:00
 */
public interface MatchRecordService {

    /**
     * 资产预匹配
     */
    CommonResp<String> assetMatch(AssetMatchReq assetMatchReq);

    /**
     * 资产匹配 一天多个产品
     */
    void assetMatchProcessForMultiProduct(AssetMatchReq assetMatchReq);

    void notifyTxsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception;

    NotifyResp doNotifyTxsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception;

    void notifyAmsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception;

    BaseResponse doNotifyAmsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception;
}
