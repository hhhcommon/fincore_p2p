package com.zb.p2p.trade.service.contract;


import com.zb.p2p.trade.api.req.ContractReq;
import com.zb.p2p.trade.api.req.LoanContractReq;
import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.persistence.entity.ContractEntity;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;

import java.util.List;

/**
 * Created by mengkai on 2017/9/20.
 */
public interface ContractService {


    /**
     * 生成原始资产默认合同
     * @param loanRequestEntity
     * @param matchRecordList
     * @return
     * @throws Exception
     */
    void generateDefaultContract(LoanRequestEntity loanRequestEntity, List<MatchRecord> matchRecordList) throws BusinessException;

    /**
     * 查询合同信息
     * @param creditorNo
     * @return
     */
    ContractEntity queryContract(String creditorNo);

    /**
     * 查询投资合同信息
     * @param extOrderNo
     * @return
     */
    ContractEntity queryContractByExtOrderNo(String extOrderNo);

    /**
     * 根据借款单号查看合同
     * @param req
     * @return
     */
    CommonResp viewContractByLoanNo(LoanContractReq req);

    /**
     * 根据债权编号查看合同
     * @param creditorNo
     * @return
     */
    CommonResp viewContractByCreditorNo(String creditorNo);

    /**
     * 根据投资订单编号查看合同
     * @param req
     * @return
     */
    CommonResp viewContractByOrderNo(ContractReq req);

    /**
     * 查询合同信息
     * @param loanNo
     * @return
     */
    List<ContractEntity> queryContractByLoanNo(String loanNo);

    void signContract(String creditorNo);

    /**
     * 根据合同状态查询合同列表
     * @param status
     * @return
     */
    List<ContractEntity> selectByStatus(String status);
}
