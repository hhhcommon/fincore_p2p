package com.zillionfortune.boss.service.history;

import java.util.List;

import com.zillionfortune.boss.dal.entity.HistoryQueryByPageConvert;
import com.zillionfortune.boss.dal.entity.OperationHistory;

public interface OperationHistoryService {
	
	/**
	 * 添加操作记录
	 */
	public boolean insertOperationHistory(OperationHistory dto);
	
    /**
	 * selectByPrimaryKey:根据主键查询权限. <br/>
	 *
	 * @param id
	 * @return
	 */
	public OperationHistory selectByPrimaryKey(Integer id);
	
	/**
	 * queryByPage:查询日志（分页） <br/>
	 *
	 * @param history
	 * @return
	 */
	public List<HistoryQueryByPageConvert> queryByPage(OperationHistory history);
	
	/**
	 * selectBySelectiveCount:根据条件统计条数. <br/>
	 *
	 * @param history
	 * @return
	 */
	public int selectBySelectiveCount(OperationHistory history);
	
	/**
	 * selectBySelective:选择性查询. <br/>
	 *
	 * @param history
	 * @return
	 */
	public List<OperationHistory> selectBySelective(OperationHistory history);
	
}
