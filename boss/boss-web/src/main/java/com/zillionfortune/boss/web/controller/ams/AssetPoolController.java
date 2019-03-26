package com.zillionfortune.boss.web.controller.ams;

import com.zb.fincore.ams.facade.dto.req.QueryPoolLeftAssetRequest;
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
import com.zb.fincore.ams.facade.dto.req.CreatePoolRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zillionfortune.boss.biz.ams.AssetPoolBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolQueryVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 资产池相关Controller
 * 
 * @author litaiping
 *
 */
@Controller
@RequestMapping(value = "/assetpoolservice")
public class AssetPoolController {
    
    private final Logger log = LoggerFactory.getLogger(AssetPoolController.class);
    
    @Autowired
    private AssetPoolBiz assetPoolBiz;
    
	@Autowired
	private HttpSessionUtils httpSessionUtils;
    /**
     * 新增资产池
     * @param req
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse add(@RequestBody AssetPoolAddVO vo) {
    	
    	log.info("AssetPoolController.add.req:" + JSON.toJSONString(vo));
		
    	CreatePoolRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null||vo.getFinanceSubjectCode()==null||vo.getLimitAmount()==null||vo.getPoolName()==null||vo.getTrusteeCode()==null||vo.getPoolType()==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new CreatePoolRequest();
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
			resp = assetPoolBiz.createAssetPool(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
    /**
     * 查询资产池列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/querylist", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryList(@RequestBody AssetPoolQueryListVO vo) {
    	
    	log.info("AssetPoolController.querylist.req:" + JSON.toJSONString(vo));
		
    	QueryPoolListRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryPoolListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = assetPoolBiz.queryAssetPoolList(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerController.querylist.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    /**
     * 查询资产池详情
     * @param req
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse query(@RequestBody AssetPoolQueryVO vo) {
    	
    	log.info("AssetPoolController.query.req:" + JSON.toJSONString(vo));
		
    	QueryPoolRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryPoolRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = assetPoolBiz.queryAssetPool(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerController.query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }

    /**
     * 产品注册-查询剩余资产列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryLeftAssetAmountList", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryLeftAssetAmountList(@RequestBody QueryPoolLeftAssetRequest req) {

        log.info("AssetPoolController.queryLeftAssetAmountList.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;

        try {

            //1.===参数校验
            if(req==null){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
                        ResultCode.ILLEGAL_PARAMETER.desc());
            }

            //2.===调用新增方法
            resp = assetPoolBiz.queryLeftAssetAmountList(req);

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            if (e instanceof BusinessException) {

                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());

            } else {

                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("PowerController.queryLeftAssetAmountList.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

}
