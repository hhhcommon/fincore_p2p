package com.zb.p2p.trade.persistence.dao;


import com.zb.p2p.trade.persistence.entity.MatchRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangxin
 * @create 2017-08-31 11:00
 */

@Repository("matchRecordMapper")
public interface MatchRecordMapper {

    /**
     * 根据ID删除预匹配记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入预匹配记录
     * @param record
     * @return
     */
    int insert(MatchRecordEntity record);

    /**
     * 有字段选择性的插入预匹配记录
     * @param record
     * @return
     */
    int insertSelective(MatchRecordEntity record);

    /**
     * 批量插入匹配记录
     * @param matchRecordEntities
     * @return
     */
    int batchInsert(List<MatchRecordEntity> matchRecordEntities);

    /**
     * 根据主键查询预匹配记录
     * @param id
     * @return
     */
    MatchRecordEntity selectByPrimaryKey(Long id);

    /**
     * 有字段选择性的更新预匹配记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MatchRecordEntity record);

    /**
     * 更新预匹配记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(MatchRecordEntity record);

    List<MatchRecordEntity> selectListByLoanNo(@Param("loanNo") String loanNo);

    /**
     * 根据资产编号查询预匹配记录
     * @param assetCode
     * @return
     */
    List<MatchRecordEntity> selectListByTransferCode(@Param("assetCode") String assetCode);

    List<MatchRecordEntity> selectList(@Param("assetCode") String assetCode,
                                       @Param("interestFlag") int interestFlag,
                                       @Param("orderNo") String orderNo);

    int updateAccountStatus(@Param("accountStatus") String accountStatus, @Param("id") long id);

    List<MatchRecordEntity> listMatchRecords(@Param("list") List taskItemList, @Param("limit") int eachFetchDataNum);

    int updateIncome(@Param("id") long id,
                     @Param("interestFlag") int interestFlag,
                     @Param("totalIncome") BigDecimal totalIncome);
    
    List<String> selectLoanNoListForJob();
     
}