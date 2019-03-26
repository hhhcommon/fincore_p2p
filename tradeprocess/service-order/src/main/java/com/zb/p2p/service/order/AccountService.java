package com.zb.p2p.service.order;


import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.entity.AccountEntity;
import com.zb.p2p.entity.MatchRecordEntity;

import java.util.List;

/**
 * Created by wangwanbin on 2017/8/31.
 */
public interface AccountService {
    /**
     * 查询未计算收益的账户
     *
     * @param productCode
     * @return
     */
    List<AccountEntity> listAccountUnInterest(String productCode, List<TaskItemDefine> list, int limit);

    /**
     * 更新账户生成收益字段状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateInterestCalcStatus(long id, String status);
    
    public void updateAccountByLoanNo(String loanNo,List<MatchRecordEntity> matchRecordEntityList);
    
    
}
