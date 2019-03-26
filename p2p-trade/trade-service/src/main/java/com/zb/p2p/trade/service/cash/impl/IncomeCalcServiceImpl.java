package com.zb.p2p.trade.service.cash.impl;

import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.trade.api.req.DailyIncomeReq;
import com.zb.p2p.trade.api.req.InvestOrderIncomeReq;
import com.zb.p2p.trade.api.resp.IncomeDto;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.common.enums.YesNoEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.common.util.JsonUtil;
import com.zb.p2p.trade.common.util.StringUtils;
import com.zb.p2p.trade.persistence.dao.DailyIncomeMapper;
import com.zb.p2p.trade.persistence.dao.MatchRecordMapper;
import com.zb.p2p.trade.persistence.dao.OrderRequestMapper;
import com.zb.p2p.trade.persistence.entity.DailyIncomeEntity;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.MatchRecordEntity;
import com.zb.p2p.trade.persistence.entity.OrderRequestEntity;
import com.zb.p2p.trade.service.cash.IncomeCalcService;
import com.zb.p2p.trade.service.common.DistributedLockService;
import com.zb.p2p.trade.service.common.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wangwanbin on 2017/8/29.
 * 收益计算服务
 */
@Service
@Slf4j
public class IncomeCalcServiceImpl implements IncomeCalcService {


    @Autowired
    private DailyIncomeMapper dailyIncomeMapper;
    @Autowired
    private MatchRecordMapper matchRecordMapper;
    @Autowired
    private OrderRequestMapper orderRequestMapper;

    @Autowired
    private DistributedLockService distributedLockService;
    @Autowired
    private RedisService redisService;


    /**
     * 收益计算
     */
    @Transactional(rollbackFor = Exception.class)
    public void incomeCalc(LoanRequestEntity loanRequest) {
        String assetCode = loanRequest.getTransferCode();
        String lockKey = StringUtils.concatStrBy_(GlobalVar.GLOBAL_INCOME_CALC_LOCK_KEY, assetCode);
        try {
            log.info("计算收益 开始 LockKey：[{}],", lockKey);

            distributedLockService.tryLock(lockKey);

            // 以资产维度查询所有未生成收益的匹配记录
            List<MatchRecordEntity> matchRecordList = matchRecordMapper.selectList(assetCode, YesNoEnum.NO.getCode(), null);

            Map<String, BigDecimal> orderInterestMap = new HashMap<>();
            if (matchRecordList != null && matchRecordList.size() > 0) {
                for (MatchRecordEntity matchRecordEntity : matchRecordList) {
                    BigDecimal tmpOrderInterest = orderInterestMap.get(matchRecordEntity.getExtOrderNo());
                    BigDecimal matchInterest = incomeCalcProcess(matchRecordEntity, loanRequest);
                    log.info("投资匹配记录[{}]生成的总收益为[{}]", matchRecordEntity.getId(), matchInterest);
                    if (tmpOrderInterest != null) {
                        orderInterestMap.put(matchRecordEntity.getExtOrderNo(), tmpOrderInterest.add(matchInterest));
                    } else {
                        orderInterestMap.put(matchRecordEntity.getExtOrderNo(), matchInterest);
                    }
                }
            }
            // 更新订单总收益
            for (Map.Entry<String, BigDecimal> entry : orderInterestMap.entrySet()) {
                int rows = orderRequestMapper.updateIncome(entry.getValue(), entry.getKey());
                log.info("更新订单[{}]总收益[{}]，影响行数[{}]", entry.getKey(), entry.getValue(), rows);
            }
            log.info("计算收益 结束 LockKey[{}],", lockKey);
        } catch (Exception e) {
            log.error("计算收益任务异常", e);
        } finally {
            try {
                distributedLockService.unLockAndDel(lockKey);
            } catch (Exception e1) {
                log.error("计算收益解锁异常", e1);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public BigDecimal incomeCalcProcess(MatchRecordEntity matchRecordEntity, LoanRequestEntity loanRequestEntity) {

        log.info("计算匹配记录的收益MatchId：[{}]", matchRecordEntity.getId());

        String assetCode = loanRequestEntity.getTransferCode();
        Date startDate = loanRequestEntity.getValueStartTime();
        BigDecimal yield = loanRequestEntity.getLoanRate();  //投资利率和借款利率相等
        int productLimit = loanRequestEntity.getLockDate(); // 投资期限
        RepaymentTypeEnum repaymentType = RepaymentTypeEnum.getByRepayType(loanRequestEntity.getRepaymentType());

        List<DailyIncomeEntity> dailyIncomeEntityList = new ArrayList<>(productLimit);
        BigDecimal unit = matchRecordEntity.getMatchedAmount()
                .multiply(yield)
                .divide(GlobalVar.YEAR_DAYS_365, 8, BigDecimal.ROUND_DOWN);

        log.info("投资匹配记录[{}]将生成[{}]条每日收益记录", matchRecordEntity.getId(), productLimit);
        BigDecimal interestedTotal = BigDecimal.ZERO;
        for (int i = 0; i < productLimit; i++) {
            DailyIncomeEntity dailyIncomeEntity = new DailyIncomeEntity();
            dailyIncomeEntity.setMemberId(matchRecordEntity.getMemberId());
            dailyIncomeEntity.setProductCode(loanRequestEntity.getProductCode());
            dailyIncomeEntity.setAssetNo(assetCode);
            dailyIncomeEntity.setRefNo(String.valueOf(matchRecordEntity.getId()));
            dailyIncomeEntity.setInterestAmount(new BigDecimal(i + 1).multiply(unit)
                    .setScale(2, BigDecimal.ROUND_DOWN).subtract(interestedTotal));
            dailyIncomeEntity.setInterestDate(DateUtils.addDay(startDate, i));
            if (repaymentType != null) {
                dailyIncomeEntity.setRepayType(repaymentType.getCode());
            }
            dailyIncomeEntityList.add(dailyIncomeEntity);
            interestedTotal = interestedTotal.add(dailyIncomeEntity.getInterestAmount());
        }

        int dailyIncomeCount = 0;

        if (!dailyIncomeEntityList.isEmpty()) {
            dailyIncomeCount = dailyIncomeMapper.insertList(dailyIncomeEntityList);
        }

        //记录总收益及标记收益已生成
        int matchRecordCount = matchRecordMapper.updateIncome(matchRecordEntity.getId(),
                YesNoEnum.YES.getCode(), interestedTotal);

        Assert.isTrue(matchRecordCount == 1, "更新匹配收益生成标记失败");

        if (dailyIncomeCount != productLimit) {
            log.error("收益计算失败，每日收益插入数量=[{}]，应插入数量=[{}]", dailyIncomeCount, productLimit);
            throw new BusinessException("收益计算失败，每日收益插入数量不等于应插入数量");
        }
        return interestedTotal;
    }


    /**
     * 查询累计收益
     *
     * @param req
     * @return
     */
    @Override
    @Cacheable(value = "Daily_Income_Cache", key = "'Income'.concat(#req.memberId.concat(#req.incomeDate))")
    public IncomeDto queryIncome(DailyIncomeReq req) {
        log.info("从数据库查询数据：{}", req);
        Date incomeDate = DateUtils.parse(req.getIncomeDate(), DateUtils.DATE_FORMAT_YYMMDD);
        BigDecimal totalIncome = dailyIncomeMapper.queryTotalIncome(req.getMemberId(), req.getProductCode(), incomeDate);

        IncomeDto respDto = new IncomeDto();
        respDto.setIncomeAmount(totalIncome == null ? BigDecimal.ZERO : totalIncome);
        return respDto;
    }

    @Override
    public IncomeDto queryYesterdayIncome(DailyIncomeReq req) {
        IncomeDto res = null;
        // 日期校验不通过则默认为当 前日期
        req.setIncomeDate(DateUtil.isDateString(req.getIncomeDate(), DateUtil.SHORT_FORMAT));
        String cacheKey = StringUtils.concatStrBy_(req.getMemberId(), req.getIncomeDate());
        try {
            // 缓存中查询结果
            String cacheResult = redisService.existedAndGet(cacheKey);
            if (StringUtils.isNotEmpty(cacheResult)) {
                res = (IncomeDto) JsonUtil.getObjectByJsonStr(cacheResult, IncomeDto.class);
            }
            // 查询本地
            res = res == null ? queryIncome(req) : res;
            if (res != null) {
                redisService.set(cacheKey, JsonUtil.convertObjToStr(res), GlobalVar.INCOME_REDIS_CACHE_LIVE_TIME);
            }
        } catch (Exception ex) {
            log.error("查询缓存异常", ex);
        }
        return res;
    }

    @Override
    @Cacheable(value = "Order_Income_Cache", key = "'OrderIncome'.concat(#req.extOrderNo)")
    public IncomeDto queryInvestOrderIncome(InvestOrderIncomeReq req) {
        IncomeDto incomeDto = new IncomeDto();
        OrderRequestEntity orderRequest = orderRequestMapper.selectByOrderNo(req.getExtOrderNo());
        if (orderRequest != null) {
            incomeDto.setIncomeAmount(orderRequest.getTotalIncome());
        }

        return incomeDto;
    }
}
