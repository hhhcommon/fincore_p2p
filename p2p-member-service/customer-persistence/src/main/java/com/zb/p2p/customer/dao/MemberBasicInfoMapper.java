package com.zb.p2p.customer.dao;

import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.domain.MemberBasicInfo;
import com.zb.p2p.customer.dao.page.Page;
import com.zb.p2p.customer.dao.page.PageData;
import com.zb.p2p.customer.dao.query.req.PerCustCond;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("memberInfoMapper")
public interface MemberBasicInfoMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(MemberBasicInfo record);

    int insertSelective(MemberBasicInfo record);

    MemberBasicInfo selectByPrimaryKey(String memberId);

    int updateByPrimaryKeySelective(MemberBasicInfo record);

    int updateByPrimaryKey(MemberBasicInfo record);

    int updateByMobile(MemberBasicInfo record);

    MemberBasicInfo selectByPrimaryMobile(@Param("mobile") String mobile, @Param("channelCode") String channelCode);

    MemberBasicInfo selectByPrimaryIden(@Param("idCardNo") String idCardNo);

    MemberBasicInfo selectByPrimaryIdenAndMobile(@Param("idCardNo") String idCardNo, @Param("mobile") String mobile, @Param("channelCode") String channelCode);

    /**
     * 根据手机号(唯一)查询
     *
     * @param mobile
     * @return
     */
    MemberBasicInfo selectByUkMobile(@Param("mobile") String mobile);

    /**
     * 根据证件号查询（只有基本字段）
     *
     * @param customerInfoList
     * @return
     */
    int insertList(List<MemberBasicInfo> customerInfoList);

    MemberBasicInfo queryBySelected(PerCustCond perCustCond);

    List<PageData> listPage(Page page);

    MemberBasicInfo selectByTxsAccountId(@Param("channelCustomerId") String channelCustomerId, @Param("channelCode") String channelCode);
}