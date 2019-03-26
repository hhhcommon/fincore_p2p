package com.zb.p2p.trade.persistence.dao;

import com.zb.p2p.trade.persistence.entity.DailyIncomeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository("dailyIncomeMapper")
public interface DailyIncomeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DailyIncomeEntity record);

    DailyIncomeEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyIncomeEntity record);

    /**
     * 批量插入
     *
     * @param list
     */
    int insertList(List<DailyIncomeEntity> list);

    /**
     * 根据会员Id、产品及收益日单个查询
     * @param memberId
     * @param productCode
     * @param incomeDate
     * @return
     */
    BigDecimal queryTotalIncome(@Param("memberId") String memberId,
                                @Param("productCode") String productCode,
                                @Param("incomeDate") Date incomeDate);

    BigDecimal queryYesterdayIncome(@Param("memberId") String memberId,
                                    @Param("incomeDate") Date incomeDate);

    BigDecimal queryIncomeByOrderNo(@Param("orderNo") String orderNo);
    
}