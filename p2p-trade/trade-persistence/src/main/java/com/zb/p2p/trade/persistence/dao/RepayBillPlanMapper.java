package com.zb.p2p.trade.persistence.dao;


import com.zb.p2p.trade.persistence.dto.RepayAmountDTO;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repayBillPlanMapper")
public interface RepayBillPlanMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RepayBillPlanEntity record);

    int batchInsert(@Param("billList") List<RepayBillPlanEntity> billList);

    int updateByPrimaryKeySelective(RepayBillPlanEntity record);

    RepayBillPlanEntity selectByPrimaryKey(Long id);

    /**
     * 根据资产编号和期号查询
     * @param assetCode
     * @param stageSeq
     * @return
     */
    RepayBillPlanEntity selectByAssetNoStage(@Param("assetCode") String assetCode,
                                             @Param("stageSeq") int stageSeq);

    /**
     * 根据借款单号及状态查询
     * @param loanNo
     * @return
     */
    List<RepayBillPlanEntity> selectByLoanNoStatus(@Param("loanNo") String loanNo,
                                                   @Param("statusList") List<String> statusList);


    /**
     * 根据资产编号查询(2.0为借款单号)
     * @param assetNoList
     * @return
     */
    List<RepayAmountDTO> selectRepayAmountList(@Param("assetNoList") List<String> assetNoList);
}