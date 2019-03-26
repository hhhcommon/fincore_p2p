package com.zb.p2p.dao.master;

import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.facade.api.resp.order.OrderMatchInfoDTO;
import com.zb.p2p.facade.api.resp.order.OrderMatchRespDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangxin
 * @create 2017-08-31 11:00
 */
public interface MatchRecordDAO {

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

    /**
     * 根据资产编号查询预匹配记录
     * @param assetCode
     * @return
     */
    List<MatchRecordEntity> selectMatchRecordListByAssetCode(@Param("assetCode") String assetCode);

    List<MatchRecordEntity> selectListByLoanNo(@Param("loanNo") String loanNo);

    List<OrderMatchInfoDTO> queryOrderMatchInfoByExtOrderNo(@Param("extOrderNo") String extOrderNo);

    List<MatchRecordEntity> selectList(@Param("productCode") String productCode,@Param("interestFlag") String interestFlag,@Param("orderNo") String orderNo);

    int updateAccountStatus(@Param("accountStatus") String accountStatus,@Param("id") long id);

    List<MatchRecordEntity> listMatchRecords(@Param("list")List taskItemList, @Param("limit")int eachFetchDataNum);

    int updateIncome(@Param("id") long id,@Param("interestFlag") String interestFlag, @Param("totalIncome") BigDecimal totalIncome,
    		@Param("loanCharge") BigDecimal loanCharge             ) ;
    
    List<String> selectLoanNoListForJob();
     
}