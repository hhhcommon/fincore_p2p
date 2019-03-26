package com.zb.p2p.dao.master;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.facade.api.resp.RepayAmountDTO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LoanRequestDAO {

    /**
     * 根据支付状态查询
     *
     * @param loanPaymentStatus
     * @return
     */
    List<LoanRequestEntity> selectByPaymentStatus(@Param("loanPaymentStatus") String loanPaymentStatus, @Param("list") List<TaskItemDefine> list, @Param("limit") int limit);

    int deleteByPrimaryKey(Integer id);

    int insert(LoanRequestEntity record);

    int insertSelective(LoanRequestEntity record);

    LoanRequestEntity selectByPrimaryKey(Integer id);

    List<LoanRequestEntity> getWaitMatchLoanRequestList(Map<String, Object> params);

    int updateByPrimaryKeySelective(LoanRequestEntity record);

    int updateByPrimaryKey(LoanRequestEntity record);

    int updateMatchStatusAndAmountById(LoanRequestEntity record);

    /**
     * 查询总额
     *
     * @param productCode
     */
    LoanRequestEntity getSumLoanAmount(@Param("productCode") String productCode);

    /**
     * 根据assetCode查询
     */
    LoanRequestEntity selectByAssetCode(@Param("assetCode") String assetCode);

    /**
     * 根据loanNo查询
     */
    LoanRequestEntity selectByLoanNo(@Param("loanNo") String loanNo);


    @Select("select if(sum(invest_amount)=asset_amount,1,0) from p2p_order where asset_code=#{assetCode}")
    int loanStatus(@Param("assetCode") String assetCode);


    int updateLoanContractStatus(@Param("id") int id, @Param("status") String status );

    List<LoanRequestEntity> queryLoanListByParams(Map<String, Object> params);
    
    int updateLoanPaymentStatus(@Param("loanPaymentStatus") String loanPaymentStatus,@Param("id") long id, @Param("loanPaymentTime") Date loanPaymentTime);
    
    int increRealLoanFee(@Param("realLoanFee") BigDecimal realLoanFee,@Param("loanNo") String loanNo,@Param("totalLoanCharge") BigDecimal totalLoanCharge);
    
    List<RepayAmountDTO> selectRepayAmountList(@Param("list") List<String> list);
    
}