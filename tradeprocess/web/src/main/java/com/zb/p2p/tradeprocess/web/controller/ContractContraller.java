package com.zb.p2p.tradeprocess.web.controller;

import com.zb.p2p.entity.ContractEntity;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.ContractReq;
import com.zb.p2p.facade.api.req.InvestContractReq;
import com.zb.p2p.facade.api.req.LoanContractReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.contract.ContractDTO;
import com.zb.p2p.facade.api.resp.contract.ContractParamDTO;
import com.zb.p2p.facade.api.resp.contract.LoanContractDTO;
import com.zb.p2p.service.contract.ContractService;
import com.zb.p2p.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mengkai on 2017/8/31.
 */
@RestController
@RequestMapping("/contract/")
public class ContractContraller {

//    @Autowired
//    private ContractFacadeImpl contractFacadeServiceImpl;
    
    @Autowired
    private ContractService contractService;
    
    @Value("${repay.deadline}")
    private String repayDeadline; 
    
    @Value("${overdue.interest.rate}")
    private String overdueInterestRate; 
    
    @Value("${overdue.days.for.terminate.contract}")
    private String overdueDaysForTerminateContract; 
    
    /**
     * 查询借款合同
     * @param contractReq
     * @return
     */
    @RequestMapping(value = "queryLoanContract" , method = RequestMethod.POST)
    public GenericResp queryContract(@RequestBody ContractReq contractReq){
    	
    	ContractEntity contractEntity = contractService.queryContract(contractReq.getCreditorNo());
    	
    	CommonResp commonResp = new CommonResp();
    	
    	if(contractEntity == null){
    		commonResp.setCode(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode());
            commonResp.setMessage(ResponseCodeEnum.RESPONSE_NOT_FUND.getDesc());
    	}else{
    		commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
            commonResp.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
    		commonResp.setData(contractEntity );
    	} 
        return GenericResp.convert(commonResp);
       // System.out.println(JsonUtils.objectToJsonStringSetNullToEmpty(GenericResp.convert(commonResp)  ));
//        return JsonUtils.objectToJsonStringSetNullToEmpty(GenericResp.convert(commonResp)  );
        
    }
    
    /**
     * 查询合同参数
     * @return
     */
    @RequestMapping(value = "queryContractParam" , method = RequestMethod.POST)
    public GenericResp<ContractParamDTO> queryContractParam(){
    	
    	ContractParamDTO contractParamDTO = new ContractParamDTO();
    	contractParamDTO.setOverdueDaysForTerminateContract(overdueDaysForTerminateContract);
    	contractParamDTO.setOverdueInterestRate(overdueInterestRate);
    	contractParamDTO.setRepayDeadline(repayDeadline);
    	
    	CommonResp commonResp = new CommonResp();
        commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
        commonResp.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
        commonResp.setData(contractParamDTO );
        
        return GenericResp.convert(commonResp);
    }

   /* @RequestMapping(value = "queryInvestContract" , method = RequestMethod.POST)
    public GenericResp<InvestContractDTO> queryInvestContract(@RequestBody InvestContractReq investContractReq){
        return GenericResp.convert(contractFacadeServiceImpl.queryInvestContract(investContractReq));
    }

    @RequestMapping(value = "queryLoanContract", method = RequestMethod.POST)
    public GenericResp<LoanContractDTO> queryLoanContract(@RequestBody LoanContractReq loanContractReq){
        return GenericResp.convert(contractFacadeServiceImpl.queryLoanContract(loanContractReq));
    }*/

}
