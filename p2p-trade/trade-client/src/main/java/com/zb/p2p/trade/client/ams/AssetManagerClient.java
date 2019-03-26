package com.zb.p2p.trade.client.ams;

import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.facade.dto.p2p.req.QueryAssetRepayPlanListRequest;
import com.zb.fincore.ams.facade.dto.req.UpdateCashStatusRequest;
import com.zb.p2p.trade.client.dto.AssetBillPlanDto;
import com.zb.p2p.trade.client.request.NotifyLoanStatusReq;
import com.zb.p2p.trade.common.model.CommonResp;
import feign.Headers;
import feign.RequestLine;

/**
 * <p> 调用资产服务Client </p>
 *
 * @author Vinson
 * @version ProductManagerClient.java v1.0 2018/4/18 14:20 Zhengwenquan Exp $
 */
public interface AssetManagerClient {

    /**
     * 3.0资产还款计划
     * @param var1
     * @return
     */
    @RequestLine("POST /p2p/internal/queryLoanRepayPlanList.json")
    @Headers("Content-Type: application/json")
    PageQueryResponse<AssetBillPlanDto> queryAssetRepayPlanList(QueryAssetRepayPlanListRequest var1);

    @RequestLine("POST /internal/updateCashStatus")
    @Headers("Content-Type: application/json")
    BaseResponse updateCashStatus(UpdateCashStatusRequest var1);

    /**
     * 通知资管更新资产放款状态为“放款成功或放款失败”
     * @param req
     * @return
     */
    @RequestLine("POST /internal/updateLoanInfo.json")
    @Headers("Content-Type: application/json")
    CommonResp notifyAssertLoanStatus(NotifyLoanStatusReq req);

}
