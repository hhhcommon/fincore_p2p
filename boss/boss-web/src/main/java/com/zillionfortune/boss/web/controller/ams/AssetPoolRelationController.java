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
import com.zb.fincore.ams.facade.dto.req.CreateAssetPoolRelationRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolAssetListRequest;
import com.zillionfortune.boss.biz.ams.AssetPoolRelationBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolRelationAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetPoolRelationQueryVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 资产资产池关联关系Controller
 * 
 * @author litaiping
 *
 */
@Controller
@RequestMapping(value = "/assetpoolrelationservice")
public class AssetPoolRelationController {
    
    private final Logger log = LoggerFactory.getLogger(AssetPoolRelationController.class);
    
    @Autowired
    private AssetPoolRelationBiz assetPoolRelationBiz;
    
	@Autowired
	private HttpSessionUtils httpSessionUtils;
    /**
     * 新增资产池
     * @param req
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse add(@RequestBody AssetPoolRelationAddVO vo) {
    	
    	log.info("AssetPoolRelationController.add.req:" + JSON.toJSONString(vo));
		
    	CreateAssetPoolRelationRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null||vo.getAssetCodeList()==null||vo.getAssetCodeList().size()==0){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new CreateAssetPoolRelationRequest();
			PropertyUtils.copyProperties(req, vo);
			try {
				req.setCreateBy(httpSessionUtils.getCuruser().getName());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常",e);
			}
			
			if(vo.getCreateBy()==null){
				req.setCreateBy("1");
			}
			
			//3.===调用新增方法
			resp = assetPoolRelationBiz.createAssetPoolRelation(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetPoolRelationController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
    
    /**
     * 查询资产资产池详情
     * @param req
     * @return
     */
    @RequestMapping(value = "/querylist", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryList(@RequestBody AssetPoolRelationQueryVO vo) {
    	
    	log.info("AssetPoolRelationController.querylist.req:" + JSON.toJSONString(vo));
		
    	QueryPoolAssetListRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null||vo.getPoolCode()==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryPoolAssetListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = assetPoolRelationBiz.queryPoolAssetList(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetPoolRelationController.querylist.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
   
}
