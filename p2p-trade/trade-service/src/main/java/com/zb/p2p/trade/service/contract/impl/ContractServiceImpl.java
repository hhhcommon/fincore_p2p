package com.zb.p2p.trade.service.contract.impl;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.p2p.trade.api.req.ContractReq;
import com.zb.p2p.trade.api.req.LoanContractReq;
import com.zb.p2p.trade.api.resp.contract.ViewLoanContractDTO;
import com.zb.p2p.trade.api.resp.contract.ViewLoanContractResp;
import com.zb.p2p.trade.client.request.GWStampContractReq;
import com.zb.p2p.trade.client.request.ViewContractRequest;
import com.zb.p2p.trade.client.response.ContractResponseDataDto;
import com.zb.p2p.trade.client.response.GWStampContractResponse;
import com.zb.p2p.trade.client.response.ViewContractResponse;
import com.zb.p2p.trade.client.response.ViewContractResponseData;
import com.zb.p2p.trade.client.signstamper.SignStamperClient;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.domain.ContractTemplateAttribute;
import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.*;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.common.model.Page;
import com.zb.p2p.trade.common.util.SensitiveInfoUtils;
import com.zb.p2p.trade.common.util.StringUtils;
import com.zb.p2p.trade.persistence.dao.*;
import com.zb.p2p.trade.persistence.entity.*;
import com.zb.p2p.trade.service.common.DistributedSerialNoService;
import com.zb.p2p.trade.service.common.InterfaceRetryService;
import com.zb.p2p.trade.service.contract.ContractService;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by mengkai on 2017/8/31.
 * 合同服务
 */
@Service
@Slf4j
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private LoanRequestMapper loanRequestMapper;
    @Autowired
    private OrderRequestMapper orderRequestMapper;
    @Autowired
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private SignStamperClient signStamperClient;

    @Autowired
    private DistributedSerialNoService distributedSerialNoService;

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private CreditorInfoMapper creditorInfoMapper;

    @Value("${repay.deadline}")
    private String repayDeadline;
    @Value("${overdue.interest.rate}")
    private String overdueInterestRate;
    @Value("${overdue.days.for.terminate.contract}")
    private String overdueDaysForTerminateContract;

    // 插入
    private boolean insertSelective(ContractEntity contractEntity) {
        //有唯一索引去重
        try {
            int rows = contractMapper.insertSelective(contractEntity);

            Assert.isTrue(rows == 1, "合同信息保存失败");

        } catch (DuplicateKeyException e) {
            log.warn("合同插入重复[{}]", contractEntity);
        }
        // 继续处理下一个
        return true;
    }

    /**
     * 生成原始资产类型的合同
     *
     * @param loanRequest
     * @param matchRecordList
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void generateDefaultContract(LoanRequestEntity loanRequest, List<MatchRecord> matchRecordList) throws BusinessException {
        //查询产品信息
        for (MatchRecord matchRecord : matchRecordList) {

            // 查询债权信息
            CreditorInfoEntity creditorInfoEntity = creditorInfoMapper.selectByOrderAndAsset(matchRecord.getExtOrderNo(), matchRecord.getTransferCode());

            OrderRequestEntity orderRequestEntity = orderRequestMapper.selectByOrderNo(matchRecord.getOrderNo());
            String contractNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.CONTRACT);

            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setCompanyCertificateNo(loanRequest.getCompanyCertificateNo());
            contractEntity.setContractNo(contractNo);
            contractEntity.setCreditorNo(creditorInfoEntity.getCreditorNo());
            contractEntity.setExpireTime(loanRequest.getExpireDate());
            contractEntity.setExtInvestOrderNo(matchRecord.getExtOrderNo());
            contractEntity.setFinanceSubjectAddress(loanRequest.getFinanceSubjectAddress());
            contractEntity.setFinanceSubjectName(loanRequest.getFinanceSubjectName());
            contractEntity.setInvestIdentityCard(orderRequestEntity.getCertNo());
            contractEntity.setInvestMemberId(matchRecord.getMemberId());
            contractEntity.setInvestMemberName(orderRequestEntity.getName());
            contractEntity.setInvestOrderNo(matchRecord.getOrderNo());
            contractEntity.setInvestTelNo(orderRequestEntity.getTelNo());
            // 投资利率等于借款利率
            contractEntity.setInvestYearYield(loanRequest.getLoanRate());
            contractEntity.setLoanAmount(matchRecord.getMatchedAmount());
            contractEntity.setLoanFee(loanRequest.getLoanFeeRate());
            BigDecimal totalInterests = cashRecordMapper.selectByAssetOrderNo(matchRecord.getExtOrderNo(), matchRecord.getTransferCode());
            contractEntity.setActualLoanInterests(totalInterests);// 单笔应还得总利息
            contractEntity.setLoanMemberId(loanRequest.getMemberId());
            contractEntity.setLoanOrderNo(loanRequest.getLoanNo());
            contractEntity.setLoanPurpose(loanRequest.getLoanPurpose());
            contractEntity.setLoanTelNo(loanRequest.getFinanceSubjectTel());
            // 资产起息日等于放款日
            contractEntity.setLoanWithdrawTime(loanRequest.getValueStartTime());
            contractEntity.setLoanYearYield(loanRequest.getLoanRate());
            contractEntity.setLockDate(loanRequest.getLockDate());
            contractEntity.setProductCode(matchRecord.getProductCode());
            contractEntity.setProductName(loanRequest.getProductName());
            contractEntity.setRepaymentType(loanRequest.getRepaymentType());
            // 到期日+1还款到账日
            contractEntity.setRepayTime(DateUtils.addDay(loanRequest.getExpireDate(), 1));
            contractEntity.setStatus(ContractEnum.INIT.name());
            contractEntity.setValueTime(loanRequest.getValueStartTime());
            contractEntity.setRepayDeadline(repayDeadline);
            contractEntity.setOverdueInterestRate(overdueInterestRate);
            contractEntity.setOverdueDaysForTerminateContract(overdueDaysForTerminateContract);

            contractEntity.setAssetCode(loanRequest.getTransferCode());
            //有唯一索引去重，是否继续
            if (insertSelective(contractEntity)) {
                continue;
            }

//            // 合同签章
//            this.signContract(matchRecord.getCreditorNo());
        }

        // 更新放款状态及标记合同已生成
        Date loanPaymentTime = new Date();
        int rows = loanRequestMapper.updateLoanInfoStatus(loanRequest.getId(), loanRequest.getLoanStatus(),
                LoanPaymentStatusEnum.LOAN_WAIT_PAY.getCode(),
                loanPaymentTime, YesNoEnum.YES.name());
        Assert.isTrue(rows == 1, "更新放款标记及合同标记信息失败");
    }

    /**
     * 查询合同
     *
     * @param creditorNo
     * @return
     */
    public ContractEntity queryContract(String creditorNo) {
        return contractMapper.selectByCreditorNo(creditorNo);
    }

    @Override
    public ContractEntity queryContractByExtOrderNo(String extOrderNo) {
        return contractMapper.selectByExtOrderNo(extOrderNo);

    }

    @Override
    public List<ContractEntity> queryContractByLoanNo(String loanNo) {
        return contractMapper.queryContractByLoanNo(loanNo);
    }

    @Override
    public CommonResp viewContractByLoanNo(LoanContractReq req) {
        CommonResp commonResp = new CommonResp();
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(req.getLoanNo())) {
                throw new Exception("借款单号不能为空。");
            }

            ContractEntity entity = new ContractEntity();
            entity.setLoanOrderNo(req.getLoanNo());
            Page page = new Page();
            BeanUtils.copy(req, page);

            int totalCount = contractMapper.selectCount(entity);
            List<ContractEntity> contractEntityList = null;
            if (totalCount > 0) {
                contractEntityList = contractMapper.queryContractByLoanNoWithPagination(entity, page);
            }

            if (CollectionUtils.isNullOrEmpty(contractEntityList)) {
                commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), ResultCodeEnum.NOT_FOUND_INFO.desc());
                return commonResp;
            } else {
                Collection<String> documentIdCollection = CollectionUtils.collect(contractEntityList, "documentId");
                List<String> documentIdList = new ArrayList<>(documentIdCollection);
                ViewContractRequest request = new ViewContractRequest();
                request.setDocumentIdList(documentIdList);
                request.setSaleChannel("TXS");
                ViewContractResponse response = signStamperClient.viewAndDownloadContract(request);
                if (null != response && ResultCodeEnum.SUCCESS.code().equals(response.getResultCode())) {
                    ViewLoanContractResp resp = new ViewLoanContractResp();
                    resp.setPageNo(req.getPageNo());
                    resp.setPageSize(req.getPageSize());
                    resp.setTotalCount(totalCount);

                    commonResp.success();
                    List<ViewLoanContractDTO> viewLoanContractDTOList = new ArrayList<>();
                    ViewContractResponseData viewContractResponseData = response.getData();
                    if (null != viewContractResponseData && !CollectionUtils.isNullOrEmpty(viewContractResponseData.getDataList())) {
                        List<ContractResponseDataDto> list = viewContractResponseData.getDataList();
                        for (ContractResponseDataDto contractResponseDataDto : list) {
                            ViewLoanContractDTO viewLoanContractDTO = new ViewLoanContractDTO();
                            viewLoanContractDTO.setViewUrl(contractResponseDataDto.getViewUrl());
                            viewLoanContractDTO.setDownloadUrl(contractResponseDataDto.getDownloadUrl());

                            ContractEntity contractEntity = CollectionUtils.find(contractEntityList, "documentId", String.valueOf(contractResponseDataDto.getDocumentId()));
                            viewLoanContractDTO.setContractNo(null == contractEntity ? "" : contractEntity.getContractNo());
                            viewLoanContractDTOList.add(viewLoanContractDTO);
                        }
                    }
                    resp.setList(viewLoanContractDTOList);

                    commonResp.setData(resp);
                } else if (null == response) {
                    commonResp.error();
                    log.error("借款单号 : {}, ----响应信息 --{}", req.getLoanNo(), response);
                } else {
                    commonResp.resetCode(response.getResultCode(), response.getResultMsg());
                }
            }
        } catch (Exception e) {
            commonResp.error();
            log.error("借款单号 : {}, ----查看借款合同异常 --{}", req.getLoanNo(), e);
        }
        return commonResp;
    }

    @Override
    public CommonResp viewContractByCreditorNo(String creditorNo) {
        CommonResp commonResp = new CommonResp();
        try {
            ContractEntity contractEntity = queryContract(creditorNo);
            if (null == contractEntity) {
                commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), ResultCodeEnum.NOT_FOUND_INFO.desc());
                return commonResp;
            } else {
                List<String> documentIdList = new ArrayList<>();
                documentIdList.add(contractEntity.getDocumentId());
                ViewContractRequest request = new ViewContractRequest();
                request.setDocumentIdList(documentIdList);
                request.setSaleChannel("TXS");
                ViewContractResponse response = signStamperClient.viewAndDownloadContract(request);
                if (null != response && ResultCodeEnum.SUCCESS.code().equals(response.getResultCode())) {
                    commonResp.success();
                    if (null != response.getData() && !CollectionUtils.isNullOrEmpty(response.getData().getDataList())) {
                        List<ContractResponseDataDto> list = response.getData().getDataList();
                        ContractResponseDataDto data = list.get(0);
                        commonResp.setData(data.getViewUrl());
                    }
                } else if (null == response) {
                    commonResp.error();
                    log.error("债权编号 : {}, ----查看借款合同响应信息 --{}", creditorNo, response);
                } else {
                    commonResp.resetCode(response.getResultCode(), response.getResultMsg());
                }
            }
        } catch (Exception e) {
            commonResp.error();
            log.error("债权编号 : {}, ----查看借款合同异常 --{}", creditorNo, e);
        }
        return commonResp;
    }

    @Override
    public CommonResp viewContractByOrderNo(ContractReq req) {
        CommonResp commonResp = new CommonResp();
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(req.getExtOrderNo())) {
                throw new Exception("投资单号不能为空。");
            }
            ContractEntity contractEntity = queryContractByExtOrderNo(req.getExtOrderNo());
            if (null == contractEntity || org.apache.commons.lang3.StringUtils.isBlank(contractEntity.getDocumentId())) {
                commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), "合同信息还未生成，请稍后重试。");
                return commonResp;
            } else {
                List<String> documentIdList = new ArrayList<>();
                documentIdList.add(contractEntity.getDocumentId());
                ViewContractRequest request = new ViewContractRequest();
                request.setDocumentIdList(documentIdList);
                request.setSaleChannel("TXS");
                ViewContractResponse response = signStamperClient.viewAndDownloadContract(request);
                if (null != response && ResultCodeEnum.SUCCESS.code().equals(response.getResultCode())) {
                    commonResp.success();
                    if (null != response.getData() && !CollectionUtils.isNullOrEmpty(response.getData().getDataList())) {
                        List<ContractResponseDataDto> list = response.getData().getDataList();
                        ContractResponseDataDto data = list.get(0);
                        commonResp.setData(data.getViewUrl());
                    }
                } else if (null == response) {
                    commonResp.error();
                    log.error("投资订单编号 : {}, ----查看借款合同响应信息 --{}", req.getExtOrderNo(), response);
                } else {
                    commonResp.resetCode(response.getResultCode(), response.getResultMsg());
                }
            }
        } catch (Exception e) {
            commonResp.error();
            log.error("投资订单编号 : {}, ----查看借款合同异常 --{}", req.getExtOrderNo(), e);
        }
        return commonResp;
    }

    /**
     * 合同盖章
     *
     * @param creditorNo
     */
    @Override
    public void signContract(String creditorNo) {
        try {
            ContractEntity contractEntity = contractMapper.selectByCreditorNo(creditorNo);
            if (null == contractEntity) {
                throw new Exception("合同盖章异常,合同信息不存在.");
            }

            GWStampContractReq request = new GWStampContractReq();
            //合同产品参数查询
            String extSerialNo = StringUtils.generateTimeLongByPrefix(GlobalVar.SYS_IDENTIFY_CODE);
            request.setExtSerialNo(extSerialNo);
            request.setBizType("P2P");
            request.setSaleChannel("TXS");//渠道
            request.setPersonName(contractEntity.getInvestMemberName());
            request.setPersonIdcard(contractEntity.getInvestIdentityCard());
            request.setPersonPaperType("IDCARD");
            request.setPersonMobile(contractEntity.getInvestTelNo());
            request.setCompanyName(contractEntity.getFinanceSubjectName());
            request.setCompanyRegisterNo(contractEntity.getCompanyCertificateNo());
            request.setCompanyAddress(contractEntity.getFinanceSubjectAddress());

            String systemConfigPath = System.getProperty("spring.config.location");
            //String contractTemplateUrl = this.getClass().getClassLoader().getResource("").getPath();
            log.info("signContract load template url  ....:"+systemConfigPath + GlobalVar.CONTRACT_TEMPLATE_FILE_PATH + File.separator + GlobalVar.LOAN_CONTRACT_FILE);
            String htmlStr = StringUtils.readResource(systemConfigPath + GlobalVar.CONTRACT_TEMPLATE_FILE_PATH + File.separator + GlobalVar.LOAN_CONTRACT_FILE);
            //log.info("signContract load template  ....:"+htmlStr.length());
            // 替换占位符
            request.setHtmlStr(dealContractAttribute(htmlStr, contractEntity));

            GWStampContractResponse stampResponse = signStamperClient.contractForStamp(request);
            if (null != stampResponse && ResultCodeEnum.SUCCESS.code().equals(stampResponse.getResultCode())) {
                //contractEntity.setContractNo(stampResponse.getData().getContractNo());
                contractEntity.setDocumentId(String.valueOf(stampResponse.getData().getDocumentId()));
                contractEntity.setStatus(ContractEnum.SIGN_SUCCESS.getCode());
                contractMapper.updateContractDocumentIdByContractNo(contractEntity);//更新契约锁唯一id
            } else {
                throw new Exception("债权编号[" + creditorNo + "]合同盖章异常,响应信息:" + stampResponse);
            }
        } catch (Exception e) {
            log.error("债权编号 : {}, ----合同签章接口异常 ----{}", creditorNo, e);
            //进入重试队列
            if (null == interfaceRetryService.selectByBusinessNoAndBizType(creditorNo, InterfaceRetryBusinessTypeEnum.SIGN_CONTRACT.getCode())) {
                InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
                interfaceRetryEntity.setBusinessNo(creditorNo);
                interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.SIGN_CONTRACT.getCode());
                interfaceRetryEntity.setRequestParam(JSON.toJSONString(creditorNo));
                interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
                interfaceRetryEntity.setMemo("合同签章");
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            }
        }
    }

    private String dealContractAttribute(String htmlStr, ContractEntity contractEntity) throws Exception {
        ContractTemplateAttribute attribute = new ContractTemplateAttribute();
        attribute.setAgreementNo(contractEntity.getContractNo());


        attribute.setInvestMemberName(SensitiveInfoUtils.chineseName(contractEntity.getInvestMemberName()));

        if (org.apache.commons.lang3.StringUtils.isBlank(contractEntity.getInvestIdentityCard())) {
            attribute.setInvestMemberIdCard("");
        } else {
            if (contractEntity.getInvestIdentityCard().length() >= 17) {
                attribute.setInvestMemberIdCard(SensitiveInfoUtils.getHiddenData(contractEntity.getInvestIdentityCard(), 6, contractEntity.getInvestIdentityCard().length() - 17));
            } else if (contractEntity.getInvestIdentityCard().length() > 6) {
                attribute.setInvestMemberIdCard(SensitiveInfoUtils.getHiddenData(contractEntity.getInvestIdentityCard(), 6, 0));
            } else {
                attribute.setInvestMemberIdCard(SensitiveInfoUtils.all(contractEntity.getInvestIdentityCard()));
            }
        }

        attribute.setInvestMemberTel(SensitiveInfoUtils.mobilePhone(contractEntity.getInvestTelNo()));

        if (org.apache.commons.lang3.StringUtils.isBlank(contractEntity.getFinanceSubjectName())) {
            attribute.setLoanMemberName("");
        } else {
            if (contractEntity.getFinanceSubjectName().length() > 6) {
                attribute.setLoanMemberName(SensitiveInfoUtils.hiddenLeftCharacters(contractEntity.getFinanceSubjectName(), 6));
            } else if (contractEntity.getFinanceSubjectName().length() > 3) {
                attribute.setLoanMemberName(SensitiveInfoUtils.hiddenLeftCharacters(contractEntity.getFinanceSubjectName(), 3));
            } else {
                attribute.setLoanMemberName(SensitiveInfoUtils.hiddenLeftCharacters(contractEntity.getFinanceSubjectName(), 1));
            }
        }

        attribute.setCompanyCertificateNo(SensitiveInfoUtils.hiddenRightCharacters(contractEntity.getCompanyCertificateNo()));

        /**
         *
         */
        if (org.apache.commons.lang3.StringUtils.isBlank(contractEntity.getFinanceSubjectAddress())) {
            attribute.setContactAddress("");
        } else {
            if (contractEntity.getFinanceSubjectAddress().length() > 6) {
                attribute.setContactAddress(SensitiveInfoUtils.hiddenRightCharacters(contractEntity.getFinanceSubjectAddress(), 6));
            } else if (contractEntity.getFinanceSubjectAddress().length() > 3) {
                attribute.setContactAddress(SensitiveInfoUtils.hiddenRightCharacters(contractEntity.getFinanceSubjectAddress(), 3));
            } else {
                attribute.setContactAddress(SensitiveInfoUtils.hiddenRightCharacters(contractEntity.getFinanceSubjectAddress(), 1));
            }
        }


        attribute.setLoanAmount(NumberUtils.roundN2(contractEntity.getLoanAmount()).toString());
        attribute.setLoanYearYield(NumberUtils.roundN2((contractEntity.getLoanYearYield().multiply(new BigDecimal(100)))) + "%");
        attribute.setLoanPurpose(contractEntity.getLoanPurpose());
        attribute.setLoanWithdrawTimeStr(DateUtils.format(contractEntity.getLoanWithdrawTime(), DateUtils.DEFAULT_DATA_FORMAT));
        attribute.setValueTimeStr(DateUtils.format(contractEntity.getValueTime(), DateUtils.DEFAULT_DATA_FORMAT));
        attribute.setExpireTimeStr(DateUtils.format(contractEntity.getExpireTime(), DateUtils.DEFAULT_DATA_FORMAT));
        attribute.setRepayTimeStr(DateUtils.format(contractEntity.getRepayTime(), DateUtils.DEFAULT_DATA_FORMAT));
        attribute.setNeedPayBackAmount(NumberUtils.roundN2(NumberUtils.sum(contractEntity.getLoanAmount(), contractEntity.getActualLoanInterests())).toString());
        attribute.setContractTimeStr(DateUtils.format(contractEntity.getCreateTime(), DateUtils.DEFAULT_DATA_FORMAT));

        StringTemplate st = new StringTemplate(htmlStr);
        st.setAttribute("contractTemplateAttribute", attribute);
        return st.toString();
    }

    @Override
    public List<ContractEntity> selectByStatus(String status) {
        return contractMapper.selectByStatus(status);
    }

}
