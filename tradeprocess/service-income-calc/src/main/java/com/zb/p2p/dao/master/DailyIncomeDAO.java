package com.zb.p2p.dao.master;

import com.zb.p2p.entity.DailyIncomeEntity;
import com.zb.p2p.facade.api.resp.DailyIncomeDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DailyIncomeDAO {
    int deleteByPrimaryKey(Long id);

    int insert(DailyIncomeEntity record);

    int insertSelective(DailyIncomeEntity record);

    DailyIncomeEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyIncomeEntity record);

    int updateByPrimaryKey(DailyIncomeEntity record);

    /**
     * 批量插入
     *
     * @param list
     */
    int insertList(List<DailyIncomeEntity> list);

    DailyIncomeEntity queryDailyIncome(@Param("memberId") String memberId, @Param("productCode") String productCode,
                                       @Param("incomeDate") Date incomeDate);

    BigDecimal queryTotalIncome(@Param("memberId") String memberId, @Param("productCode") String productCode,
                                @Param("incomeDate") Date incomeDate);


    List<DailyIncomeDTO> queryTotalIncomeBatchByProductCode(@Param("memberId") String memberId, @Param("productCodes") List<String> productCodes,
                                                            @Param("incomeDate") Date incomeDate);
  
    BigDecimal queryYesterdayIncome(@Param("memberId") String memberId,  @Param("incomeDate") String incomeDate);
    
    BigDecimal queryIncomeByOrderNo(  @Param("orderNo") String orderNo);
    
     BigDecimal queryCashedIncome(Map<String, Object> params);
     
    
    
}