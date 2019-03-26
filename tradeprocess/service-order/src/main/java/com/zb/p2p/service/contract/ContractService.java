package com.zb.p2p.service.contract;

import java.util.List;

import com.zb.p2p.entity.ContractEntity;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.facade.api.req.InvestContractReq;
import com.zb.p2p.facade.api.req.LoanContractReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.contract.ContractDTO;
import com.zb.p2p.facade.api.resp.contract.LoanContractDTO;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;

/**
 * Created by mengkai on 2017/9/20.
 */
public interface ContractService {

    /*CommonResp<InvestContractDTO> queryInvestContract(InvestContractReq investContractReq) throws Exception;

    CommonResp<LoanContractDTO> queryLoanContract(LoanContractReq loanContractReq) throws Exception;*/

    CommonResp generateContract(LoanRequestEntity loanRequestEntity,List<MatchRecordEntity> matchRecordEntityList) throws Exception;
    
    public ContractEntity queryContract(String creditorNo);
    
    
}
