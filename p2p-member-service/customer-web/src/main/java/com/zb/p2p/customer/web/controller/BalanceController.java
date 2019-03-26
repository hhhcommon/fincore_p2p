/**
 * 
 */
package com.zb.p2p.customer.web.controller;

import javax.annotation.Resource;

import com.zb.p2p.customer.common.model.BaseRes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zb.p2p.customer.api.BalanceAPI;
import com.zb.p2p.customer.api.entity.CustomerIdReq;
import com.zb.p2p.customer.api.entity.currency.CustomerAcountDetail;
import com.zb.p2p.customer.api.entity.currency.CustomerEaccountBalance;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.StringUtils;
import com.zb.p2p.customer.service.BalanceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author guolitao
 *
 */
@Slf4j
@Api(value = "会员余额控制器",description="会员余额")
@RequestMapping("/balance")
@RestController
public class BalanceController implements BalanceAPI {
	
	@Resource
	private BalanceService balanceService;
//	@ApiOperation(value = "个人会员活期余额查询", httpMethod = "POST", produces = "application/json")
//    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
//    @ResponseBody
//    @RequestMapping(value = "/queryCustomerBalance", method = RequestMethod.POST, produces = "application/json")
	/* (non-Javadoc)
	 * @see com.zb.p2p.customer.api.BalanceAPI#queryCustomerBalance(com.zb.p2p.customer.api.entity.CustomerIdReq, java.lang.String)
	 */
	@Override
	public BaseRes<CustomerEaccountBalance> queryCustomerBalance(@RequestBody(required = false) CustomerIdReq req, @RequestHeader(name="customerId",required=false)String custId) {
		BaseRes<CustomerEaccountBalance> result=new BaseRes<CustomerEaccountBalance>(true);
		String customerId = req != null ? req.getCustomerId() : "";
		if(custId != null){
			customerId = custId;
		}
		log.info("个人会员活期余额查询 >> customerId:{}",customerId);

    	if(customerId==null){
    		result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
    		result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());	
    		return result;
    	}
    	if(StringUtils.isNumeric(customerId)){
    		Long cusid=Long.parseLong(customerId);
    		try{
	    		CustomerEaccountBalance cb= balanceService.queryCustomerBalance(cusid);
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
	@ApiOperation(value = "个人会员账户查询（内部）", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/queryCustAccount", method = RequestMethod.POST, produces = "application/json")
	@Override
	public BaseRes<CustomerAcountDetail> queryCustAccount(@RequestBody(required=false) CustomerIdReq req, @RequestHeader(name="customerId",required=false)String custId) {
		BaseRes<CustomerAcountDetail> result=new BaseRes<CustomerAcountDetail>(true);
		String customerId = req != null ? req.getCustomerId() : "";
		if(custId != null){
			customerId = custId;
		}
		log.info("个人会员账户查询（内部） >> customerId:{}",customerId);

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
    		}catch(Exception e){
    			result.resetCode(AppCodeEnum._9999_ERROR);
    		}
    	}else{
    		result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
    		result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
    	}
		return result;
	}
	/*@ApiOperation(value = "查询活期开关", httpMethod = "POST", produces = "application/json",notes="0-关闭1-打开")
    @ApiResponse(code = 200, message = "success", response = BaseRes.class)
    @ResponseBody
    @RequestMapping(value = "/queryBalanceFlag", method = RequestMethod.POST, produces = "application/json")
	@Override
	public BaseRes<Integer> queryBalanceFlag() {
		BaseRes<Integer> result=new BaseRes<Integer>(true);
		Integer flag = balanceService.queryBalanceFlag();
		result.setData(flag);
		return result;
	}*/

}
