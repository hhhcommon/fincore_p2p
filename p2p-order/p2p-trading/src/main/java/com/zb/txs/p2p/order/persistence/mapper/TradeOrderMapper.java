package com.zb.txs.p2p.order.persistence.mapper;

import com.zb.txs.p2p.business.invest.request.InvestmentRecordRequest;
import com.zb.txs.p2p.investment.util.Page;
import com.zb.txs.p2p.order.persistence.model.TradeOrder;
import com.zb.txs.p2p.order.persistence.model.TradeOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TradeOrderMapper {

    int insertSelective(TradeOrder record);

    int updateByPrimaryKeySelective(TradeOrder record);

    int updateMatchStatus(@Param("id") Long appointId, @Param("targetState") String targetState, @Param("currentState") String currentState);

    List<TradeOrder> selectOrderListByLastId(InvestmentRecordRequest reqPara);

    TradeOrder selectByPrimaryKey(@Param("id") Long id);

    List<TradeOrder> selectOrderListByLastIdSort(@Param("memberId") String memberId, @Param("lastId") Long lastId, @Param("pageSize") Long pageSize);

    List<TradeOrder> selectOrderListPaySort(@Param("memberId") String memberId, @Param("lastId") Long lastId, @Param("pageSize") Long pageSize);

    List<TradeOrder> selectOrderListCashSort(@Param("memberId") String memberId, @Param("lastId") Long lastId, @Param("pageSize") Long pageSize);

    List<TradeOrder> selectByExample(TradeOrderExample example);

    TradeOrder selectByIdAndMemberIdAndAmount(TradeOrder record);

    int contByDatePage(TradeOrder record, Page page);

    List<TradeOrder> findByDatePage(TradeOrder record, Page page);

    List<TradeOrder> queryOrderFailure(@Param("queryTime") Date queryTime);

    int contByMatchStatus(@Param("productCode") String productCode);

    TradeOrder selectByOrderId(TradeOrder record);

    List<TradeOrder> queryOrderListByParams(Map<String, Object> params);

    int countByProductPage(TradeOrder record, Page page);

    List<TradeOrder> findByProductPage(TradeOrder record, Page page);
}