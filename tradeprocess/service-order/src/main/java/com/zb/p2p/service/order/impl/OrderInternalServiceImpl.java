package com.zb.p2p.service.order.impl;

import com.zb.fincore.common.utils.BeanUtils;
import com.zb.p2p.entity.OrderRequestEntity;
import com.zb.p2p.facade.service.internal.OrderInternalService;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.facade.service.internal.dto.OrderDTO;
import com.zb.p2p.service.order.BasicDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by limingxin on 2017/9/6.
 */
@Service
@Slf4j
public class OrderInternalServiceImpl implements OrderInternalService {

    @Autowired
    private BasicDataService basicDataService;

    @Override
    public List<OrderDTO> queryOrderListByProductCodeForMatch(OrderDTO orderDTO) {
        try {
            return BeanUtils.copyAs(basicDataService.queryOrderListByProductCodeForMatch(BeanUtils.copyAs(orderDTO, OrderRequestEntity.class)), OrderDTO.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public List<OrderDTO> queryOrderListByParams(Map<String, Object> params) {
        try {
            return BeanUtils.copyAs(basicDataService.queryOrderListByParams(params), OrderDTO.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public List<LoanRequestDTO> getWaitMatchLoanRequestList(Map<String, Object> params) {
        try {
            return BeanUtils.copyAs(basicDataService.getWaitMatchLoanRequestList(params),
                    LoanRequestDTO.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public void updateOrderForBatchById(List<OrderDTO> batchOrders) throws Exception{
        basicDataService.updateOrderForBatchById(batchOrders);
    }
    
    @Override
    public List<OrderDTO> selectByAssetCodeForOrder(String assetCode) {
        List<OrderDTO> list = null;
        try {
            list = BeanUtils.copyAs(basicDataService.selectByAssetCodeForOrder(assetCode), OrderDTO.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return list;
    }






    @Override
    public void updateLoanRequest(LoanRequestDTO loanRequestResp) throws Exception {
        basicDataService.updateLoanRequest(loanRequestResp);
    }

    @Override
    public void updateMatchStatusAndAmountById(LoanRequestDTO loanRequestResp) throws Exception {
        basicDataService.updateMatchStatusAndAmountById(loanRequestResp);
    }

    @Override
    public LoanRequestDTO selectByAssetCodeForLoan(String assetCode) {
        try {
            LoanRequestDTO loanRequestDTO = BeanUtils.copyAs(basicDataService.selectByAssetCodeForLoan(assetCode), LoanRequestDTO.class);
            return loanRequestDTO;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public LoanRequestDTO selectByAssetCodeForLoanByLoanNo(String loanNo) {
        try {
            LoanRequestDTO loanRequestDTO = BeanUtils.copyAs(basicDataService.selectByAssetCodeForLoanByLoanNo(loanNo), LoanRequestDTO.class);
            return loanRequestDTO;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /*@Override
   public int updateLoanRequestContractStatus(int id, String status, int version) {
        return basicDataService.updateLoanRequestContractStatus(id, status, version);
    }*/

    @Override
    public List<LoanRequestDTO> queryLoanListByParams(Map<String, Object> params) {
        try {
            return BeanUtils.copyAs(basicDataService.queryLoanListByParams(params), LoanRequestDTO.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

}
