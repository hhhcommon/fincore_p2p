package com.zb.p2p.service.contract.check;

import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.LoanContractReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.contract.LoanContractDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by mengkai on 2017/9/1.
 */
@Component
public class LoanContractChecker {
    public CommonResp<LoanContractDTO> checkQueryLoanContractReq(LoanContractReq req){
        CommonResp<LoanContractDTO> response = new CommonResp<>();
        if (req == null) {
            response.setCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode());
            response.setMessage(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getDesc());
            return response;
        }
        if (StringUtils.isBlank(req.getMemberId())) {
            response.setCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode());
            response.setMessage("借款人id不能为空");
            return response;
        }
        if (StringUtils.isBlank(req.getLoanNo())) {
            response.setCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode());
            response.setMessage("外部资产编号不能为空");
            return response;
        }
        return null;

    }


}
