package com.zb.p2p.customer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.p2p.customer.dao.domain.NotifyInfo;


@Repository("notifyMapper")
public interface NotifyMapper {
	 int insert(NotifyInfo notifyInfo);
	 
	 int updateBycustomerId(NotifyInfo notifyInfo);
	 
	 int updateBycustomerIdSyn(NotifyInfo notifyInfo);
	 
	 List<NotifyInfo> selectNotifyInfo(NotifyInfo notifyInfo);
}
