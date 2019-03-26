/**
 * 
 */
package com.zb.p2p.customer.service.convert;

import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.Page;
import com.zb.p2p.customer.common.util.DateUtil;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.dao.domain.page.PerCustPage;
import com.zb.p2p.customer.dao.page.PageData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 个人会员分页列表转换
 * @author guolitao
 *
 */
public class PerCustPageListConverter {

	public static Page<CustomerDetail> convert(PerCustPage page,List<PageData> pageList){
		Page<CustomerDetail> result = new Page<>();
		//分页信息
		result.setPageNo(page.getPageNo());
		result.setPageSize(page.getPageSize());
		result.setTotalRecordsCount(page.getTotalRecordsCount());
		result.setTotalPage(page.getTotalPage());
		//结果
		List<CustomerDetail> rows = new ArrayList<>();
		result.setRows(rows);
		if(pageList != null && !pageList.isEmpty()){
			CustomerDetail cd = null;
			Integer freshStatus = null;
			Integer fixStatus = null;
			for(PageData pd : pageList){
				cd = new CustomerDetail();
				cd.setCustomerId(""+pd.get("customerId"));
				//基本信息
				cd.setIdCardNo(pd.getString("idCardNo"));
				cd.setIdCardType(pd.getString("idCardType"));
				cd.setIsBindCard((Integer)pd.get("isBindCard"));
				cd.setIsRealName((Integer)pd.get("isReal"));
				cd.setIsRiskRate((Integer)pd.get("isRiskRate"));
				cd.setMobile(pd.getString("mobile"));
				cd.setName(pd.getString("realName"));
				cd.setRegisterTime(DateUtil.formatDateTime((Date)pd.get("registerTime")));
				cd.setIsOpenAccount(StringUtils.isBlank(pd.getString("accountNo")) ? "0":"1");
				cd.setIsActiveEAccount((Integer)pd.get("isActiveEAccount"));
				cd.setIsDepositManage((Integer)pd.get("isDepositManage"));
				freshStatus = (Integer)pd.get("buyFreshProductStatus");
				fixStatus = (Integer)pd.get("buyFixedProductStatus");
				if(2 == freshStatus){
					//新手标锁定，可购
					cd.setCanFresh(1);//1---可购
				}else if(1 == fixStatus || 1== freshStatus){
					cd.setCanFresh(0);//0---不可购
				}else{
					cd.setCanFresh(1);//1---可购
				}
				cd.setBuyFreshProductStatus(freshStatus);
				cd.setBuyFixedProductStatus(fixStatus);
				cd.setChannelCustomerId(pd.getString("channelCustomerId"));
				cd.setChannelCode(pd.getString("channelCode"));
				cd.setLoginPwd(pd.getString("loginPwd"));
				rows.add(cd);
			}
		}
		return result;
	}
}
