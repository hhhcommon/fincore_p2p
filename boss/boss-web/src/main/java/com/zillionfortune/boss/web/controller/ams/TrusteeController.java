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
import com.zb.fincore.ams.facade.dto.req.CreateTrusteeRequest;
import com.zb.fincore.ams.facade.dto.req.QueryTrusteeListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryTrusteeRequest;
import com.zillionfortune.boss.biz.ams.TrusteeBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.ams.vo.TrusteeAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.TrusteeQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.TrusteeQueryVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 受托方相关Controller
 * 
 * @author litaiping
 *
 */
@Controller
@RequestMapping(value = "/trusteeservice")
public class TrusteeController {
    
    private final Logger log = LoggerFactory.getLogger(TrusteeController.class);
    
    @Autowired
    private TrusteeBiz trusteeBiz;
    
	@Autowired
	private HttpSessionUtils httpSessionUtils;
    /**
     * 新增受托方
     * @param req
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse add(@RequestBody TrusteeAddVO vo) {
    	
    	log.info("TrusteeController.add.req:" + JSON.toJSONString(vo));
		
    	CreateTrusteeRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new CreateTrusteeRequest();
			PropertyUtils.copyProperties(req, vo);
			try {
				req.setCreateBy(httpSessionUtils.getCuruser().getName());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常",e);
			}
			
			if(req.getCreateBy()==null){
				req.setCreateBy("1");
			}
			
			//3.===调用新增方法
			resp = trusteeBiz.createTrustee(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("TrusteeController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
   
    
    /**
     * 查询发行方列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/querylist", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryList(@RequestBody TrusteeQueryListVO vo) {
    	
    	log.info("TrusteeController.querylist.req:" + JSON.toJSONString(vo));
		
    	QueryTrusteeListRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryTrusteeListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = trusteeBiz.queryTrusteeList(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("TrusteeController.querylist.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    /**
     * 查询发行方详情
     * @param req
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse query(@RequestBody TrusteeQueryVO vo) {
    	
    	log.info("TrusteeController.query.req:" + JSON.toJSONString(vo));
		
    	QueryTrusteeRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryTrusteeRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = trusteeBiz.queryTrustee(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("TrusteeController.query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
}
