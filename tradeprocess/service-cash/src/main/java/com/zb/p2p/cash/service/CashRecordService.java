package com.zb.p2p.cash.service;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.entity.*;
import com.zb.p2p.enums.CashStatusEnum;
import com.zb.p2p.enums.CashTypeEnum;
import com.zb.p2p.facade.api.req.CashRecordReq;
import com.zb.p2p.facade.api.resp.CashRecordDTO;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.service.internal.dto.MatchRecordDTO;
import com.zb.p2p.service.callback.api.req.NotifyCashResultReq;
import com.zb.p2p.service.callback.api.resp.RetryResp;
import com.zb.payment.msd.cashier.facade.dto.req.CashReqDTO;
import com.zb.payment.msd.cashier.facade.dto.req.QueryTradeStatusReqDTO;
import com.zb.payment.msd.cashier.facade.dto.req.TailBalanceTranferReqDTO;

import java.util.List;

/**
 * Function:兑付服务
 * Author: created by liguoliang
 * Date: 2017/8/31 0031 上午 11:16
 * Version: 1.0
 */
public interface CashRecordService {

    boolean execCash(String productCode) throws Exception;

    void cashProcess(String productCode, List<String> loanNoList, CashTypeEnum cashTypeEnum) throws Exception;

    void saveCashRecordAndBatch(MatchRecordDTO matchRecordEntity, CashRecordEntity cashRecord) throws Exception;

    void notifyAssetParty(List<String> loanList, CashStatusEnum cashStatusEnum);
}
