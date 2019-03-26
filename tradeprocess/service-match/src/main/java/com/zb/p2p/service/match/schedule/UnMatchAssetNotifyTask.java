package com.zb.p2p.service.match.schedule;
/*
package com.zb.p2p.service.match.impl;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.common.exception.BusinessException;
import com.zb.p2p.enums.LoanStatusEnum;
import com.zb.p2p.enums.SequenceEnum;
import com.zb.p2p.facade.service.internal.OrderInternalService;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.service.callback.MSDCallBackService;
import com.zb.p2p.service.callback.api.req.NotifyLoanStatusReq;
import com.zb.p2p.service.callback.api.req.NotifyUnMatchedOrderReq;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

*/
/**
 * 日切未匹配资产通知马上贷
 * @author zhangxin
 * @create 2017-09-07 17:23
 *//*

public class UnMatchAssetNotifyTask implements IScheduleTaskDealSingle<LoanRequestDTO>{
    private static Logger logger = LoggerFactory.getLogger(UnMatchAssetNotifyTask.class);

    @Autowired
    private OrderInternalService orderInternalService;

    @Autowired
    private MSDCallBackService msdCallBackService;

    @Override
    public boolean execute(LoanRequestDTO loanRequestDTO, String s) throws Exception {
        return true;
    }

    @Override
    public List<LoanRequestDTO> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1) throws Exception {
        logger.info("UnMatchAssetNotifyTask start running ....");
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> loanStatusList = new ArrayList<String>();
        loanStatusList.add(LoanStatusEnum.LOAN_UNMATCHED.getCode());
        loanStatusList.add(LoanStatusEnum.LOAN_FAILED.getCode());
        loanStatusList.add(LoanStatusEnum.LOAN_LOCKED.getCode());
        params.put("loanStatusList", loanStatusList);
        params.put("queryLastDayUnMatchedLoan", "1");
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日期减1
        Date lastDay = ca.getTime(); //当前日期前一天
        params.put("lastDay", lastDay);
        List<LoanRequestDTO> loanRequestList = orderInternalService.queryLoanListByParams(params);
        if (CollectionUtils.isNullOrEmpty(loanRequestList)) {
            return null;
        }

        List<String> originTransactionNoList = new ArrayList<String>();
        for(LoanRequestDTO loanRequestDTO: loanRequestList){
            originTransactionNoList.add(loanRequestDTO.getLoanNo());
        }
        //请求参数封装
        //用日期做流水号
        NotifyLoanStatusReq notifyLoanStatusReq = new NotifyLoanStatusReq();
        notifyLoanStatusReq.setTransactionNo(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYMMDD));
        notifyLoanStatusReq.setNotifyType("1");
        notifyLoanStatusReq.setStatus("0");//放款失败
        notifyLoanStatusReq.setResultCode("9999");
        notifyLoanStatusReq.setResultMessage("日切资产未匹配。");
        notifyLoanStatusReq.setRequestTime(DateUtils.format(new Date(), DateUtils.DEFAULT_DATETIME_FORMAT));
        notifyLoanStatusReq.setOriginTransactionNoList(originTransactionNoList);
        // 调用远程服务
        logger.debug("日切通知马上贷更新资产放款状态请求参数：" + notifyLoanStatusReq);
        NotifyResp notifyOrderResp = msdCallBackService.notifyLoanStatus(notifyLoanStatusReq);
        logger.debug("日切通知马上贷更新资产放款状态响应参数：" + notifyOrderResp);
        // 判断远程URl调用是否成功
        if (null != notifyOrderResp && !"0000".equals(notifyOrderResp.getCode())) {
            logger.info("日切通知马上贷更新资产放款状态失败:" + notifyOrderResp.getMsg());
        }
        logger.info("UnMatchAssetNotifyTask end running ....");
        return null;
    }

    @Override
    public Comparator<LoanRequestDTO> getComparator() {
        return null;
    }
}
*/
