package com.zillionfortune.boss.web.controller.ams;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.facade.dto.req.ApproveAssetRequest;
import com.zb.fincore.ams.facade.dto.req.QueryAssetApprovalListRequest;
import com.zillionfortune.boss.biz.ams.AssetApprovalBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.ams.vo.AssetApprovaVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryAssetApprovalListVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 资产审核Controller
 * 
 * @author litaiping
 *
 */
@Controller
@RequestMapping(value = "/assetapprovalservice")
public class AssetApprovalController {
    
    private final Logger log = LoggerFactory.getLogger(AssetApprovalController.class);
    
    @Autowired
    private AssetApprovalBiz assetApprovalBiz;
    
	@Autowired
	private HttpSessionUtils httpSessionUtils;
    /**
     * 资产审核
     * @param req
     * @return
     */
    @RequestMapping(value = "/approveasset", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse approveAsset(@RequestBody AssetApprovaVO vo) {
    	
    	log.info("AssetApprovalController.approveAsset.req:" + JSON.toJSONString(vo));
		
    	ApproveAssetRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new ApproveAssetRequest();
			PropertyUtils.copyProperties(req, vo);
			try {
				req.setApprovalBy(httpSessionUtils.getCuruser().getName());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常",e);
			}
			
			if(req.getApprovalBy()==null){
				req.setApprovalBy("1");
			}
			
			//3.===调用新增方法
			resp = assetApprovalBiz.approveAsset(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetApprovalController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
  
    /**
     * 查询资产审核记录列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryassetapprovallist", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryAssetApprovalList(@RequestBody QueryAssetApprovalListVO vo) {
    	
    	log.info("AssetApprovalController.querylist.req:" + JSON.toJSONString(vo));
		
    	QueryAssetApprovalListRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryAssetApprovalListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = assetApprovalBiz.queryAssetApprovalList(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetApprovalController.querylist.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
}
