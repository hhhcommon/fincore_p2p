package com.zb.p2p.service.incomecalc;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.exception.CommonException;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.GlobalVar;
import com.zb.p2p.dao.master.AccountDao;
import com.zb.p2p.dao.master.DailyIncomeDAO;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.dao.master.OrderRequestDAO;
import com.zb.p2p.entity.AccountEntity;
import com.zb.p2p.entity.DailyIncomeEntity;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.entity.OrderRequestEntity;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.BatchDailyIncomeReq;
import com.zb.p2p.facade.api.req.DailyIncomeReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.DailyIncomeDTO;
import com.zb.p2p.facade.api.resp.product.ProductDTO;
import com.zb.p2p.facade.service.internal.ProductInternalService;
import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.service.order.AccountService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwanbin on 2017/8/29.
 * 收益计算服务
 */
@Service
@Slf4j
public class IncomeCalcServiceImpl implements IncomeCalcService {

 
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductInternalService productInternalService;
    @Autowired
    private DailyIncomeDAO dailyIncomeDAO;
    
    @Autowired
    private MatchRecordDAO matchRecordDAO;
    
    @Autowired
    private OrderRequestDAO orderDAO;
    
    @Autowired
    private DistributedLockService distributedLockService;
    
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private LoanRequestDAO loanRequestDAO;
 
    
/*    public List<OrderRequestEntity> listTask(List<TaskItemDefine> list, int limit) {
        List<ProductDTO> productModelList = productInternalService.queryProductListByDate(null);
        if (productModelList != null && !productModelList.isEmpty()) {
            for (ProductDTO productDTO : productModelList) {
            	//查询未生成收益的
//            	List<MatchRecordEntity> matchRecordEntityList = matchRecordDAO.selectListByProductCode( productDTO.getProductCode(),"0");
            	
            	List<OrderRequestEntity> orderlist = orderDAO.selectList( productDTO.getProductCode());
                if (orderlist != null && !orderlist.isEmpty()) {
                    return orderlist;
                }
            }
        }
        return new ArrayList<>(0);
    }*/

    /**
     * 查找未计算收益的产品列表
     *
     * @param productCode
     * @param list
     * @param limit
     * @return
     */
    private List<AccountEntity> listAccountUnInterest(String productCode, List<TaskItemDefine> list, int limit) {
        return accountService.listAccountUnInterest(productCode, list, limit);
    }

    /**
     * 收益计算
     * 
     */ 
    @Transactional(rollbackFor = Exception.class)
    public void incomeCalc(ProductDTO productDTO) { 
    	String lockKey = GlobalVar.GLOBAL_INCOMECALC_LOCK_KEY  + "_"  +  productDTO.getProductCode() ;
    	try {
    		log.info( "计算收益 开始  productDTO.getProductCode()：" + productDTO.getProductCode());
    		
			distributedLockService.tryLock(lockKey);
			List<OrderRequestEntity> orderlist = orderDAO.selectListForIncomeCalc( productDTO.getProductCode()); //查询total_income <= 0的订单
	    	
	    	for (OrderRequestEntity orderEntity : orderlist) {
	    		try {
	    			incomeCalc( orderEntity, productDTO);
				}catch (CommonException e) {
					log.error(e.getMessage());
				} catch (Exception e) {
					 log.error("",e); 
				} 
			}
	    	
	    	log.info( "计算收益 结束  productDTO.getProductCode()：" + productDTO.getProductCode());
		} catch (Exception e) {
			 log.error("",e); 
		}finally {
			try {
				distributedLockService.unLock(lockKey);
			} catch (Exception e1) {
				 log.error("",e1);
			}
		}
    	 
    }
    
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void incomeCalc(OrderRequestEntity orderEntity,ProductDTO productDTO) {
    	
    	log.info("计算订单收益orderEntity.getOrderNo():" + orderEntity.getOrderNo());
    	
    	 String productCode = productDTO.getProductCode(); 
         Date startDate = productDTO.getProductPeriodDTO().getValueTime();
         BigDecimal yield = productDTO.getProductProfitDTO().getMinYieldRate();//利率
//         BigDecimal yield = new BigDecimal(0.1);
         int  productLimit = productDTO.getProductPeriodDTO().getInvestPeriod();
         
         
    	//订单维度查询所有未生成收益的匹配记录
    	List<MatchRecordEntity> matchRecordEntityList = matchRecordDAO.selectList(null, "0", orderEntity.getOrderNo());
    	
    	BigDecimal orderInterestedTotal = BigDecimal.ZERO; //订单总收益
    	
    	if(matchRecordEntityList != null && matchRecordEntityList.size() > 0){
    		for (MatchRecordEntity matchRecordEntity : matchRecordEntityList) {
        		List<DailyIncomeEntity> dailyIncomeEntityList = new ArrayList<>(productLimit);
                BigDecimal unit = matchRecordEntity.getMatchedAmount().multiply(yield.setScale(4, BigDecimal.ROUND_DOWN)).
                        divide(new BigDecimal(365), 8, BigDecimal.ROUND_DOWN);
                BigDecimal interestedTotal = BigDecimal.ZERO; //投资利率和借款利率相等
                for (int i = 0; i < productLimit; i++) {
                    DailyIncomeEntity dailyIncomeEntity = new DailyIncomeEntity();
                    dailyIncomeEntity.setMemberId(matchRecordEntity.getMemberId());
                    dailyIncomeEntity.setProductCode(productCode);
                    dailyIncomeEntity.setRefNo( matchRecordEntity.getCreditorNo());
                    dailyIncomeEntity.setInterestAmount(new BigDecimal(i + 1).multiply(unit).setScale(2, BigDecimal.ROUND_DOWN).subtract(interestedTotal));
                    dailyIncomeEntity.setInterestDate(DateUtils.addDay(startDate, i));
                    dailyIncomeEntityList.add(dailyIncomeEntity);
                    interestedTotal = interestedTotal.add(dailyIncomeEntity.getInterestAmount());
                }
                
                int dailyIncomeCount = 0;
                
                if (!dailyIncomeEntityList.isEmpty()) {
                	dailyIncomeCount = dailyIncomeDAO.insertList(dailyIncomeEntityList);
                }
                
                orderInterestedTotal = orderInterestedTotal.add(interestedTotal );
                  
               //借款手续费
               BigDecimal totalLoanCharge = new BigDecimal(0);
               LoanRequestEntity loanRequestEntity =  loanRequestDAO.selectByLoanNo(matchRecordEntity.getLoanNo() );
               totalLoanCharge = matchRecordEntity.getMatchedAmount().multiply( loanRequestEntity.getLoanFee())
               		.multiply(new BigDecimal( loanRequestEntity.getLockDate())).divide(new BigDecimal(365),2, BigDecimal.ROUND_CEILING);
               
               //记录总收益及收益状态
               int matchRecordCount = matchRecordDAO.updateIncome(matchRecordEntity.getId(),"1", interestedTotal,totalLoanCharge);
               
               //更新借款方的利息  和 手续费
               int loanRequestCount = loanRequestDAO.increRealLoanFee(interestedTotal, matchRecordEntity.getLoanNo(),totalLoanCharge);
               
               if(dailyIncomeCount <= 0 || matchRecordCount <= 0 || loanRequestCount <= 0){
            	   throw new CommonException("收益计算失败matchRecordEntity.getId()" + matchRecordEntity.getId());
               }
    		}
    		
    		//更新订单总收益
        	orderDAO.updateIncome(orderInterestedTotal, orderEntity.getId());
        	
        	//更新持仓收益
        	AccountEntity account = accountDao.selectByMemberIdAndProductCode(orderEntity.getMemberId(), orderEntity.getProductCode());
        	accountDao.increAccount(account.getId(), null, orderInterestedTotal);
    	} 
    }
    

    /**
     * 查询累计收益
     *
     * @param req
     * @return
     */
    @Override
    public CommonResp<DailyIncomeDTO> queryIncome(DailyIncomeReq req) {
        if (StringUtils.isBlank(req.getMemberId()) || StringUtils.isBlank(req.getProductCode())
                || StringUtils.isBlank(req.getIncomeDate())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), ResponseCodeEnum.RESPONSE_PARAM_FAIL.getDesc());
        }
        BigDecimal totalIncome = dailyIncomeDAO.queryTotalIncome(req.getMemberId(), req.getProductCode(), DateUtils.parse(req.getIncomeDate(), DateUtils.DATE_FORMAT_YYMMDD));

        DailyIncomeDTO respDTO = new DailyIncomeDTO();
        respDTO.setMemberId(req.getMemberId());
        respDTO.setProductCode(req.getProductCode());
        respDTO.setIncomeDate(req.getIncomeDate());
        respDTO.setIncomeAmt(totalIncome == null ? BigDecimal.ZERO : totalIncome);
        CommonResp resp = CommonResp.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
        resp.setData(respDTO);
        return resp;
    }

    @Override
    public CommonResp<List<DailyIncomeDTO>> queryBatchIncome(BatchDailyIncomeReq req) {
        if (StringUtils.isBlank(req.getMemberId()) || CollectionUtils.isNullOrEmpty(req.getProductCodes())
                || StringUtils.isBlank(req.getIncomeDate())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), ResponseCodeEnum.RESPONSE_PARAM_FAIL.getDesc());
        }
        List<DailyIncomeDTO> dailyIncomeDTOS = dailyIncomeDAO.queryTotalIncomeBatchByProductCode(req.getMemberId(), req.getProductCodes(), DateUtils.parse(req.getIncomeDate(), DateUtils.DATE_FORMAT_YYMMDD));
        CommonResp resp = CommonResp.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
        resp.setData(dailyIncomeDTOS);
        return resp;
    }
}
