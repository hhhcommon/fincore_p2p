/**
 * 
 */
package com.zb.p2p.customer.service.convert;

import java.util.ArrayList;
import java.util.List;

import com.zb.p2p.customer.api.entity.OrgCustomerDetail;
import com.zb.p2p.customer.api.entity.Page;
import com.zb.p2p.customer.dao.domain.page.PerCustPage;
import com.zb.p2p.customer.dao.page.PageData;

/**
 * @author guolitao
 *
 */
public class OrgCustPageListConverter {

	public static Page<OrgCustomerDetail> convert(PerCustPage page,List<PageData> pageList){
		Page<OrgCustomerDetail> result = new Page<OrgCustomerDetail>();
		//分页信息
		result.setPageNo(page.getPageNo());
		result.setPageSize(page.getPageSize());
		result.setTotalRecordsCount(page.getTotalRecordsCount());
		result.setTotalPage(page.getTotalPage());
		//结果
		List<OrgCustomerDetail> rows = new ArrayList<>();
		result.setRows(rows);
		if(pageList != null && !pageList.isEmpty()){
			OrgCustomerDetail cd = null;
			for(PageData pd : pageList){
				cd = new OrgCustomerDetail();
				//基本信息
				cd.setIdCardNo(pd.getString("idCardNo"));
				cd.setIdCardType(pd.getString("idCardType"));
				cd.setOrgId(""+pd.get("orgId"));
				cd.setOrgName(pd.getString("orgName"));
				
				rows.add(cd);
			}
		}
		return result;
	}
}
