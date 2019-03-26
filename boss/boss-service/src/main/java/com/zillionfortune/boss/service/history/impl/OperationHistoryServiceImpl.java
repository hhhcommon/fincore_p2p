package com.zillionfortune.boss.service.history.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zillionfortune.boss.dal.dao.OperationHistoryDao;
import com.zillionfortune.boss.dal.entity.HistoryQueryByPageConvert;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.service.history.OperationHistoryService;

@Service
public class OperationHistoryServiceImpl implements OperationHistoryService {
	
	@Autowired
	private OperationHistoryDao opOperationHistoryDao;

	@Override
	public boolean insertOperationHistory(OperationHistory dto) {
		int res = 0;
		if(dto!=null){
			//dto.setCreateBy(CommonConstants.TRADE_OPERATE_SYSTEM);
			//dto.setModifyBy(CommonConstants.TRADE_OPERATE_SYSTEM);
			res = opOperationHistoryDao.insertSelective(dto);
		}
		return res>0;
	}
	
	@Override
	public OperationHistory selectByPrimaryKey(Integer id) {
		return opOperationHistoryDao.selectByPrimaryKey(id);
	}

	@Override
	public List<HistoryQueryByPageConvert> queryByPage(OperationHistory history) {
		return opOperationHistoryDao.queryByPage(history);
	}

	@Override
	public int selectBySelectiveCount(OperationHistory history) {
		return opOperationHistoryDao.selectBySelectiveCount(history);
	}

	@Override
	public List<OperationHistory> selectBySelective(OperationHistory history) {
		return opOperationHistoryDao.selectBySelective(history);
	}

}
