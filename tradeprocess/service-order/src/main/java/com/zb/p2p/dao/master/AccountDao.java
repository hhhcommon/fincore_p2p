package com.zb.p2p.dao.master;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.entity.AccountEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountDao {
    int deleteByPrimaryKey(Long id);

    int insert(AccountEntity accountEntity);

    /**
     * 新增记录
     *
     * @param accountEntity
     * @return
     */
    int insertSelective(AccountEntity accountEntity);

    AccountEntity selectByPrimaryKey(Long id);

    AccountEntity selectByMemberIdAndProductCode(@Param("memberId") String memberId, @Param("productCode") String productCode);

    AccountEntity selectByMemberId(@Param("memberId") String memberId);

    int updateByPrimaryKeySelective(AccountEntity accountEntity);

    /**
     * //根据主键更新
     *
     * @param accountEntity
     * @return
     */
    int updateByPrimaryKey(AccountEntity accountEntity);

    /**
     * 根据id更新兑付状态
     *
     * @param accountEntity
     * @return
     */
    int updateCashFlagById(AccountEntity accountEntity);

    int updateAccountAmount(AccountEntity accountEntity);

    List<AccountEntity> listCashAccount(@Param("productCode") String productCode,
                                        @Param("list") List<TaskItemDefine> list,
                                        @Param("limit") int limit);

    /**
     * 根据产品统计
     *
     * @param productCode
     * @return
     */
    int countByProduct(@Param("productCode") String productCode);

    /**
     * 根据产品统计兑付
     *
     * @param productCode
     * @return
     */
    int countCashAccount(@Param("productCode") String productCode);

    List<AccountEntity> listAccountUnInterest(@Param("productCode") String productCode,
                                              @Param("list") List<TaskItemDefine> list,
                                              @Param("limit") int limit);

    /**
     * 修改收益计算flag
     *
     * @param id
     * @param interestCalcFlag
     * @return
     */
    int updateInterestCalcStatus(@Param("id") long id, @Param("interestFlag") String interestCalcFlag);

    //增加持仓
    int increAccount(@Param("id") Long id, @Param("addAmount") BigDecimal addAmount, @Param("addInterest") BigDecimal addInterest);

    BigDecimal queryAmountByMemberId(Map<String, Object> params);


    AccountEntity selectByWithdrawNo(@Param("withdrawNo") String withdrawNo);
}