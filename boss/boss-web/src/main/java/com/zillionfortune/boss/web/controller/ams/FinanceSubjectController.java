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
import com.zb.fincore.ams.facade.dto.req.CreateFinanceSubjectRequest;
import com.zb.fincore.ams.facade.dto.req.QueryFinanceSubjectListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryFinanceSubjectRequest;
import com.zillionfortune.boss.biz.ams.FinanceSubjectBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.ams.vo.FinanceSubjectAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.FinanceSubjectQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.FinanceSubjectQueryVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 发行方相关Controller
 * 
 * @author litaiping
 *
 */
@Controller
@RequestMapping(value = "/financesubjectservice")
public class FinanceSubjectController {
    
    private final Logger log = LoggerFactory.getLogger(FinanceSubjectController.class);
    
    @Autowired
    private FinanceSubjectBiz financeSubjectBiz;
    
	@Autowired
	private HttpSessionUtils httpSessionUtils;
    /**
     * 新增发行方
     * @param req
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse add(@RequestBody FinanceSubjectAddVO vo) {
    	
    	log.info("FinanceSubjectController.add.req:" + JSON.toJSONString(vo));
		
    	CreateFinanceSubjectRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new CreateFinanceSubjectRequest();
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
			resp = financeSubjectBiz.createFinanceSubject(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("FinanceSubjectController.add.resp:" + JSON.toJSONString(resp));
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
    public BaseWebResponse queryList(@RequestBody FinanceSubjectQueryListVO vo) {
    	
    	log.info("FinanceSubjectController.querylist.req:" + JSON.toJSONString(vo));
		
    	QueryFinanceSubjectListRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryFinanceSubjectListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = financeSubjectBiz.queryFinanceSubjectList(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("FinanceSubjectController.querylist.resp:" + JSON.toJSONString(resp));
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
    public BaseWebResponse query(@RequestBody FinanceSubjectQueryVO vo) {
    	
    	log.info("AssetPoolController.query.req:" + JSON.toJSONString(vo));
		
    	QueryFinanceSubjectRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryFinanceSubjectRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = financeSubjectBiz.queryFinanceSubject(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("FinanceSubjectController.query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
}
