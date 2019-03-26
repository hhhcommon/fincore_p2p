package com.zb.p2p.service.order.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.exception.CommonException;
import com.zb.p2p.dao.master.AccountDao;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.entity.AccountEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.enums.SequenceEnum;
import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.service.common.DistributedSerialNoService;
import com.zb.p2p.service.order.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by wangwanbin on 2017/8/31.
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
	
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;
    
    @Autowired
    private DistributedLockService distributedLockService;
    
    @Autowired
    private MatchRecordDAO matchRecordDAO;

    @Override
    public List<AccountEntity> listAccountUnInterest(String productCode, List<TaskItemDefine> list, int limit) {
        return accountDao.listAccountUnInterest(productCode, list, limit);
    }

    @Override
    public int updateInterestCalcStatus(long id, String status) {
        return accountDao.updateInterestCalcStatus(id, status);
    }
    
    /**
     * 根据匹配结果     修改持仓
     * @param loanNo   借款单号
     * @return
     * @throws Exception 
     */ 
    public void updateAccountByLoanNo(String loanNo,List<MatchRecordEntity> matchRecordEntityList) {
    	  
    	for (MatchRecordEntity matchRecordEntity : matchRecordEntityList) {
    		updateAccountByMatchRecord(  matchRecordEntity);
		}
    }
    
   // @Transactional(rollbackFor = Exception.class)
    public void updateAccountByMatchRecord(MatchRecordEntity matchRecordEntity) throws CommonException {
    	AccountEntity account = accountDao.selectByMemberIdAndProductCode(matchRecordEntity.getMemberId(), matchRecordEntity.getProductCode());
		
		Date timeNow = new Date();
		 
		int accountCount = 0;
		
		if(account == null ){
			account = new AccountEntity();
			account.setAccountNo(distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.ACCOUNT_NO,"", 1) );
			account.setAmount(matchRecordEntity.getMatchedAmount());
//			account.setCreateBy( ); 
			account.setCreateTime(timeNow);
			account.setInterest(new BigDecimal(0));
			//account.setInterestFlag( "0");
			account.setMemberId(matchRecordEntity.getMemberId() );
//			account.setModifyBy(modifyBy);
			account.setModifyTime(timeNow);
			account.setProductCode(matchRecordEntity.getProductCode() );
//			account.setSaleChannel( );
			  
			accountCount = accountDao.insertSelective(account);
			 
		}else{
			accountCount = accountDao.increAccount(account.getId(), matchRecordEntity.getMatchedAmount(),null);
		}
		
		//修改匹配表的持仓状态
		int matchRecordCount = matchRecordDAO.updateAccountStatus("TRUE", matchRecordEntity.getId());
		
		if(accountCount <= 0 || matchRecordCount <= 0  ){
			throw new CommonException("更新持仓失败");
		} 
    }
}
