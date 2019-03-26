/**
 * 
 */
package com.zb.p2p.customer.service;

import com.zb.p2p.customer.api.entity.CustomerReq;
import com.zb.p2p.customer.api.entity.LoginDeviceDetail;

/**
 * 登录设备记录
 * @author liguoliang
 *
 */
public interface DeviceService {

	/**
	 * 查询登录设备
	 * @param customerId
	 * @return
	 */
	LoginDeviceDetail getLoginDevice(Long customerId);


	/**
	 * 记录登录设备
	 * @param req
	 * @return
	 */
	int insertSelective(CustomerReq req);

}
