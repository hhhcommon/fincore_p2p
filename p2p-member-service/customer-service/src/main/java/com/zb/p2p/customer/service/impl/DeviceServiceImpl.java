/**
 * 
 */
package com.zb.p2p.customer.service.impl;

import com.zb.p2p.customer.api.entity.CustomerReq;
import com.zb.p2p.customer.api.entity.LoginDeviceDetail;
import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.dao.LoginDeviceMapper;
import com.zb.p2p.customer.dao.domain.LoginDevice;
import com.zb.p2p.customer.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录设备记录
 * @author liguoliang
 *
 */
@Service
public class DeviceServiceImpl implements DeviceService {
	//private static final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);
    @Resource
    private LoginDeviceMapper loginDeviceMapper;

	@Override
	public LoginDeviceDetail getLoginDevice(Long customerId) {
		List<LoginDevice> infoList = loginDeviceMapper.selectByCustomerId(customerId);
		if (infoList == null || infoList.size() <= 0) {
			return null;
		}
		LoginDeviceDetail loginDeviceDetail = new LoginDeviceDetail();
		for(int i=0;i<infoList.size();i++)
		{
			LoginDevice item1 = (LoginDevice)infoList.get(0);
			loginDeviceDetail.setLoginDevice(item1.getModel());
			if (infoList.size()>1){
				LoginDevice item2 = (LoginDevice)infoList.get(1);
				loginDeviceDetail.setHisDevice(item2.getModel());
				loginDeviceDetail.setLastLoginTime(DateUtil.format(item2.getCreateTime(),DateUtil.NEW_FORMAT));
			}
		}

		return loginDeviceDetail;
	}



	@Override
	public int insertSelective(CustomerReq req) {
		LoginDevice record = new LoginDevice();
		record.setCustomerId(req.getCustomerId());
		record.setModel(req.getModel());
		record.setName(req.getName());
		record.setSerialNo(req.getSerialNo());
		return loginDeviceMapper.insertSelective(record);
	}


}
