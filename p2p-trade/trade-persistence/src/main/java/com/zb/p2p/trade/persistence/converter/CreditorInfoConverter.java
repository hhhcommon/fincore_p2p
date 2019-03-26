package com.zb.p2p.trade.persistence.converter;

import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.CreditorStatusEnum;
import com.zb.p2p.trade.persistence.entity.CreditorInfoEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p> 兑付计划转换工具 </p>
 *
 * @author Vinson
 * @version CashPlanConverter.java v1.0 2018/4/21 11:49 Zhengwenquan Exp $
 */
public class CreditorInfoConverter {

    public static CreditorInfo convert(CreditorInfoEntity from){

        if (from == null) {
            return null;
        }
        CreditorInfo to = new CreditorInfo();
        to.setId(from.getId());
        to.setAssetNo(from.getAssetNo());
        to.setFromOrderNo(from.getFromOrderNo());
        to.setLatestCashDate(from.getLatestCashDate());
        to.setLatestPrinciple(from.getLatestPrinciple());
        to.setLatestInterest(from.getLatestInterest());
        to.setMatchId(from.getMatchId());
        to.setCreateTime(from.getCreateTime());
        to.setMemberId(from.getMemberId());
        to.setMemo(from.getMemo());
        to.setOrderNo(from.getOrderNo());
        to.setOrgAssetNo(from.getOrgAssetNo());
        to.setOrgOrderNo(from.getOrgOrderNo());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLoanNo(from.getLoanNo());
        to.setModifyTime(from.getModifyTime());
        to.setPayingPrinciple(from.getPayingPrinciple());
        to.setPayingInterest(from.getPayingInterest());
        to.setPaidPrinciple(from.getPaidPrinciple());
        to.setPaidInterest(from.getPaidInterest());
        to.setStatus(CreditorStatusEnum.getByCode(from.getStatus()));
        to.setServiceFee(from.getServiceFee());

        to.setTransferAssetCode(from.getTransferAssetCode());
        to.setTransferTradeNo(from.getTransferTradeNo());
        to.setInvestAmount(from.getInvestAmount());

        to.setVersion(from.getVersion());
        to.setCreditorNo(from.getCreditorNo());

        return to;
    }

    public static CreditorInfoEntity convert(CreditorInfo from){

        if (from == null || from.getMatchId() == null) {
            return null;
        }
        CreditorInfoEntity to = new CreditorInfoEntity();
        to.setId(from.getId());
        to.setCreditorNo(from.getCreditorNo());
        to.setAssetNo(from.getAssetNo());
        to.setFromOrderNo(from.getFromOrderNo());
        to.setLatestCashDate(from.getLatestCashDate());
        to.setLatestPrinciple(from.getLatestPrinciple());
        to.setLatestInterest(from.getLatestInterest());
        to.setMatchId(from.getMatchId());
        to.setMemberId(from.getMemberId());
        to.setMemo(from.getMemo());
        to.setOrderNo(from.getOrderNo());
        to.setOrgAssetNo(from.getOrgAssetNo());
        to.setOrgOrderNo(from.getOrgOrderNo());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLoanNo(from.getLoanNo());
        to.setModifyTime(from.getModifyTime());
        to.setPayingPrinciple(from.getPayingPrinciple());
        to.setPayingInterest(from.getPayingInterest());
        to.setPaidPrinciple(from.getPaidPrinciple());
        to.setPaidInterest(from.getPaidInterest());
        if (from.getStatus() != null) {
            to.setStatus(from.getStatus().getCode());
        }
        to.setServiceFee(from.getServiceFee());
        to.setTransferAssetCode(from.getTransferAssetCode());
        to.setTransferTradeNo(from.getTransferTradeNo());
        to.setInvestAmount(from.getInvestAmount());

        to.setVersion(from.getVersion());
        to.setCreditorNo(from.getCreditorNo());

        to.setCreateTime(new Date());

        return to;
    }

    public static List<CreditorInfo> convertToList(List<CreditorInfoEntity> fromList){

        if (CollectionUtils.isNullOrEmpty(fromList)) {
            return Collections.emptyList();
        }
        List<CreditorInfo> toList = new ArrayList<>();
        for (CreditorInfoEntity infoEntity : fromList) {
            toList.add(convert(infoEntity));
        }
        return toList;
    }

    public static CreditorInfo convertByMatchRecord(MatchRecord from){

        CreditorInfo to = new CreditorInfo();
//        to.setId(from.getId());
        to.setAssetNo(from.getTransferCode());
        to.setOrgAssetNo(from.getOriginalAssetCode());

        to.setFromOrderNo(from.getParentOrderNo());
        to.setOrderNo(from.getExtOrderNo());
        to.setOrgOrderNo(from.getOriginalOrderNo());

        to.setMatchId(from.getId());
        to.setMemberId(from.getMemberId());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLoanNo(from.getLoanNo());
        to.setModifyTime(from.getModifyTime());
        // 发起转让时，记录转让资产编号
//        to.setTransferAssetCode(from.getTransferCode());
        // 外部查询父债权后回填
//        to.setParentId(from.getParentId());
        to.setStatus(CreditorStatusEnum.INIT);
        to.setInvestAmount(from.getMatchedAmount());
        // 根据转让手续费率计算得来
//        to.setServiceFee(0);

        to.setVersion(from.getVersion());
        to.setCreateTime(new Date());

        return to;
    }

    /**
     * 转换为债权Id集合
     * @param matchRecordList
     * @return
     */
    public static List<Long> convert2CreditorIds(List<MatchRecord> matchRecordList){
        List<Long> creditorIds = new ArrayList<>();
        for (MatchRecord matchRecord : matchRecordList) {
            creditorIds.add(matchRecord.getId());
        }
        return creditorIds;
    }

}
