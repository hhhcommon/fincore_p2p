package com.zb.p2p.trade.persistence.dao;


import com.zb.p2p.trade.persistence.entity.OrderRequestEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("orderRequestMapper")
public interface OrderRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderRequestEntity record);

    int insertSelective(OrderRequestEntity record);

    OrderRequestEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderRequestEntity record);

    int updateByPrimaryKey(OrderRequestEntity record);

    List<OrderRequestEntity> queryOrderListByProductCodeForMatch(OrderRequestEntity orderEntity);

    List<OrderRequestEntity> queryOrderListByParams(Map<String, Object> params);

    List<OrderRequestEntity> selectListForIncomeCalc(@Param("productNo") String productNo);
    
    int updateIncome(@Param("totalIncome") BigDecimal totalIncome, @Param("orderNo") String orderNo);

    /**
     * 根据assetCode查询
     */
    OrderRequestEntity selectByOrderNo(@Param("orderNo") String orderNo);
    
}