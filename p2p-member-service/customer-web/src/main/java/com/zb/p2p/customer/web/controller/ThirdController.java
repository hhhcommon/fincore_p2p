/**
 * 
 */
package com.zb.p2p.customer.web.controller;

import javax.annotation.Resource;

import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.service.config.ReadOnlyConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.p2p.customer.api.ThirdAPI;
import com.zb.p2p.customer.api.entity.CustomerCardBin;
import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.ThirdCustReq;
import com.zb.p2p.customer.api.entity.currency.CustomerAcountDetail;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.service.BalanceService;
import com.zb.p2p.customer.service.InfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author guolitao
 *
 */
@Api(value = "第三方查询控制器", description = "第三方接口")
@Controller
@RequestMapping("/third")
public class ThirdController implements ThirdAPI {

	private static final Logger logger = LoggerFactory.getLogger(ThirdController.class);
	@Resource
	private InfoService infoService;
	@Resource
	private BalanceService balanceService;
	
	@ReadOnlyConnection
	@ApiOperation(value = "会员绑卡查询", httpMethod = "POST", produces = "application/json")
	@ApiResponse(code = 200, message = "success", response = BaseRes.class)
	@ResponseBody
	@RequestMapping(value = "/bankCardBindInfo", method = RequestMethod.POST, produces = "application/json")
	@Override
	public BaseRes<CustomerCardBin> bankCardBindInfo(@RequestBody ThirdCustReq req) {
		logger.info("clientNo=" + req.getClientNo() + ",clientSeqNo=" + req.getClientSeqNo() + ",qjsCustomerId="
				+ req.getQjsCustomerId());
		BaseRes<CustomerCardBin> result = new BaseRes<CustomerCardBin>(true);
		String customerId = req.getQjsCustomerId();
		if (StringUtils.isNumeric(customerId)) {
			CustomerCardBin detailInfo = infoService.getPerCard(Long.valueOf(customerId));
			if (detailInfo == null) {
				result.setCode(AppCodeEnum._A101_USER_NOT_BIND.getCode());// 没有绑卡
				result.setMessage("会员【" + customerId + "】没有绑卡");
			} else {
				result.setData(detailInfo);
			}
		} else {
			result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
			result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
		}
		return result;
	}
	@ReadOnlyConnection
	@ApiOperation(value = "会员基本信息查询", httpMethod = "POST", produces = "application/json")
	@ApiResponse(code = 200, message = "success", response = BaseRes.class)
	@ResponseBody
	@RequestMapping(value = "/perDetailInfo", method = RequestMethod.POST, produces = "application/json")
	@Override
	public BaseRes<CustomerDetail> perDetailInfo(@RequestBody ThirdCustReq req) {
		logger.info("clientNo=" + req.getClientNo() + ",clientSeqNo=" + req.getClientSeqNo() + ",qjsCustomerId="
				+ req.getQjsCustomerId());
		BaseRes<CustomerDetail> result = new BaseRes<CustomerDetail>(true);
		String customerId = req.getQjsCustomerId();
		if (StringUtils.isNumeric(customerId)) {
			CustomerDetail detailInfo = infoService.getPerDetail(Long.valueOf(customerId));
			if (detailInfo == null) {
				result.setCode(AppCodeEnum._A001_USER_NOT_EXISTS.getCode());
				result.setMessage("会员【" + customerId + "】不存在");
			} else {
				result.setData(detailInfo);
			}
		} else {
			result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
			result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
		}
		return result;

	}
	@ReadOnlyConnection
	@ApiOperation(value = "会员账户信息查询", httpMethod = "POST", produces = "application/json")
	@ApiResponse(code = 200, message = "success", response = BaseRes.class)
	@ResponseBody
	@RequestMapping(value = "/perAccountDetail", method = RequestMethod.POST, produces = "application/json")
	@Override
	public BaseRes<CustomerAcountDetail> queryCustAccount(@RequestBody ThirdCustReq req) {
		logger.info("clientNo=" + req.getClientNo() + ",clientSeqNo=" + req.getClientSeqNo() + ",qjsCustomerId="
				+ req.getQjsCustomerId());
		BaseRes<CustomerAcountDetail> result=new BaseRes<CustomerAcountDetail>(true);
		String customerId = req.getQjsCustomerId();
    	if(customerId==null){
    		result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
    		result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());	
    		return result;
    	}
    	if(StringUtils.isNumeric(customerId)){
    		Long cusid=Long.parseLong(customerId);
    		try{
    			CustomerAcountDetail cb= balanceService.queryCustomerAcountDetail(cusid);
	    		result.setData(cb);
    		}catch(AppException e){
    			result.setCode(e.getCode());
    			result.setMessage(e.getMessage());
    		}
    	}else{
    		result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
    		result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
    	}
		return result;
	}

}
