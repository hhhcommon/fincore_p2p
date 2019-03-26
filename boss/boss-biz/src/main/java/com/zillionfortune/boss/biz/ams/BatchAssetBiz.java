package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.*;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * 资产批量录入业务处理
 * Created by MABIAO on 2017/6/30 0030.
 */
public interface BatchAssetBiz {

    public BaseWebResponse importBatchAsset(ImportBatchAssetRequest req);

    public BaseWebResponse queryCreditAssetListForApproval(QueryCreditAssetListRequest req);

    public BaseWebResponse approveCreditAsset(ApprovalCreditAssetRequest req);

    public BaseWebResponse selectByFileName(String fileName);

    public BaseWebResponse insertAssetFile(String fileName);

    public BaseWebResponse createFileTemplateParam(CreateFileTemplateParamRequest req);

    public BaseWebResponse generateContract(GenerateContractRequest req);

    public BaseWebResponse queryAssetContractList(QueryAssetContractListRequest req);

    public BaseWebResponse queryUnPackageAssetList(QueryUnPackageAssetRequest req);

    public BaseWebResponse queryRecordAssetList(QueryRecordAssetListRequest req);

    public BaseWebResponse queryFileTemplateParam(QueryFileTemplateParamRequest req);

    public BaseWebResponse registerExchangeAsset(RegisterExchangeAssetRequest req);

    public BaseWebResponse updateRegisterExchangeInfo(UpdateRegisterExchangeInfoRequest req);

    public BaseWebResponse queryUnPackageAssetDetail(QueryUnPackageAssetDetailRequest req);

    public BaseWebResponse queryExchangeRegisterRecordList(QueryExchangeRegisterRecordListRequest req);

    public BaseWebResponse queryExchangeRegisterRecordDetail(QueryExchangeRegisterDetailRequest req);

}
