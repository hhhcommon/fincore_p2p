package com.zb.p2p.facade.service.internal;

import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.facade.service.internal.dto.OrderDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by limingxin on 2017/9/6.
 */
public interface OrderInternalService {

    /***
     * 资产匹配时  根据产品编号查询投资单
     * @return
     */
    List<OrderDTO> queryOrderListByProductCodeForMatch(OrderDTO orderDTO);

    /***
     * 根据条件查询投资订单
     * @return
     */
    List<OrderDTO> queryOrderListByParams(Map<String, Object> params);

    /***
     * 查询借款单
     * 时间升序 未匹配
     * @return
     */
    List<LoanRequestDTO> getWaitMatchLoanRequestList(Map<String, Object> params);

    void updateOrderForBatchById(List<OrderDTO> batchOrders) throws Exception;


    /**
     * 更新借款单
     *
     * @param loanRequestDTO
     */
    void updateLoanRequest(LoanRequestDTO loanRequestDTO) throws Exception;

    void updateMatchStatusAndAmountById(LoanRequestDTO loanRequestResp) throws Exception;

    /**
     * 根据assetCode查询借款记录
     *
     * @param assetCode
     * @return
     */
    LoanRequestDTO selectByAssetCodeForLoan(String assetCode);

    /**
     * 根据loanNo查询借款记录
     *
     * @param loanNo
     * @return
     */
    LoanRequestDTO selectByAssetCodeForLoanByLoanNo(String loanNo);

    /**
     * 更新借款
     *
     * @param id
     * @param status
     * @param version
     * @return
     */
    /*int updateLoanRequestContractStatus(int id, String status, int version);*/

    /**
     * 根据条件查询借款单
     *
     * @param params
     * @return
     */
    List<LoanRequestDTO> queryLoanListByParams(Map<String, Object> params);

    List<OrderDTO> selectByAssetCodeForOrder(String assetCode);

}
