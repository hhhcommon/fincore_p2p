package com.zb.p2p.customer.dao;

import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.dao.page.Page;
import com.zb.p2p.customer.dao.page.PageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerInfoMapper")
public interface CustomerInfoMapper {
    int deleteByPrimaryKey(Long customerId);

    int insert(CustomerInfo record);

    int insertSelective(CustomerInfo record);

    CustomerInfo selectByPrimaryKey(Long customerId);

    int updateByPrimaryKeySelective(CustomerInfo record);

    int updateByPrimaryKey(CustomerInfo record);

    int updateByMobile(CustomerInfo record);

    List<PageData> listPage(Page page);

    CustomerInfo selectByAccountId(@Param("channelCustomerId") String channelCustomerId, @Param("channelCode") String channelCode);

    CustomerInfo selectByPrimaryMobile(@Param("mobile") String mobile, @Param("channelCode") String channelCode);

    CustomerInfo selectByPrimaryIden(@Param("idCardNo") String idCardNo);

    CustomerInfo selectByPrimaryIdenAndMobile(@Param("idCardNo") String idCardNo, @Param("mobile") String mobile, @Param("channelCode") String channelCode);

    /**
     * 根据手机号(唯一)查询
     *
     * @param mobile
     * @return
     */
    CustomerInfo selectByUkMobile(@Param("mobile") String mobile);

    int insertList(List<CustomerInfo> customerInfoList);

    int updateHistoryIsBindCard();

    CustomerInfo selectByMemberIdAndType(@Param("memberType") String memberType, @Param("memberId") String memberId);

}