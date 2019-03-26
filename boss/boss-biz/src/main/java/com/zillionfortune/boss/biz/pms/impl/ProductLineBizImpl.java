package com.zillionfortune.boss.biz.pms.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zb.fincore.common.utils.AesHttpClientUtils;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.line.ProductLineServiceFacade;
import com.zb.fincore.pms.facade.line.dto.req.QueryProductLineListRequest;
import com.zb.fincore.pms.facade.line.model.ProductLineModel;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zillionfortune.boss.biz.pms.ProductLineBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.ProductTypeEnum;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;

/**
 * ClassName: ProductLineBizImpl <br/>
 * Function: 产品线服务接口实现. <br/>
 * Date: 2017年5月31日 上午11:05:02 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class ProductLineBizImpl implements ProductLineBiz {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ProductLineServiceFacade productLineServiceFacade;
	
	
	@Value("${productline_list_query_url}")
    private String productLineListQueryUrl;
	
	@Value("${internal_productline_list_query_url}")
	private String internalproductLineListQueryUrl;
	
	@Autowired
    protected AesHttpClientUtils aesHttpClientUtils;
	
	/**
	 * 产品线列表查询.
	 * @see com.zillionfortune.boss.biz.pms.ProductLineBiz#queryProductLineList(com.zb.fincore.pms.facade.line.dto.req.QueryProductLineListRequest)
	 */
	@Override
	public BaseWebResponse queryProductLineList(QueryProductLineListRequest req,String productType) {
		log.info("ProductBizImpl.queryProductLineList.req:");
		BaseWebResponse resp = null;
		try {
			// 调用产品线列表查询服务接口
			//PageQueryResponse<ProductLineModel>  queryProductLineListResponse= productLineServiceFacade.queryProductLineList(req);
			PageQueryResponse<ProductLineModel>  queryProductLineListResponse= productLineListQueryUseHttp(req,productType);
			
			if (ResultCode.SUCCESS.code().equals(queryProductLineListResponse.getRespCode())) {
				resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
				
				Map<String,Object> respMap = new HashMap<String,Object>();
				respMap.put("dataList", queryProductLineListResponse.getDataList());
				resp.setData(respMap);
			} else {
				resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),queryProductLineListResponse.getRespMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		}
		
		log.info("ProductBizImpl.queryProductLineList.resp:" + JSON.toJSONString(resp));
		return resp;
	}
	
	/**
     * 产品线列表查询调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public PageQueryResponse<ProductLineModel> productLineListQueryUseHttp(QueryProductLineListRequest req,String productType) throws Exception {
        PageQueryResponse<ProductLineModel> productLineModelPageQueryResponse = null;
        String respContent = null;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	productLineListQueryUrl=internalproductLineListQueryUrl;
        }
        // 调用远程服务
        log.info("产品线列表查询 调用pms请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(productLineListQueryUrl, JSONObject.toJSONString(req));
        log.info("产品线列表查询 调用pms响应参数：：" + respContent);

        if (StringUtils.isNotBlank(respContent)) {
            // 将json字符创转换成json对象
        	productLineModelPageQueryResponse = JSONObject.parseObject(respContent, new TypeReference<PageQueryResponse<ProductLineModel>>() {
            });
        }
        return productLineModelPageQueryResponse;
    }
	
}
