package com.zb.p2p.service.order;

import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.dao.master.OrderRequestDAO;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.entity.OrderRequestEntity;
import com.zb.p2p.enums.OrderStatusEnum;
import com.zb.p2p.facade.api.req.OrderReq;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.facade.service.internal.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by limingxin on 2017/8/31.
 */
@Service
public class BasicDataService {
    @Autowired
    private LoanRequestDAO loanRequestDAO;
    @Autowired
    private OrderRequestDAO orderRequestDAO;


    public List<LoanRequestEntity> getWaitMatchLoanRequestList(Map<String, Object> params) {
        return loanRequestDAO.getWaitMatchLoanRequestList(params);
    }

    public List<OrderRequestEntity> queryOrderListByParams(Map<String, Object> params) {
        return orderRequestDAO.queryOrderListByParams(params);
    }

    public List<OrderRequestEntity> queryOrderListByProductCodeForMatch(OrderRequestEntity orderEntity) {
        return orderRequestDAO.queryOrderListByProductCodeForMatch(orderEntity);
    }

    public void updateOrderForBatchById(List<OrderDTO> batchOrders) throws Exception {
        orderRequestDAO.updateOrderForBatchById(batchOrders);
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void generatorOrder(OrderReq orderReq) throws Exception {
        OrderRequestEntity order = BeanUtils.copyAs(orderReq, OrderRequestEntity.class);
        order.setCreateTime(new Date());
        order.setStatus(OrderStatusEnum.WAIT_UNMATCHED.getCode());
        order.setOrderTime(DateUtils.parse(orderReq.getOrderTime(), DateUtils.DEFAULT_DATETIME_FORMAT));
        orderRequestDAO.insertSelective(order);
    }





    public void updateLoanRequest(LoanRequestDTO loanRequestResp) throws Exception {
        loanRequestDAO.updateByPrimaryKey(BeanUtils.copyAs(loanRequestResp, LoanRequestEntity.class));
    }

    public void updateLoanRequest(LoanRequestEntity loanRequest) {
        loanRequestDAO.updateByPrimaryKeySelective(loanRequest);
    }

    public void updateMatchStatusAndAmountById(LoanRequestDTO loanRequestResp) throws Exception {
        loanRequestDAO.updateMatchStatusAndAmountById(BeanUtils.copyAs(loanRequestResp, LoanRequestEntity.class));
    }

    public List<OrderRequestEntity> selectByAssetCodeForOrder(String assetCode) {
        return orderRequestDAO.selectByAssetCode(assetCode);
    }

    public LoanRequestEntity selectByAssetCodeForLoan(String assetCode) {
        return loanRequestDAO.selectByAssetCode(assetCode);
    }

    public LoanRequestEntity selectByAssetCodeForLoanByLoanNo(String loanNo) {
        return loanRequestDAO.selectByLoanNo(loanNo);
    }

    /**
     * （以assetCode的纬度去sum订单表投资金额是否等于借款金额）
     *
     * @param assetCode
     * @return
     */
    public boolean isLoaned(String assetCode) {
        return loanRequestDAO.loanStatus(assetCode) == 1;
    }

    /**
     * 以assetCode的纬度去sum订单表投资金额是否大于借款金额
     *
     * @param assetCode
     * @param matchAmount
     * @return
     */
    public boolean isOrdered(String assetCode, BigDecimal matchAmount) {
        /*List<OrderEntity> list = selectByAssetCodeForOrder(assetCode);
        if (list == null || list.size() == 0) {
            return true;
        }
        BigDecimal assetAmount = new BigDecimal(0);
        BigDecimal investAmount = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            OrderEntity orderEntity = list.get(i);
            investAmount = investAmount.add(orderEntity.getInvestAmount());
            assetAmount = orderEntity.getAssetAmount();
        }
        if (investAmount.add(matchAmount).compareTo(assetAmount) > 0) {
            return false;
        }
        return true;*/


        return true;
    }

    /*public int updateLoanRequestContractStatus(int id, String status, int version) {
        return loanRequestDAO.updateLoanContractStatus(id, status, version);

    }*/

    public List<LoanRequestEntity> queryLoanListByParams(Map<String, Object> params) {
        return loanRequestDAO.queryLoanListByParams(params);
    }

}
