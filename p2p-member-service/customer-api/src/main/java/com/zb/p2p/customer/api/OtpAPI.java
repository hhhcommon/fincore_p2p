package com.zb.p2p.customer.api;

import com.zb.p2p.customer.common.model.BaseRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.zb.p2p.customer.api.entity.CheckSmsCaptchaReq;
import com.zb.p2p.customer.api.entity.GenImgCaptchaReq;
import com.zb.p2p.customer.api.entity.GenImgCaptchaRes;
import com.zb.p2p.customer.api.entity.SendSmsCaptchaReq;

/**
 * 一次性口令
 * @author liujia
 *
 */
public interface OtpAPI {
	
	/**
	 * step-1:生成图片验证码
	 * @param req
	 * @param customerId
	 * @return
	 */
    @PostMapping(value="/m1/s1GenImgCaptcha")
    public BaseRes<GenImgCaptchaRes> s1GenImgCaptcha(@RequestBody GenImgCaptchaReq req, @RequestHeader("customerId") Long customerId);
	
	/**
	 * step-2:展示图片验证码（s1接口中直接返回完整url)
	 * @param captchaCode
	 * @return
	 */
    @GetMapping(value="/m1/s2ShowImgCaptcha/{captchaCode}")
    public String s2ShowImgCaptcha(@PathVariable String captchaCode);
	
	/**
	 * step-3:发送短信校验码
	 * @param req
	 * @param customerId
	 * @return
	 */
    @PostMapping(value="/m1/s3SendSmsCaptcha")
    public BaseRes<Object> s3SendSmsCaptcha(@RequestBody SendSmsCaptchaReq req,@RequestHeader("customerId") Long customerId);
	
	/**
	 * step-4:验证短信校验码（不开放给H5）
	 * @param req
	 * @param customerId
	 * @return
	 */
    @PostMapping(value="/m1/s4CheckSmsCaptcha")
    public BaseRes<Object> s4CheckSmsCaptcha(@RequestBody CheckSmsCaptchaReq req,@RequestHeader(value="customerId") Long customerId);
	
}
