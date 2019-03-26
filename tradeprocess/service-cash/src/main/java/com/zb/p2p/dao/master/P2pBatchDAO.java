package com.zb.p2p.dao.master;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.entity.P2pBatchEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface P2pBatchDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(P2pBatchEntity record);

    int insertSelective(P2pBatchEntity record);

    P2pBatchEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(P2pBatchEntity record);

    int updateByPrimaryKey(P2pBatchEntity record);

    /**
     * 根据交易业务代码查询批次列表
     *
     * @return
     */
    List<P2pBatchEntity> listBatchByType(@Param("batchType") String batchType, @Param("status") String status,
                                         @Param("list") List<TaskItemDefine> list, @Param("limit") int limit);


    List<String> listBatchProduct();

    /**
     * 根据产品计算总笔数
     *
     * @param record
     * @return
     */
    int countBatch(P2pBatchEntity record);
    /**
     * 更新状态
     *
     * @return
     */
    int updateBatchStatus(P2pBatchEntity record);

    /**
     * 根据productCode和batchType查询记录
     *
     * @return
     */
    P2pBatchEntity selectBatchByType(P2pBatchEntity record);

    P2pBatchEntity selectByReqNo(@Param("reqNo") String reqNo);

}