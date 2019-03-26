package com.zb.p2p.trade.client.ams;

import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.facade.dto.p2p.req.QueryAssetRepayPlanListRequest;
import com.zb.p2p.trade.client.dto.AssetBillPlanDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version AmsClientService.java v1.0 2018/4/25 15:19 Zhengwenquan Exp $
 */
@Slf4j
@Service
public class AmsClientService {

    @Autowired
    private AssetManagerClient assetManagerClient;


    /**
     * 封装查询资产还款计划(3.0)
     * @param orgAssetNo
     * @param stageNo
     * @return
     */
    public List<AssetBillPlanDto> queryRepayPlan(String orgAssetNo, Integer stageNo) {
        QueryAssetRepayPlanListRequest repayPlanListRequest = new QueryAssetRepayPlanListRequest();
        repayPlanListRequest.setPageSize(1000);
        repayPlanListRequest.setAssetCode(orgAssetNo);
        repayPlanListRequest.setCurrentRepayCount(stageNo);
        PageQueryResponse<AssetBillPlanDto> repayPlanRep = assetManagerClient.queryAssetRepayPlanList(repayPlanListRequest);
        if (repayPlanRep != null && repayPlanRep.isSuccess()) {
            return repayPlanRep.getDataList();
        }

        return null;
    }
}
