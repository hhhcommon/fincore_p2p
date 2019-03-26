package com.zb.p2p.customer.web.controller;

import com.google.code.kaptcha.Producer;
import com.zb.p2p.customer.api.entity.CheckSmsCaptchaReq;
import com.zb.p2p.customer.api.entity.GenImgCaptchaReq;
import com.zb.p2p.customer.api.entity.GenImgCaptchaRes;
import com.zb.p2p.customer.api.entity.SendSmsCaptchaReq;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.dao.domain.CustomerBindcard;
import com.zb.p2p.customer.service.CardService;
import com.zb.p2p.customer.service.InfoService;
import com.zb.p2p.customer.service.OtpService;
import com.zb.p2p.customer.service.bo.CheckSmsCaptchaBO;
import com.zb.p2p.customer.service.bo.GenImgCaptchaBO;
import com.zb.p2p.customer.service.bo.ImgCaptchaBO;
import com.zb.p2p.customer.service.bo.SendSmsCaptchaBO;
import com.zb.p2p.customer.validator.CheckSmsCaptchaReqValidator;
import com.zb.p2p.customer.validator.GenImgCaptchaReqValidator;
import com.zb.p2p.customer.validator.SendSmsCaptchaReqValidator;
import com.zb.p2p.customer.web.config.JsonResponse;
import com.zb.p2p.customer.web.convert.OtpConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * one time password Controller
 * @author liujia
 *
 */
@Api(value="一次性口令",description="一次性口令")
@Slf4j
@RequestMapping(value="/otp", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@RestController
public class OtpController {
	
	@Autowired
	private OtpService otpService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private CardService cardService;
	
	
	@Resource(name="imgCaptchaProducer")
    private Producer imgCaptchaProducer;
	@Autowired
	private GenImgCaptchaReqValidator genImgCaptchaReqValidator; 
	@Autowired
	private SendSmsCaptchaReqValidator sendSmsCaptchaReqValidator;
	@Autowired
	private CheckSmsCaptchaReqValidator checkSmsCaptchaReqValidator;
	
	@ApiOperation(value = "step-1:生成图片验证码")
	@JsonResponse
	@ResponseBody
    @PostMapping(value="/m1/s1GenImgCaptcha", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public BaseRes<GenImgCaptchaRes> s1GenImgCaptcha(@RequestBody GenImgCaptchaReq req, @RequestHeader("customerId") Long customerId){
    		log.info("s1GenImgCaptcha start. user:{}  ",customerId);
    		this.genImgCaptchaReqValidator.validate(req);

    		BaseRes<GenImgCaptchaRes> res = new BaseRes<>(false);
		//AppContext.getInstance().setCustomerId(customerId);
		GenImgCaptchaBO obj = OtpConvert.genImgCaptchaReq2GenImgCaptchaBO(req); 
		ImgCaptchaBO imgCaptchaBO = this.otpService.genImgCaptcha(obj);
		GenImgCaptchaRes genImgCaptchaRes = OtpConvert.imgCaptchaBO2GenImgCaptchaRes(imgCaptchaBO);
		res.setData(genImgCaptchaRes);
		res.success();

    		return res;
    }
	
	@ApiOperation(value = "step-2:展示图片验证码（s1接口中直接返回完整url）")
    @GetMapping(value="/m1/s2ShowImgCaptcha/{imgCaptchaCode}", produces = "image/jpeg", consumes= {})
    public String s2ShowImgCaptcha(@PathVariable String imgCaptchaCode,HttpServletResponse response) throws IOException{
    		log.info(" showImgCaptcha >> code:{}  ",imgCaptchaCode);
    		ServletOutputStream out = null;
    		try {

    			String imgCaptchaVal = this.otpService.findImgCaptcha(imgCaptchaCode);
        		log.info(" showImgCaptcha >> val:{}  ",imgCaptchaVal);

	    		BufferedImage bi = imgCaptchaProducer.createImage(imgCaptchaVal);
	        out = response.getOutputStream();
	        ImageIO.write(bi, "jpg", out);
	        
            out.flush();
        } finally {
			if(out != null) {
				out.close();
			}
        }
    		return null;
    }
	
	@JsonResponse
	@ResponseBody
	@ApiOperation(value = "step-3:发送短信校验码")
    @PostMapping(value="/m1/s3SendSmsCaptcha", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public BaseRes<Object> s3SendSmsCaptcha(@RequestBody SendSmsCaptchaReq req,@RequestHeader(name = "customerId",required = false) Long customerId){
    		log.info("s3SendSmsCaptcha start. user:{}  ",customerId);

		CustomerBindcard customerBindcard = new CustomerBindcard();
		if(StringUtils.isEmpty(req.getMobile() )){
			if(StringUtils.isNotEmpty(req.getCustomerId())){
				customerBindcard = cardService.queryCardByCustomerId(Long.parseLong(req.getCustomerId() ));
				req.setMobile( customerBindcard.getMobile());
			}else if(null != customerId){
				customerBindcard = cardService.queryCardByCustomerId(customerId);
				req.setMobile( customerBindcard.getMobile());
			}else{
				return new BaseRes(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(),"用户ID为空");
			}
		} else {
			//解绑卡时发送短信验证码手机号为绑卡手机号
			if ("05".equals(req.getOtpBusiCode())){
				if(StringUtils.isNotEmpty(req.getCustomerId())){
					customerBindcard = cardService.queryCardByCustomerId(Long.parseLong(req.getCustomerId() ));
				}else if(null != customerId){
					customerBindcard = cardService.queryCardByCustomerId(customerId);
				}else{
					return new BaseRes(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(),"用户ID为空");
				}
				if (customerBindcard == null){
					return new BaseRes<>( AppCodeEnum._A101_USER_NOT_BIND);
				} else {
					if (!req.getMobile().equals(customerBindcard.getMobile())) {
						return new BaseRes<>( AppCodeEnum._A127_UNBIND_MOBILE_ERROR);
					}
				}
			}
		}

    		this.sendSmsCaptchaReqValidator.validate(req);
    		BaseRes<Object> res = new BaseRes<>(false);
    		SendSmsCaptchaBO obj = OtpConvert.sendSmsCaptchaReq2sendSmsCaptchaBO(req);
    		this.otpService.sendSmsCaptcha(obj);
    		res.success();
    		return res;
    }
	
	@JsonResponse
	@ResponseBody
	@ApiOperation(value = "step-4:验证短信校验码（不开放给H5）")
    @PostMapping(value="/m1/s4CheckSmsCaptcha", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public BaseRes<Object> s4CheckSmsCaptcha(@RequestBody CheckSmsCaptchaReq req,@RequestHeader(value="customerId") Long customerId){
    		log.info("s4CheckSmsCaptcha start. user:{}  ",customerId);
    		this.checkSmsCaptchaReqValidator.validate(req);
    		BaseRes<Object> res = new BaseRes<>(false);
    		CheckSmsCaptchaBO obj = OtpConvert.checkSmsCaptchaReq2CheckSmsCaptchaBO(req);
    		this.otpService.checkSmsCaptcha(obj);
    		res.success();
    		return res;
    }
	
}
