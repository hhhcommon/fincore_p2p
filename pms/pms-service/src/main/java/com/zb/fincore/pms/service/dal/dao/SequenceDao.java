package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.Sequence;

/**
 * 功能: 序列数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 09:31
 * 版本: V1.0
 */
public interface SequenceDao {

    int insert(Sequence record);

    int insertSelective(Sequence record);

    Sequence selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sequence record);

    int updateByPrimaryKey(Sequence record);

    int deleteByPrimaryKey(Long id);

    /**
     * 根据名称查询ID
     *
     * @param sequenceName
     * @return
     */
    Long selectIdByName(String sequenceName);

    /**
     * 行级悲观锁
     *
     * @param id
     * @return
     */
    Sequence selectForUpdateById(Long id);

    /**
     * 更新键值
     *
     * @param sequence
     * @return
     */
    int updateVal(Sequence sequence);

    Sequence selectByName(String sequenceName);

    int updateValWithLock(Sequence record);
}