package com.zb.p2p.dao.master;

import com.zb.p2p.entity.OrderRequestEntity;
import com.zb.p2p.facade.service.internal.dto.OrderDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderRequestDAO {
    int deleteByPrimaryKey(Long id);

    int insert(OrderRequestEntity record);

    int insertSelective(OrderRequestEntity record);

    OrderRequestEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderRequestEntity record);

    int updateByPrimaryKey(OrderRequestEntity record);

    /**
     * 根据assetCode查询
     */
    List<OrderRequestEntity> selectByAssetCode(@Param("assetCode") String assetCode);

    /**
     * 根据productCode查询
     */
    BigDecimal selectInvestedAmountByProductCode(@Param("productCode") String productCode);


    List<OrderRequestEntity> queryOrderListByProductCodeForMatch(OrderRequestEntity orderEntity);

    List<OrderRequestEntity> queryOrderListByParams(Map<String, Object> params);

    void updateOrderForBatchById(List<OrderDTO> batchOrders);
    
    List<OrderRequestEntity> selectListForIncomeCalc(@Param("productCode") String productCode);
    
    int updateIncome(@Param("totalIncome") BigDecimal totalIncome,@Param("id") long id);
    
    OrderRequestEntity selectByOrderNo(@Param("orderNo") String orderNo);
    
    
    
    
}