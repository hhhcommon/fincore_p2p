package com.zb.p2p.facade.impl;

import com.dianping.cat.message.storage.MessageBlockWriter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.zb.p2p.dao.master.AccountDao;
import com.zb.p2p.dao.master.DailyIncomeDAO;
import com.zb.p2p.enums.CashStatusEnum;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.BatchDailyIncomeReq;
import com.zb.p2p.facade.api.req.DailyIncomeReq;
import com.zb.p2p.facade.api.req.OrderIncomeReq;
import com.zb.p2p.facade.api.req.YesterdayIncomeReq;
import com.zb.p2p.facade.api.resp.AccountAndHistoryIncomeDTO;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.DailyIncomeDTO;
import com.zb.p2p.facade.api.resp.IncomeDTO;
import com.zb.p2p.facade.service.InterestFacade;
import com.zb.p2p.service.incomecalc.IncomeCalcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangwanbin on 2017/9/8.
 */
@Slf4j
public class InterestFacadeImpl implements InterestFacade {
	
    @Autowired
    private IncomeCalcService incomeCalcService;
    
    @Autowired
    private DailyIncomeDAO dailyIncomeDAO;
    
    @Autowired
    private AccountDao accountDao;
      
    /**
     * 缓存5W条数据，30s过期
     */
    /*private LoadingCache<DailyIncomeReq, CommonResp<DailyIncomeDTO>> dailyIncomeReqCommonRespLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(10000 * 5)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<DailyIncomeReq, CommonResp<DailyIncomeDTO>>() {
                        public CommonResp<DailyIncomeDTO> load(DailyIncomeReq key) throws Exception {
                            return incomeCalcService.queryIncome(key);
                        }
                    });
    *//**
     * 缓存5W条数据，30s过期
     *//*
    private LoadingCache<BatchDailyIncomeReq, CommonResp<List<DailyIncomeDTO>>> dailyIncomeListReqCommonRespLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(10000 * 5)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<BatchDailyIncomeReq, CommonResp<List<DailyIncomeDTO>>>() {
                        public CommonResp<List<DailyIncomeDTO>> load(BatchDailyIncomeReq key) throws Exception {
                            return incomeCalcService.queryBatchIncome(key);
                        }
                    });
                    */
    //昨日收益
    private LoadingCache<YesterdayIncomeReq, CommonResp<IncomeDTO>> yesterdayIncomeCache = CacheBuilder.newBuilder()
            .maximumSize(10000 * 5)
            .expireAfterWrite(3, TimeUnit.HOURS) //6小时
            .build(
                    new CacheLoader<YesterdayIncomeReq, CommonResp<IncomeDTO>>() {
                        public CommonResp<IncomeDTO > load(YesterdayIncomeReq key) throws Exception {
                        	CommonResp commonResp = CommonResp.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
                        	
                        	IncomeDTO  incomeDTO = new IncomeDTO(); 
                        	incomeDTO.setIncome( dailyIncomeDAO.queryYesterdayIncome(key.getMemberId(), key.getIncomeDate()));
                        	commonResp.setData(incomeDTO );
                            return commonResp;
                        }
                    });
    
    //订单到期收益
    private LoadingCache<OrderIncomeReq, CommonResp<IncomeDTO>> orderIncomeCache = CacheBuilder.newBuilder()
            .maximumSize(10000 * 5)
            .expireAfterWrite(5, TimeUnit.MINUTES) //1小时
            .build(
                    new CacheLoader<OrderIncomeReq, CommonResp<IncomeDTO>>() {
                        public CommonResp<IncomeDTO > load(OrderIncomeReq key) throws Exception {
                        	CommonResp commonResp = CommonResp.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
                        	
                        	IncomeDTO  incomeDTO = new IncomeDTO(); 
                        	incomeDTO.setIncome(dailyIncomeDAO.queryIncomeByOrderNo(key.getOrderNo()) );
                        	commonResp.setData(incomeDTO );
                            return commonResp;
                        }
                    });
    
    //持仓本金  和  累计收益
    private LoadingCache<DailyIncomeReq, CommonResp<AccountAndHistoryIncomeDTO>> accountAndHistoryIncomeCache = CacheBuilder.newBuilder()
            .maximumSize(10000 * 5)
            .expireAfterWrite(3, TimeUnit.MINUTES) //3分钟
            .build(
                    new CacheLoader<DailyIncomeReq, CommonResp<AccountAndHistoryIncomeDTO>>() {
                        public CommonResp<AccountAndHistoryIncomeDTO > load(DailyIncomeReq key) throws Exception {
                        	CommonResp commonResp = CommonResp.build(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
                        	AccountAndHistoryIncomeDTO accountAndHistoryIncomeDTO = new AccountAndHistoryIncomeDTO();
                            Map<String, Object> param = new HashMap<>();
                            param.put("memberId", key.getMemberId());
                            param.put("status", CashStatusEnum.CASHED_CARD_NOTIFY.getCode());
                            param.put("productCodes", key.getProductCodeList());
                        	accountAndHistoryIncomeDTO.setIncome(dailyIncomeDAO.queryCashedIncome(param));
                        	accountAndHistoryIncomeDTO.setInvestAmount(accountDao.queryAmountByMemberId(param));
                        	commonResp.setData( accountAndHistoryIncomeDTO);
                            return commonResp;
                        }
                    });
    
    @Override
    public CommonResp<IncomeDTO> queryYesterdayIncome(YesterdayIncomeReq req) {
        try {
            return yesterdayIncomeCache.get(req);
        } catch (Exception ex) {
            log.error("异常", ex);
            return CommonResp.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }

    }
    
    @Override
    public CommonResp<IncomeDTO> queryOrderIncome(OrderIncomeReq req) {
        try {
            return orderIncomeCache.get(req);
        } catch (Exception ex) {
            log.error("异常", ex);
            return CommonResp.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }

    }
    
    public CommonResp queryAccountAndHistoryIncome(DailyIncomeReq req) {
        try {
            return accountAndHistoryIncomeCache.get(req);
        } catch (Exception ex) {
            log.error("异常", ex);
            return CommonResp.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }

    }
    
    /**
     * 清除缓存
     */
    public void invalidateCache() {
        try {
        	log.info("清除缓存");
        	yesterdayIncomeCache.invalidateAll();
        	orderIncomeCache.invalidateAll();
            accountAndHistoryIncomeCache.invalidateAll();
        } catch (Exception ex) {
            log.error("异常", ex);
        }

    }

    /*public CommonResp<DailyIncomeDTO> queryIncome(DailyIncomeReq req) {
        try {
            return dailyIncomeReqCommonRespLoadingCache.get(req);
        } catch (Exception ex) {
            log.error("收益查询异常", ex);
            return CommonResp.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }

    }

    @Override
    public CommonResp<List<DailyIncomeDTO>> queryBatchIncome(BatchDailyIncomeReq req) {
        try {
            return dailyIncomeListReqCommonRespLoadingCache.get(req);
        } catch (Exception ex) {
            log.error("批量收益查询异常", ex);
            return CommonResp.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }
    }*/
    
    
}
