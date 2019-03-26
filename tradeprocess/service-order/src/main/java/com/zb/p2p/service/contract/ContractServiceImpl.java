package com.zb.p2p.service.contract;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductPeriodModel;
import com.zb.fincore.pms.facade.product.model.ProductProfitModel;
import com.zb.p2p.dao.master.ContractDAO;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.dao.master.OrderRequestDAO;
import com.zb.p2p.entity.ContractEntity;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.entity.OrderRequestEntity;
import com.zb.p2p.enums.ContractEnum;
import com.zb.p2p.enums.SequenceEnum;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.service.common.DistributedSerialNoService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by mengkai on 2017/8/31.
 * 合同服务
 */
@Service
@Slf4j
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDAO contractDAO;
    
    @Autowired
    private LoanRequestDAO loanRequestDao;
    
    @Autowired
    private OrderRequestDAO orderRequestDAO;
    
    @Autowired
    private DistributedSerialNoService distributedSerialNoService ;
    
    @Autowired
    private ProductServiceForP2PFacade productServiceForP2PFacade;
    
    @Value("${repay.deadline}")
    private String repayDeadline; 
    
    @Value("${overdue.interest.rate}")
    private String overdueInterestRate; 
    
    @Value("${overdue.days.for.terminate.contract}")
    private String overdueDaysForTerminateContract; 
    
    /**
     * 生成合同
     *
     * @return
     */
    //@Transactional(rollbackFor = Exception.class)
    public CommonResp generateContract(LoanRequestEntity loanRequestEntity,List<MatchRecordEntity> matchRecordEntityList) throws Exception {
    	CommonResp response = new CommonResp();
    	  
    	QueryProductInfoRequest queryProductInfoRequest = new QueryProductInfoRequest();
    	queryProductInfoRequest.setProductCode(loanRequestEntity.getProductCode());
    	   
    	//查询产品信息
    	QueryProductInfoResponse queryProductInfoResponse = productServiceForP2PFacade.queryProductInfo(queryProductInfoRequest);
    	ProductModel productModel = queryProductInfoResponse.getProductModel();
    	ProductPeriodModel productPeriodModel = productModel.getProductPeriodModel();
    	ProductProfitModel productProfitModel = productModel.getProductProfitModel();
    	
    	int investPeriod = productPeriodModel.getInvestPeriod();
    	Date valueTime = productPeriodModel.getValueTime(); //起息日
    	Date expireTime = DateUtils.addDay(productPeriodModel.getValueTime(), investPeriod -1 ) ; //到期日
    	Date dateNow = new Date();
    	
    	for (MatchRecordEntity matchRecordEntity : matchRecordEntityList) {
    		
    		OrderRequestEntity orderRequestEntity = orderRequestDAO.selectByOrderNo(matchRecordEntity.getOrderNo());
    		String contractNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.CONTRACT);
    		
			ContractEntity contractEntity = new ContractEntity();
			contractEntity.setCompanyCertificateNo(loanRequestEntity.getCompanyCertificateNo() );
			contractEntity.setContractNo(contractNo);
//			contractEntity.setCreateBy(createBy);
			contractEntity.setCreateTime( dateNow);
			contractEntity.setCreditorNo(matchRecordEntity.getCreditorNo());
			contractEntity.setExpireTime(expireTime);
			contractEntity.setExtInvestOrderNo(matchRecordEntity.getExtOrderNo() );
			contractEntity.setFinanceSubjectAddress(loanRequestEntity.getFinanceSubjectAddress());
			contractEntity.setFinanceSubjectName(loanRequestEntity.getFinanceSubjectName() );
			contractEntity.setInvestIdentityCard(orderRequestEntity.getCertNo()  );
			contractEntity.setInvestMemberId( matchRecordEntity.getMemberId());
			contractEntity.setInvestMemberName(orderRequestEntity.getName());
			contractEntity.setInvestOrderNo(matchRecordEntity.getOrderNo() );
			contractEntity.setInvestTelNo( orderRequestEntity.getTelNo());
			contractEntity.setInvestYearYield(productProfitModel.getMinYieldRate() );
			contractEntity.setLoanAmount(matchRecordEntity.getMatchedAmount() );
			contractEntity.setLoanFee(loanRequestEntity.getLoanFee() );
			contractEntity.setLoanMemberId(loanRequestEntity.getMemberId() );
			contractEntity.setLoanOrderNo(loanRequestEntity.getLoanNo() );
			contractEntity.setLoanPurpose(loanRequestEntity.getLoanPurpose() );
			contractEntity.setLoanTelNo(loanRequestEntity.getFinanceSubjectTel() );
			contractEntity.setLoanWithdrawTime(valueTime );
			contractEntity.setLoanYearYield(loanRequestEntity.getLoanRate() );
			contractEntity.setLockDate(investPeriod);
//			contractEntity.setMemo(memo);
//			contractEntity.setModifyBy(modifyBy);
			contractEntity.setModifyTime(dateNow);
			contractEntity.setProductCode( matchRecordEntity.getProductCode());
			contractEntity.setProductName(loanRequestEntity.getProductName()  );
			contractEntity.setRepaymentType(loanRequestEntity.getRepaymentType() );
			contractEntity.setRepayTime(DateUtils.addDay(expireTime, 1)  );
			contractEntity.setStatus(ContractEnum.INIT.name());
			contractEntity.setValueTime(valueTime );
			contractEntity.setRepayDeadline(repayDeadline);
			contractEntity.setOverdueInterestRate(overdueInterestRate);
			contractEntity.setOverdueDaysForTerminateContract(overdueDaysForTerminateContract);
			
			//有唯一索引去重
			contractDAO.insertSelective( contractEntity);
		}
    	
    	loanRequestDao.updateLoanContractStatus(loanRequestEntity.getId(), "TRUE"); 
    	return response;
         
    }
    
    /**
     * 查询合同
     * @param creditorNo
     * @return
     */
   public ContractEntity queryContract(String creditorNo){
	   return contractDAO.selectByCreditorNo(creditorNo);
   }
    

    /**
     * 查询投资人合同
     *
     * @param investContractReq
     * @return
     */
   /* public CommonResp<InvestContractDTO> queryInvestContract(InvestContractReq investContractReq) throws Exception {
        try {
            CommonResp<InvestContractDTO> response = new CommonResp<>();
            //参数校验
            CommonResp commonResp = investContractChecker.checkQueryInvestContractReq(investContractReq);
            if (commonResp != null) {
                return commonResp;
            }
            //查询合同信息
            InvestContractPositionBO investContractPositionBO = contractDAO.getInvestContractPosition(investContractReq.getMemberId(), investContractReq.getProductCode());
            if (investContractPositionBO == null) {
                response.setCode(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode());
                response.setMessage(ResponseCodeEnum.RESPONSE_NOT_FUND.getDesc());
                return response;
            }
            List<InvestMathAssetInfoBO> investMathAssetInfoList = contractDAO.ListInvestMathAssetInfo(investContractReq.getMemberId(), investContractReq.getProductCode());
            List<AssetInfoDTO> assetList = new ArrayList<>();
            if (investMathAssetInfoList != null && investMathAssetInfoList.size() > 0) {
                for (InvestMathAssetInfoBO investMathAssetInfoBO : investMathAssetInfoList) {
                    AssetInfoDTO assetInfoDTO = new AssetInfoDTO();
                    assetInfoDTO.setLoanOrderNo(investMathAssetInfoBO.getLoanOrderNo());
                    assetInfoDTO.setLoanMemberId(investMathAssetInfoBO.getLoanMemberId());
                    assetInfoDTO.setLoanMemberName(investMathAssetInfoBO.getLoanMemberName());
                    assetInfoDTO.setLoanAmount(investMathAssetInfoBO.getLoanTotalAmount());
                    assetInfoDTO.setLoanPurpose(investMathAssetInfoBO.getLoanPurpose());
                    assetInfoDTO.setLoanTelNo(investMathAssetInfoBO.getLoanTelNo());
                    assetInfoDTO.setLoanIdentityCard(investMathAssetInfoBO.getLoanIdentityCard());
                    assetList.add(assetInfoDTO);
                }
            }
            //组装显示结果信息
            InvestContractDTO InvestContractDTO = new InvestContractDTO();
            InvestContractDTO.setInvestContractNo(investContractPositionBO.getInvestContractNo());
            InvestContractDTO.setSaleChannel(investContractPositionBO.getSaleChannel());
            InvestContractDTO.setProductCode(investContractPositionBO.getProductCode());
            InvestContractDTO.setProductName(investContractPositionBO.getProductName());
            InvestContractDTO.setInvestMemberId(investContractPositionBO.getInvestMemberId());
            InvestContractDTO.setInvestMemberName(investContractPositionBO.getInvestMemberName());
            InvestContractDTO.setInvestIdentityCard(investContractPositionBO.getInvestIdentityCard());
            InvestContractDTO.setInvestAmount(investContractPositionBO.getInvestTotalAmount());
            InvestContractDTO.setInvestYearYield(investContractPositionBO.getInvestYearYield());
            InvestContractDTO.setInvestStartDate(investContractPositionBO.getInvestStartDate());
            InvestContractDTO.setInvestEndDate(investContractPositionBO.getInvestEndDate());
            InvestContractDTO.setInvestLockData(investContractPositionBO.getInvestLockDate());
            InvestContractDTO.setReservationNo(investContractPositionBO.getReservationNo());
            InvestContractDTO.setInvestTelNo(investContractPositionBO.getInvestTelNo());
            InvestContractDTO.setAssetList(assetList);
            //返回结果
            response.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
            response.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
            response.setData(InvestContractDTO);
            return response;
        } catch (Exception e) {
            log.error("查询投资人合同异常", e);
            throw e;
        }
    }*/

    /**
     * 查询借款人合同
     *
     * @param loanContractReq
     * @return
     */
    /*public CommonResp<LoanContractDTO> queryLoanContract(LoanContractReq loanContractReq) throws Exception {
        try {
            CommonResp<LoanContractDTO> response = new CommonResp<>();
            //参数校验
            CommonResp commonResp = loanContractChecker.checkQueryLoanContractReq(loanContractReq);
            if (commonResp != null) {
                return commonResp;
            }
            //通过外部资产编码查询资产编码
            //查询资产信息
            LoanRequestDTO loanRequestDTO = orderInternalService.selectByAssetCodeForLoanByLoanNo(loanContractReq.getLoanNo());
            if (loanRequestDTO == null) {
                response.setCode(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode());
                response.setMessage(ResponseCodeEnum.RESPONSE_NOT_FUND.getDesc());
                return response;
            }
            if (StringUtils.isBlank(loanRequestDTO.getAssetCode())) {
                response.setCode(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode());
                response.setMessage("获取资产编码为空");
                return response;
            }
            //查询合同信息
            LoanContractBO loanContractBO = contractDAO.getLoanContract(loanContractReq.getMemberId(), loanRequestDTO.getAssetCode());
            if (loanContractBO == null) {
                response.setCode(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode());
                response.setMessage(ResponseCodeEnum.RESPONSE_NOT_FUND.getDesc());
                return response;
            }
            List<LoanMathInvestInfoBO> LoanMathInvestInfList = contractDAO.ListLoanMathInvestInfo(loanContractReq.getMemberId(), loanRequestDTO.getAssetCode());
            List<InvestInfoDTO> investList = new ArrayList<>();
            if (LoanMathInvestInfList != null && LoanMathInvestInfList.size() > 0) {
                for (LoanMathInvestInfoBO loanMathInvestInfoBO : LoanMathInvestInfList) {
                    InvestInfoDTO investInfoDTO = new InvestInfoDTO();
                    investInfoDTO.setInvestMemberId(loanMathInvestInfoBO.getInvestMemberId());
                    investInfoDTO.setInvestMemberName(loanMathInvestInfoBO.getInvestMemberName());
                    investInfoDTO.setInvestAmount(loanMathInvestInfoBO.getInvestTotalAmount());
                    investInfoDTO.setReservationNo(loanMathInvestInfoBO.getReservationNo());
                    investInfoDTO.setInvestTelNo(loanMathInvestInfoBO.getInvestTelNo());
                    investInfoDTO.setInvestIdentityCard(loanMathInvestInfoBO.getInvestIdentityCard());
                    investList.add(investInfoDTO);
                }
            }
            //组装显示结果信息
            LoanContractDTO loanContractDTO = new LoanContractDTO();
            loanContractDTO.setLoanOrderNo(loanContractBO.getLoanOrderNo());
            loanContractDTO.setLoanContractNo(loanContractBO.getLoanContractNo());
            loanContractDTO.setSaleChannel(loanContractBO.getSaleChannel());
            loanContractDTO.setAssetCode(loanContractBO.getAssetCode());
            loanContractDTO.setAssetName(loanContractBO.getAssetName());
            loanContractDTO.setLoanMemberId(loanContractBO.getLoanMemberId());
            loanContractDTO.setLoanMemberName(loanContractBO.getLoanMemberName());
            loanContractDTO.setLoanIdentityCard(loanContractBO.getLoanIdentityCard());
            loanContractDTO.setLoanAmount(loanContractBO.getLoanAmount());
            loanContractDTO.setLoanYearYield(loanContractBO.getLoanYearYield());
            loanContractDTO.setLoanStartDate(loanContractBO.getLoanStartDate());
            loanContractDTO.setLoanEndDate(loanContractBO.getLoanEndDate());
            loanContractDTO.setLoanLockData(loanContractBO.getLoanLockData());
            loanContractDTO.setLoanFee(loanContractBO.getLoanFee());
            loanContractDTO.setLoanTelNo(loanContractBO.getLoanTelNo());
            loanContractDTO.setLoanPurpose(loanContractBO.getLoanPurpose());
            loanContractDTO.setInvestList(investList);
            //返回结果
            response.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
            response.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
            response.setData(loanContractDTO);
            return response;
        } catch (Exception e) {
            log.error("查询接口合同异常", e);
            throw e;
        }
    }*/

    /**
     * 生成合同记录
     *
     * @return
     */
    /* @Transactional
    public CommonResp generateContract(String assetCode, LoanRequestDTO loanRq) throws Exception {
        CommonResp response = new CommonResp();
        //校验参数
        if (StringUtils.isBlank(assetCode)) {
            String paramError = "生成合同参数异常:资产编码不能为空";
            log.error(paramError);
            throw new CommonException(paramError);
        }
        List<ContractEntity> contractEntities = Lists.newArrayList();
        //查询资产匹配p2p_order表,通过assetCode
        List<OrderDTO> orderList = orderInternalService.selectByAssetCodeForOrder(assetCode);
        if (orderList != null && orderList.size() > 0) {
            for (OrderDTO orderDTO : orderList) {
                //查询资产信息
                LoanRequestDTO loanRequestDTO = orderInternalService.selectByAssetCodeForLoan(assetCode);
                if (loanRequestDTO == null) {
                    String loanRequestDTOErrorMsg = "生成合同异常:根据资产编码" + assetCode + "查询p2p_loan_request结果为空";
                    log.error(loanRequestDTOErrorMsg);
                    throw new CommonException(loanRequestDTOErrorMsg);
                }
                ContractEntity contractEntity = new ContractEntity();
                contractEntity.setLoanContractNo(contractNoGenerateServiceImpl.generateContractNo(ContractNoEnum.LCONTRACT.getCode(), loanRequestDTO.getSaleChannel(), loanRequestDTO.getId().toString()));
                contractEntity.setLoanOrderNo(loanRequestDTO.getLoanNo());
                contractEntity.setLoanSaleChannel(loanRequestDTO.getSaleChannel());
                contractEntity.setAssetCode(loanRequestDTO.getAssetCode());
                contractEntity.setAssetName(loanRequestDTO.getAssetName());
                contractEntity.setLoanAmount(loanRequestDTO.getAssetAmount());
                contractEntity.setLoanYearYield(loanRequestDTO.getAssetYearYield());
                contractEntity.setLoanFee(loanRequestDTO.getLoanFee());
                contractEntity.setLoanLockDate(loanRequestDTO.getLockDate());
                contractEntity.setLoanStartDate(loanRequestDTO.getValueStartTime());
                contractEntity.setLoanEndDate(loanRequestDTO.getValueEndTime());
                contractEntity.setLoanNo(loanRequestDTO.getLoanNo());
                //调用资产系统查询借款人个人信息
                BorrowerInfoRequest borrowerInfoRequest = new BorrowerInfoRequest();
                borrowerInfoRequest.setAssetCode(assetCode);
                QueryResponse<AssetRepayPlanModel> assetInfoResponse = borrowerInfoFacade.identityInfoQuery(borrowerInfoRequest);
                if (!assetInfoResponse.isSuccess()) {
                    String assetInfoResponseErrorMsg = "生成合同异常:根据资产编码" + assetCode + "调用ams-BorrowerInfoQuery方法失败-失败编码" + assetInfoResponse.getRespCode() + ",失败信息" + assetInfoResponse.getRespMsg();
                    log.error(assetInfoResponseErrorMsg);
                    throw new CommonException(assetInfoResponseErrorMsg);
                } else {
                    if (assetInfoResponse.getData() == null) {
                        String assetInfoResErrorMsg = "生成合同异常:根据资产编码" + assetCode + "调用ams-BorrowerInfoQuery方法成功但查询结果为空";
                        log.error(assetInfoResErrorMsg);
                        throw new CommonException(assetInfoResErrorMsg);
                    }
                    contractEntity.setLoanMemberId(assetInfoResponse.getData().getMemberId());
                    contractEntity.setLoanMemberName(assetInfoResponse.getData().getLoanName());
                    contractEntity.setLoanIdentityCard(assetInfoResponse.getData().getLoanCertNo());
                    contractEntity.setLoanPurpose(assetInfoResponse.getData().getLoanPurpose());
                    contractEntity.setLoanTelNo(assetInfoResponse.getData().getTel());
                }
                //查询投资相关信息
                //ReservationOrderDTO reservationOrderDTO = orderInternalService.selectByExtReservationNo(orderDTO.getReservationNo());
//                if (reservationOrderDTO == null) {
//                    String reservationOrderError = "生成合同异常:预约单编号" + orderDTO.getReservationNo() + "查询p2p_reservation_order查询结果为空";
//                    log.error(reservationOrderError);
//                    throw new CommonException(reservationOrderError);
//                }
                
                contractEntity.setInvestContractNo(contractNoGenerateServiceImpl.generateContractNo(ContractNoEnum.ICONTRACT.getCode(), orderDTO.getSaleChannel(), orderDTO.getProductCode() + orderDTO.getMemberId()));
//                contractEntity.setReservationNo(orderDTO.getReservationNo());
                contractEntity.setInvestOrderNo(orderDTO.getOrderNo());
                contractEntity.setInvestMemberId(orderDTO.getMemberId());
                contractEntity.setInvestMemberName(orderDTO.getName());
                contractEntity.setInvestIdentityCard(orderDTO.getCertNo());
                contractEntity.setInvestSaleChannel(orderDTO.getSaleChannel());
                contractEntity.setInvestAmount(orderDTO.getInvestAmount());
                contractEntity.setProductCode(orderDTO.getProductCode());
                contractEntity.setExtInvestOrderNo(orderDTO.getExtOrderNo());
                contractEntity.setCreateBy(CommonConstants.TRADE_OPERATE_SYSTEM);
                contractEntity.setInvestTelNo(orderDTO.getTelNo());
                //查询产品详情
                CommonResp<ProductDTO> productDTO = productInternalService.queryProductInfoByProductCode(orderDTO.getProductCode());
                if (productDTO == null) {
                    String productInfoError = "生成合同异常:根据产品编码" + orderDTO.getProductCode() + "查询queryProductInfoByProductCode方法结果为空";
                    log.error(productInfoError);
                    throw new CommonException(productInfoError);
                } else {
                    contractEntity.setProductName(productDTO.getData().getProductName());
                    contractEntity.setProductDisplayName(productDTO.getData().getProductDisplayName());
                    contractEntity.setInvestYearYield(productDTO.getData().getProductProfitDTO().getMinYieldRate());
                    contractEntity.setInvestLockDate(productDTO.getData().getProductPeriodDTO().getInvestPeriod());
                    contractEntity.setInvestStartDate(productDTO.getData().getProductPeriodDTO().getValueTime());
                    contractEntity.setInvestEndDate(DateUtils.addDay(productDTO.getData().getProductPeriodDTO().getExpectExpireTime(), -1));
                }
                contractEntities.add(contractEntity);
            }
            //每20条切分一个，分批插入
            List<List<ContractEntity>> batchList = Lists.partition(contractEntities, 20);
            for (List<ContractEntity> contractEntityList : batchList) {
                contractDAO.insertBatch(contractEntityList);
            }
            response.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
            response.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
        } else {
            String findError = "生成合同异常:通过资产编码" + assetCode + "查询p2p_order表结果为空";
            log.error(findError);
            throw new CommonException(findError);
        }
        return response;
    }*/
    
    public static void main(String[] args){
//    	int investPeriod = 1;
//    	Date valueTime = new Date(); //起息日
//    	Date expireTime = DateUtils.addDay(valueTime, investPeriod -1 ) ; 
//    	
//    	System.out.println(DateUtils.formatDateNotNull(expireTime, DateUtils.DEFAULT_DATA_FORMAT));
//    	
    	OrderRequestEntity orderRequestEntity = new OrderRequestEntity();
    	
    	orderRequestEntity.setCertNo("aaaa");
    	
    	System.out.println(JSON.toJSONString(orderRequestEntity));
    	
    }
   
    
}
