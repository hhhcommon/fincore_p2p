package com.zb.p2p.customer.web.controller;

import com.zb.p2p.customer.common.model.BaseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.p2p.customer.api.UrlDispenserAPI;
import com.zb.p2p.customer.api.entity.AuthregisterReq;
import com.zb.p2p.customer.api.entity.AuthregisterRes;
import com.zb.p2p.customer.api.entity.CheckCodeRequest;
import com.zb.p2p.customer.api.entity.CheckCodeRes;
import com.zb.p2p.customer.api.entity.URLDispenserRequest;
import com.zb.p2p.customer.api.entity.URLDispenserResp;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.service.URLDispenserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;



@Api(value = "对外接口",description="分发页面")
@Controller
@RequestMapping("/Dispenser")
public class DispenserController implements UrlDispenserAPI{
 
	@Autowired
	URLDispenserService uRLDispenserService;


	@ApiOperation(value = "页面分发", httpMethod = "POST", produces = "application/json")
	@ApiResponse(code = 0000, message = "success", response = BaseRes.class)
	@ResponseBody
	@RequestMapping(value = "/urlDispenser")
	@Override
	public BaseRes<URLDispenserResp> urlDispenser(@RequestBody URLDispenserRequest req) {
	
		BaseRes<URLDispenserResp> result=new BaseRes<URLDispenserResp>();
		if(req==null||req.getTxsAccountId()==null||"".equals(req.getTxsAccountId())
				||req.getSourcePage()==null||"".equals(req.getSourcePage())
				/*||req.getClientSeqNo()==null||"".equals(req.getClientSeqNo())
				||req.getClientSeqNo()==null||"".equals(req.getClientSeqNo())*/
				){
			result.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
			result.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
			return result;
		}
		
		URLDispenserResp resp=uRLDispenserService.urlDispenser(req);
		 
		if(resp!=null){
			result.setData(resp);
			result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
			result.setMessage(AppCodeEnum._0000_SUCCESS.getMessage());
		}else{
			result.setData(resp);
			result.setCode(AppCodeEnum._9999_ERROR.getCode());
			result.setMessage(AppCodeEnum._9999_ERROR.getMessage());
		}
	
		return result;
	}

	@ApiOperation(value = "校验code",  httpMethod = "POST", produces = "application/json")
	@ApiResponse(code = 0000, message = "success", response = CheckCodeRes.class)
	@ResponseBody
	@RequestMapping(value = "/checkCode")
	@Override
	public BaseRes<CheckCodeRes> checkCode(@RequestBody CheckCodeRequest req) {
	
		if(req.getCode()==null){
			BaseRes<CheckCodeRes> re=new BaseRes<CheckCodeRes>();
			re.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
			re.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
			return re;
		}
		BaseRes<CheckCodeRes> result=uRLDispenserService.checkCode(req);
	 
		return result;
	}
	
	@ApiOperation(value = "授权注册", httpMethod = "POST",  produces = "application/json")
	@ApiResponse(code = 0000, message = "success", response = AuthregisterRes.class)
	@ResponseBody
	@RequestMapping(value = "/authregister")
	@Override
	public BaseRes<AuthregisterRes> authregister(@RequestBody AuthregisterReq req) {
		// TODO Auto-generated method stub
		if(req.getToken()==null){
			BaseRes<AuthregisterRes> re=new BaseRes<AuthregisterRes>();
			re.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
			re.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
			return re;
		}
		
		AuthregisterRes res=uRLDispenserService.authregister(req);
		
		BaseRes<AuthregisterRes> result=new BaseRes<AuthregisterRes>();
		if(res!=null){
			result.setData(res);
			result.setCode(AppCodeEnum._0000_SUCCESS.getCode());
			result.setMessage(AppCodeEnum._0000_SUCCESS.getMessage());
		}else{
			result.setCode(AppCodeEnum._A009_REFUSE.getCode());
			result.setMessage(AppCodeEnum._A009_REFUSE.getMessage());
		}
		
		return result;
	}

	/*@ApiOperation(value = "激活", httpMethod = "POST", produces = "application/json")
	@ApiResponse(code = 0000, message = "success", response = BindRes.class)
	@ResponseBody
	@RequestMapping(value = "/bind")
	@Override
	public BaseRes<BindRes> bind(@RequestBody BindReq req) {
		// TODO Auto-generated method stub
		
		if(req.getSmsCode()==null||req.getToken()==null||"".equals(req.getSmsCode())||"".equals(req.getToken())){
			BaseRes<BindRes> re=new BaseRes<BindRes>();
			re.setCode(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode());
			re.setMessage(AppCodeEnum._0001_ILLEGAL_PARAMETER.getMessage());
			return re;
		}
		
		BaseRes<BindRes> result=uRLDispenserService.bind(req);
 
		
    	if(result==null){
    		result = new BaseRes<BindRes>();
    		result.setCode(AppCodeEnum._A009_REFUSE.getCode());
			result.setMessage(AppCodeEnum._A009_REFUSE.getMessage());
		    return result;
    	}else{
    		return result;
    	}
	}*/
	
	
	/*@ApiOperation(value = "发送验证码", httpMethod = "POST", produces = "application/json")
	@ApiResponse(code = 0000, message = "success", response = BindSMSSendResp.class)
	@ResponseBody
	@RequestMapping(value = "/sendSms")
	@Override
	public BaseRes<BindSMSSendResp> sendSms(@RequestBody BindSmsReq req) {
		// TODO Auto-generated method stub
		BaseRes<BindSMSSendResp> response=new BaseRes<BindSMSSendResp>();
		BindSMSSendResp res=	uRLDispenserService.senSms(req);
		if(res==null){
			response.setCode(AppCodeEnum._A008_CHECK_CODE.getCode());
			response.setMessage(AppCodeEnum._A008_CHECK_CODE.getMessage());
			return response;
		}else{
			response.setData(res);
			response.setCode(AppCodeEnum._0000_SUCCESS.getCode());
			response.setMessage(AppCodeEnum._0000_SUCCESS.getMessage());
			return response;
		}
		
	}*/

}
