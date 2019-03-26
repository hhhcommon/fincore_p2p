package com.zb.p2p.service.contract.check;

import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.InvestContractReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.contract.ContractDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by mengkai on 2017/9/1.
 */
@Component
public class InvestContractChecker {

    public CommonResp<ContractDTO> checkQueryInvestContractReq(InvestContractReq req){
        CommonResp<ContractDTO> response = new CommonResp<>();
        if (req == null) {
            response.setCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode());
            response.setMessage(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getDesc());
            return response;
        }
        if (StringUtils.isBlank(req.getMemberId())) {
            response.setCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode());
            response.setMessage("投资人会员id不能为空");
            return response;
        }
        if (StringUtils.isBlank(req.getProductCode())) {
            response.setCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode());
            response.setMessage("产品编码不能为空");
            return response;
        }
        return null;
    }


}
