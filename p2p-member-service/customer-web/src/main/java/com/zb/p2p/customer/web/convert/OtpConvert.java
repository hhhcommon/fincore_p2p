package com.zb.p2p.customer.web.convert;

import com.zb.p2p.customer.api.entity.*;
import com.zb.p2p.customer.service.bo.*;

public class OtpConvert {
	
	public static GenImgCaptchaBO genImgCaptchaReq2GenImgCaptchaBO(GenImgCaptchaReq req) {
		GenImgCaptchaBO obj = new GenImgCaptchaBO();
		obj.setMobile(req.getMobile());
		obj.setOtpBusiCode(req.getOtpBusiCode());
		return obj;
	}
	
	public static GenImgCaptchaRes imgCaptchaBO2GenImgCaptchaRes(ImgCaptchaBO imgCaptchaBO) {
		GenImgCaptchaRes res = new GenImgCaptchaRes();
		res.setFreeImgToken(imgCaptchaBO.getFreeImgToken());
		res.setImgCaptchaCode(imgCaptchaBO.getImgCaptchaCode());
		res.setImgCaptchaUrl(imgCaptchaBO.getImgCaptchaUrl());
		return res;
	}
	
	public static SendSmsCaptchaBO sendSmsCaptchaReq2sendSmsCaptchaBO(SendSmsCaptchaReq req) {
		SendSmsCaptchaBO obj = new SendSmsCaptchaBO();
		obj.setFreeImgToken(req.getFreeImgToken());
		obj.setImgCaptchaCode(req.getImgCaptchaCode());
		obj.setImgCaptchaVal(req.getImgCaptchaVal());
		obj.setOtpBusiCode(req.getOtpBusiCode());
		obj.setMobile(req.getMobile());
		return obj;
	}
	
	public static CheckSmsCaptchaBO checkSmsCaptchaReq2CheckSmsCaptchaBO(CheckSmsCaptchaReq req) {
		CheckSmsCaptchaBO obj = new CheckSmsCaptchaBO();
		obj.setMobile(req.getMobile());
		obj.setOtpBusiCode(req.getOtpBusiCode());
		obj.setSmsCaptchaVal(req.getSmsCaptchaVal());
		return obj;
	}

}
