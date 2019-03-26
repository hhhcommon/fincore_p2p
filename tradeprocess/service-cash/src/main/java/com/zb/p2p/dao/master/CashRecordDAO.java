package com.zb.p2p.dao.master;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.CashNotify;
import com.zb.p2p.entity.CashRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CashRecordDAO {
    int deleteByPrimaryKey(Long id);

    int insert(CashRecordEntity record);

    int insertSelective(CashRecordEntity record);

    CashRecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CashRecordEntity record);

    int updateByPrimaryKey(CashRecordEntity record);

    /**
     * 去掉日期验证，因为执行兑付可能滞后，而且我们也有一个前置条件：兑付产品的所有资产是否还款来控制能否执行
     *
     * @param productCode
     * @param loanNoList
     * @return
     */
    List<CashRecordEntity> listCashRecord(@Param("productCode") String productCode, @Param("list") List<String> loanNoList);

    List<CashNotify> queryLoanList(@Param("list") List<String> loanNoList);

    void updateFeeStatusByLoanNo(@Param("loanNo") String loanNo, @Param("status") String status);

    List<CashRecordEntity> listCashRecordLimit(@Param("list") List<TaskItemDefine> list, @Param("limit") int limit);

    /**
     * P
     * 查询产品是否全部兑付完成
     *
     * @param productCode
     * @return
     */
    int countCashRecord(@Param("productCode") String productCode);

    int updateStatusByReqNo(CashRecordEntity record);

    int updateStatusByPCodeAndMemId(CashRecordEntity record);

    /**
     * 批量插入
     *
     * @param list
     */
    void insertCashList(List<CashRecordEntity> list);

    /**
     * 查询总额
     *
     * @param productCode
     */
    CashRecordEntity getSumCashAmount(@Param("productCode") String productCode);

    List<CashRecordEntity> queryCashRecord(CashRecordEntity record);

    CashRecordEntity selectByReqNo(@Param("reqNo") String reqNo);

    /**
     * 以订单纬度查询待提现（成功到余额）的列表
     *
     * @return
     */
    List<CashNotify> withdrawlList();

    /**
     * 以资产纬度查询已经兑付到卡成功的记录
     *
     * @return
     */
    List<CashNotify> queryAssetsByCashEnd();

    /**
     * 以订单纬度查询已经兑付到卡成功的记录
     *
     * @return
     */
    List<CashNotify> queryOrdersByCashEnd();

    List<CashNotify> queryOrdersByCashing();

    void updateCashByCashCard();

    /**
     * 查询兑付过程中调用支付异常的兑付数据
     *
     * @return
     */
    List<String> notifyAssetPartyCashFail();

    void updateUpdateTime(@Param("listStr") String listStr);

    String queryLoanNoListByCreditorNo(@Param("creditorNo") String creditorNo);
}