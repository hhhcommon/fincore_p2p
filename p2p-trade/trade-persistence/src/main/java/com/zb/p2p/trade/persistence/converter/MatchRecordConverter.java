package com.zb.p2p.trade.persistence.converter;

import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.trade.common.domain.MatchRecord;
import com.zb.p2p.trade.common.enums.MatchStatusEnum;
import com.zb.p2p.trade.persistence.entity.MatchRecordEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version MatchRecordConverter.java v1.0 2018/4/21 17:55 Zhengwenquan Exp $
 */
public class MatchRecordConverter {

    public static MatchRecord convert(MatchRecordEntity from){

        MatchRecord to = new MatchRecord();
        to.setId(from.getId());
        to.setAccountStatus(from.getAccountStatus());
//        to.setCashFlag(from.getCashFlag());
        to.setExtOrderNo(from.getExtOrderNo());
        to.setLoanAmount(from.getLoanAmount());
        to.setLoanCharge(from.getLoanCharge());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLoanNo(from.getLoanNo());
        to.setMatchedAmount(from.getMatchedAmount());
        to.setMatchStatus(MatchStatusEnum.getByCode(from.getMatchStatus()));
        to.setMemberId(from.getMemberId());
        to.setModifyTime(from.getModifyTime());
        to.setOrderNo(from.getOrderNo());
        to.setProductCode(from.getProductCode());
        to.setTotalIncome(from.getTotalIncome());

        to.setTransferCode(from.getTransferCode());
        to.setOriginalAssetCode(from.getOriginalAssetCode());
        to.setParentOrderNo(from.getParentOrderNo());
        to.setOriginalOrderNo(from.getOriginalOrderNo());

        to.setVersion(from.getVersion());

        return to;
    }

    public static MatchRecordEntity convert(MatchRecord from){

        MatchRecordEntity to = new MatchRecordEntity();
        to.setId(from.getId());
        to.setAccountStatus(from.getAccountStatus());
//        to.setCashFlag(from.getCashFlag());
        to.setExtOrderNo(from.getExtOrderNo());
        to.setLoanAmount(from.getLoanAmount());
        to.setLoanCharge(from.getLoanCharge());
        to.setLoanMemberId(from.getLoanMemberId());
        to.setLoanNo(from.getLoanNo());
        to.setMatchedAmount(from.getMatchedAmount());
        if (from.getMatchStatus() != null) {
            to.setMatchStatus(from.getMatchStatus().getCode());
        }
        to.setMemberId(from.getMemberId());
        to.setModifyTime(from.getModifyTime());
        to.setOrderNo(from.getOrderNo());
        to.setProductCode(from.getProductCode());
        to.setTotalIncome(from.getTotalIncome());

        to.setTransferCode(from.getTransferCode());
        to.setOriginalAssetCode(from.getOriginalAssetCode());
        to.setParentOrderNo(from.getParentOrderNo());
        to.setOriginalOrderNo(from.getOriginalOrderNo());

        to.setVersion(from.getVersion());

        return to;
    }

    public static List<MatchRecord> convertToList(List<MatchRecordEntity> fromList){

        if (CollectionUtils.isNullOrEmpty(fromList)) {
            return Collections.emptyList();
        }
        List<MatchRecord> toList = new ArrayList<>();
        for (MatchRecordEntity matchEntity : fromList) {
            toList.add(convert(matchEntity));
        }
        return toList;
    }

}
