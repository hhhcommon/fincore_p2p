package com.zillionfortune.boss.web.controller.ams;

import java.util.List;

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
import com.zb.fincore.ams.facade.dto.req.QueryContractSignListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryContractSignRequest;
import com.zb.fincore.ams.facade.dto.req.UpdateContractSignStatusRequest;
import com.zillionfortune.boss.biz.ams.SignBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.service.role.RoleService;
import com.zillionfortune.boss.web.controller.ams.vo.QueryContractSignListRequestVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryContractSignRequestVO;
import com.zillionfortune.boss.web.controller.ams.vo.UpdateContractSignStatusRequestVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 签章相关Controller
 * 
 * @author litaiping
 * 
 */
@Controller
@RequestMapping(value = "/signservice")
public class SignController {

	private final Logger log = LoggerFactory.getLogger(SignController.class);

	@Autowired
	private SignBiz signBiz;

	@Autowired
	private HttpSessionUtils httpSessionUtils;

	@Autowired
	private final static String SIGN_ROLE = "SIGN_ROLE";

	@Autowired
	private RoleService roleService;

	/**
	 * 查询盖章管理列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querycontractsignlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryContractSignList(@RequestBody QueryContractSignListRequestVO vo) {
		log.info("SignController.queryContractSignList.req:" + JSON.toJSONString(vo));
		QueryContractSignListRequest req = null;
		BaseWebResponse resp = null;
		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}
			req = new QueryContractSignListRequest();
			PropertyUtils.copyProperties(req, vo);
			List<Role> roleList = roleService.selectByUserId(httpSessionUtils.getCuruser().getId());
			boolean hasRole=false;
			if (roleList != null && roleList.size() > 0) {
				for (int i = 0; i < roleList.size(); i++) {
					Role role = roleList.get(i);
					if (SIGN_ROLE.equals(role.getSignLevel())) {
						req.setRoleId(String.valueOf(role.getId()));
						hasRole=true;
						break;
					}
				}
			}
			if(!hasRole){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), "该用户没盖章权限");
				return resp;
			}
			resp = signBiz.queryContractSignList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("SignController.queryContractSignList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

	/**
	 * 查看盖章详情
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querycontractsigndetail", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryContractSignDetail(@RequestBody QueryContractSignRequestVO vo) {
		log.info("SignController.queryContractSignDetail.req:" + JSON.toJSONString(vo));
		QueryContractSignRequest req = null;
		BaseWebResponse resp = null;
		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}
			req = new QueryContractSignRequest();
			PropertyUtils.copyProperties(req, vo);

			resp = signBiz.queryContractSignDetail(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("SignController.queryContractSignDetail.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

	/**
	 * 盖章
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/signcontract", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse signContract(@RequestBody UpdateContractSignStatusRequestVO vo) {
		log.info("SignController.signContract.req:" + JSON.toJSONString(vo));
		UpdateContractSignStatusRequest req = null;
		BaseWebResponse resp = null;
		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}
			req = new UpdateContractSignStatusRequest();
			PropertyUtils.copyProperties(req, vo);

			resp = signBiz.signContract(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("SignController.signContract.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

	/**
	 * 去章
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cancelsigncontract", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse cancelSignContract(@RequestBody UpdateContractSignStatusRequestVO vo) {
		log.info("SignController.cancelSignContract.req:" + JSON.toJSONString(vo));
		UpdateContractSignStatusRequest req = null;
		BaseWebResponse resp = null;
		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}
			req = new UpdateContractSignStatusRequest();
			PropertyUtils.copyProperties(req, vo);

			resp = signBiz.cancelSignContract(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("SignController.cancelSignContract.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
}
