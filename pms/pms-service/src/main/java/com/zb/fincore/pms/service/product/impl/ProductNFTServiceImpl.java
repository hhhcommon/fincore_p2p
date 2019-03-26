package com.zb.fincore.pms.service.product.impl;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.GlobalConfigConstants;
import com.zb.fincore.pms.common.enums.RegisterTypeEnum;
import com.zb.fincore.pms.common.utils.DataFormatUtil;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestNFT;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.dal.dao.GlobalConfigDao;
import com.zb.fincore.pms.service.product.ProductNFTService;

/**
 * 功能: N日复投计划产品接口类
 * 日期: 2017/4/6 0006 16:57
 * 版本: V1.0
 */
@Service
public class ProductNFTServiceImpl extends AbstractProductServiceImpl implements ProductNFTService {

    private static Logger logger = LoggerFactory.getLogger(ProductNFTServiceImpl.class);
    
    @Autowired
    private SequenceService sequenceService;
    
    @Autowired
    private GlobalConfigDao globalConfigDao;

    /**
     * 货架系统 产品注册
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public RegisterProductResponse registerProductNFT(@Valid RegisterProductRequestNFT req) throws Exception {
    	
    	RegisterProductRequest regReq = BeanUtils.copyAs(req, RegisterProductRequest.class);
    	
    	//产品名称组装
        String productName = new StringBuffer()
		.append(req.getRegisterType().equals(RegisterTypeEnum.UNAUTO.getCode())?req.getProductDisplayName():req.getProductDisplayName().split("-")[0])
		.append("-")
		.append(sequenceService.generateProductNameCode(Constants.SEQUENCE_NAME_PREFIX_PRODUCT_NAME,req.getPatternCode()))
		.toString();
        regReq.setProductName(productName);
        regReq.setProductDisplayName(productName);
        
    	if (req.getRegisterType().equals(RegisterTypeEnum.UNAUTO.getCode()) ) {
    		String nPlanProductCloseTime = globalConfigDao.selectByPropertyName(GlobalConfigConstants.N_PLAN_PRODUCT_CLOSE_TIME).getPropertyValue();
            nPlanProductCloseTime = (StringUtils.isBlank(nPlanProductCloseTime)?"23:00:00":nPlanProductCloseTime).replace(" ", "");
            
    		String yyyymmdd = DataFormatUtil.getDateFormat(new Date());
        	regReq.setSaleStartTime(DataFormatUtil.formatDate(yyyymmdd, DataFormatUtil.DATE_DEFAULT_FORMAT));
        	regReq.setSaleEndTime(DataFormatUtil.formatDate(yyyymmdd + " " + nPlanProductCloseTime, DataFormatUtil.DATETIME_DEFAULT_FORMAT));
    	} 
    	String currentDayAfter = DataFormatUtil.getCurrentDayAfter() + " 00:00:00";
    	regReq.setValueTime(DataFormatUtil.formatDate(currentDayAfter, "yyyy-MM-dd HH:mm:ss"));	
    	
    	
    	return super.registerProduct(regReq);
    	
    }
}
