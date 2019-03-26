package com.zb.p2p.customer.dao;


import com.zb.p2p.customer.dao.domain.LoginDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginDeviceMapper {

    int insertSelective(LoginDevice record);

    LoginDevice selectByPrimaryKey(Long id);

    List<LoginDevice> selectByCustomerId(@Param("customerId") Long customerId);

}