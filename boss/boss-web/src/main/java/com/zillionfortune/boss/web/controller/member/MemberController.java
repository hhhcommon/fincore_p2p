package com.zillionfortune.boss.web.controller.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.member.MemberBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.member.vo.QueryMemberInfoVO;

/**
 * 会员相关接口
 * 
 * @author fangyang
 * 
 */
@Controller
@RequestMapping(value = "/memberservice")
public class MemberController {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberBiz memberBiz;

	/**
	 * 查询用户信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/querymemberinfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryMemberInfo(@RequestBody QueryMemberInfoVO vo) {
		
		log.info("MemberController.queryMemberInfo.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = memberBiz.queryMemberInfo( vo.getMemberId(), vo.getMobile());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("MemberController.queryMemberInfo.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 查询用户绑卡信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/querymembercards", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryMemberCards(@RequestBody QueryMemberInfoVO vo) {
		
		log.info("MemberController.queryMemberCards.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = memberBiz.queryMemberCards( vo.getMemberId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("MemberController.queryMemberCards.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

}
