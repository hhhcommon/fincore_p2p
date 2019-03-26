package com.zb.p2p.customer.dao;

import com.zb.p2p.customer.dao.domain.OrgCustomerInfo;
import com.zb.p2p.customer.dao.page.Page;
import com.zb.p2p.customer.dao.page.PageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("orgCustomerInfoMapper")
public interface OrgCustomerInfoMapper {
    int deleteByPrimaryKey(Long orgId);

    int insert(OrgCustomerInfo record);

    int insertSelective(OrgCustomerInfo record);

    OrgCustomerInfo selectByPrimaryKey(Long orgId);

    int updateByPrimaryKeySelective(OrgCustomerInfo record);

    int updateByPrimaryKey(OrgCustomerInfo record);
    
    List<PageData> listPageOrgAcc(Page page);

    /**
     * 根据机构组织代码(唯一)查询
     * @param idCardNo
     * @return
     */
    OrgCustomerInfo selectByUkIdCardNo(@Param("idCardNo") String idCardNo);

    /**
     * 根据系统标识获取机构信息
     * @param sourceId
     * @return
     */
    OrgCustomerInfo selectBySourceId(@Param("sourceId") String sourceId);

    /**
     * 根据渠道会员Id获取机构信息
     * @param channelMemberId
     * @return
     */
    OrgCustomerInfo selectByChannelMemberId(@Param("channelMemberId") String channelMemberId);

    /**
     * 批量插入
     * @param customerList
     */
    int batchInsert(@Param("customerList") List<OrgCustomerInfo> customerList);
}