package com.zb.p2p.trade.web.controller;


import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.trade.api.req.ContractReq;
import com.zb.p2p.trade.api.req.LoanContractReq;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.persistence.entity.ContractEntity;
import com.zb.p2p.trade.service.contract.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class TradeController {

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = "/heartbeat", method = RequestMethod.POST, produces = "application/json")
    public CommonResp<String> heartbeat(@RequestBody Object req) {
        return new CommonResp<>();
    }

    /**
     * 查询投资合同
     * @param contractReq
     * @return
     */
    @RequestMapping(value = "contract/queryInvestContractInfo" , method = RequestMethod.POST)
    public CommonResp queryInvestContract(@RequestBody ContractReq contractReq){

        ContractEntity contractEntity = contractService.queryContractByExtOrderNo(contractReq.getExtOrderNo());

        CommonResp commonResp = new CommonResp();

        if(contractEntity == null){
            commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), ResultCodeEnum.NOT_FOUND_INFO.desc());
        }else{
            commonResp.setData(contractEntity );
        }
        return commonResp;
    }


    /**
     * 查询借款合同
     * @param contractReq
     * @return
     */
    @RequestMapping(value = "contract/queryContractInfo" , method = RequestMethod.POST)
    public CommonResp queryContract(@RequestBody ContractReq contractReq){

        ContractEntity contractEntity = contractService.queryContract(contractReq.getCreditorNo());

        CommonResp commonResp = new CommonResp();

        if(contractEntity == null){
            commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), ResultCodeEnum.NOT_FOUND_INFO.desc());
        }else{
            commonResp.setData(contractEntity );
        }
        return commonResp;
    }

    /**
     * 查看电子合同
     * @param contractReq
     * @return
     */
    @RequestMapping(value = "contract/viewContract" , method = RequestMethod.POST)
    public CommonResp viewContract(@RequestBody ContractReq contractReq){

        ContractEntity contractEntity = contractService.queryContract(contractReq.getCreditorNo());

        CommonResp commonResp = new CommonResp();

        if(contractEntity == null){
            commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), ResultCodeEnum.NOT_FOUND_INFO.desc());
        }else{
            commonResp.setData(contractEntity );
        }
        return commonResp;
    }

    /**
     * 查看电子合同
     * @param req
     * @return
     */
    @RequestMapping(value = "contract/viewContractByLoanNo" , method = RequestMethod.POST)
    public CommonResp viewContractByLoanNo(@RequestBody LoanContractReq req){
        return contractService.viewContractByLoanNo(req);
    }

    /**
     * 根据债权编号查看电子合同
     * @param req
     * @return
     */
    @RequestMapping(value = "contract/viewContractByCreditorNo" , method = RequestMethod.POST)
    public CommonResp viewContractByCreditorNo(@RequestBody ContractReq req){
        return contractService.viewContractByCreditorNo(req.getCreditorNo());
    }

    /**
     * 根据债权编号查看电子合同
     * @param req
     * @return
     */
    @RequestMapping(value = "contract/viewContractByOrderNo" , method = RequestMethod.POST)
    public CommonResp viewContractByOrderNo(@RequestBody ContractReq req){
        return contractService.viewContractByOrderNo(req);
    }

    /**
     * 电子合同签章
     * @param req
     * @return
     */
    @RequestMapping(value = "contract/signContract" , method = RequestMethod.POST)
    public void signContract(@RequestBody ContractReq req){
        contractService.signContract(req.getCreditorNo());
    }
}
