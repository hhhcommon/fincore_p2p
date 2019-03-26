package com.zb.p2p.tradeprocess.test;

import com.zb.p2p.enums.LoanContractStatusEnum;
import com.zb.p2p.facade.api.req.*;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.contract.ContractDTO;
import com.zb.p2p.facade.api.resp.contract.LoanContractDTO;
import com.zb.p2p.facade.service.internal.OrderInternalService;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.service.contract.ContractServiceImpl;
import com.zb.p2p.tradeprocess.web.TradeProcessApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mengkai on 2017/8/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeProcessApplication.class)
public class ContractTest {

    @Autowired
    ContractServiceImpl contractService;
    @Autowired
    OrderInternalService orderInternalService;


    @Test
    public void genConctor(){
           /* String assetCode = "AMA170900040";
            //校验合同是否已经生成,正在处理中
            LoanRequestDTO loanRequestDTO = orderInternalService.selectByAssetCodeForLoan(assetCode);
            if(loanRequestDTO == null){
                System.out.println("资产匹配完成接收MQ处理异常:通过资产编码"+assetCode+"查询p2p_loan_request结果为空");
                return;
            }
            if(LoanContractStatusEnum.SUCCESS.getCode().equals(loanRequestDTO.getContractStatus())
                    ||LoanContractStatusEnum.PROCESSING.getCode().equals(loanRequestDTO.getContractStatus())){
                System.out.println("资产匹配完成接收MQ处理:通过资产编码"+assetCode+"查询p2p_loan_request合同生成状态为"+loanRequestDTO.getContractStatus());
                return;
            }
            Integer version = loanRequestDTO.getVersion();
            try {
                //更新p2p_loan_request表合同状态为处理中
                int updateRes = orderInternalService.updateLoanRequestContractStatus(loanRequestDTO.getId(),LoanContractStatusEnum.PROCESSING.getCode(),loanRequestDTO.getVersion());
                if(updateRes != 0){
                    //生成合同处理
                    contractService.generateContract(assetCode,loanRequestDTO);
                }
            }catch (Exception e) {
                e.printStackTrace();
                //更新p2p_loan_request表合同状态为失败
                orderInternalService.updateLoanRequestContractStatus(loanRequestDTO.getId(),LoanContractStatusEnum.FAIL.getCode(),version+1);
            }*/

    }

    @Test
    public void queryInvestContract() {
       /* InvestContractReq investContractReq = new InvestContractReq();
        investContractReq.setMemberId("test538");
        investContractReq.setProductCode("0217090005");
        try {
            CommonResp<InvestContractDTO> a = contractService.queryInvestContract(investContractReq);
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    @Test
    public void queryLoanContract() {
       /* LoanContractReq loanContractReq = new LoanContractReq();
        loanContractReq.setMemberId("M12323");
        loanContractReq.setLoanNo("MSD1709081709027");
        try {
            CommonResp<LoanContractDTO> b = contractService.queryLoanContract(loanContractReq);
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }



}