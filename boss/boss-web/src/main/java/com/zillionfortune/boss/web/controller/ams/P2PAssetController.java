package com.zillionfortune.boss.web.controller.ams;

import java.util.ArrayList;
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
import com.zb.fincore.ams.facade.dto.p2p.req.ApprovalLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.QueryAssetRepayPlanListRequest;
import com.zb.fincore.ams.facade.dto.req.CreateDebtRightInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryDebtRightInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryLoanInfoRequest;
import com.zb.fincore.ams.facade.dto.req.QueryLoanRepayListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryProductLoanRelationListRequest;
import com.zillionfortune.boss.biz.ams.P2PAssetBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.ams.vo.CreateDebtRightInfoRequestVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryDebtRighDetailVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryDebtRightInfoRequestVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryLoanOutTimeListRequestVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 资产相关Controller
 * 
 * @author litaiping
 * 
 */
@Controller
@RequestMapping(value = "/p2passetservice")
public class P2PAssetController {

	private final Logger log = LoggerFactory.getLogger(P2PAssetController.class);

	@Autowired
	private P2PAssetBiz p2PAssetBiz;

	@Autowired
	private HttpSessionUtils httpSessionUtils;

	/**
	 * 产品关联借款订单列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryproductloanrelationlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryProductLoanRelationList(@RequestBody QueryProductLoanRelationListRequestVO vo) {
		log.info("P2PAssetController.queryProductLoanRelationList.req:" + JSON.toJSONString(vo));
		QueryProductLoanRelationListRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryProductLoanRelationListRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2PAssetBiz.queryProductLoanRelationList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryProductLoanRelationList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 逾期的借款订单列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryloanouttimelist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryLoanOutTimeList(@RequestBody QueryLoanOutTimeListRequestVO vo) {
		log.info("P2PAssetController.queryLoanOutTimeList.req:" + JSON.toJSONString(vo));
		QueryLoanRepayListRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryLoanRepayListRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2PAssetBiz.queryLoanOutTimeList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryLoanOutTimeList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 债权建立
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/createdebtright", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	createDebtRight(@RequestBody CreateDebtRightInfoRequestVO vo) {
		log.info("P2PAssetController.createDebtRight.req:" + JSON.toJSONString(vo));
		List<CreateDebtRightInfoRequest> list = new ArrayList<CreateDebtRightInfoRequest>();
		BaseWebResponse resp = null;
		try {
			list=vo.getList();
			resp = p2PAssetBiz.createDebtRight(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.createDebtRight.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

	/**
	 * 债权列表查询
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querydebtrightlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryDebtRightList(@RequestBody QueryDebtRightInfoRequestVO vo) {
		log.info("P2PAssetController.queryDebtRightList.req:" + JSON.toJSONString(vo));
		QueryDebtRightInfoRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryDebtRightInfoRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2PAssetBiz.queryDebtRightList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryDebtRightList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 债权详情查询
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querydebtrighdetail", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryDebtRighDetail(@RequestBody QueryDebtRighDetailVO vo) {
		log.info("P2PAssetController.queryDebtRighDetail.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = p2PAssetBiz.queryDebtRighDetail(vo.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryDebtRighDetail.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	/**
	 * 兑付列表查询
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querycashlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryCashList(@RequestBody QueryDebtRighDetailVO vo) {
		log.info("P2PAssetController.queryCashList.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = p2PAssetBiz.queryCashList(vo.getPageSize(),vo.getPageNo());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryCashList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 债权详情列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querydebtrighdetaillist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryDebtRighDetailList(@RequestBody QueryDebtRighDetailVO vo) {
		log.info("P2PAssetController.queryDebtRighDetailList.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		QueryDebtRightInfoRequest req=new QueryDebtRightInfoRequest();
		try {
			req.setProductCode(vo.getProductCode());
			resp = p2PAssetBiz.queryCashDetailList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryDebtRighDetailList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 获取合同
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getloanagreement", method = RequestMethod.GET)
	@ResponseBody
	public BaseWebResponse 	getLoanAgreement(String assetNo) {
		log.info("P2PAssetController.getLoanAgreement.assetNo=" + assetNo);
		BaseWebResponse resp = null;
		QueryDebtRightInfoRequest req=new QueryDebtRightInfoRequest();
		try {
			resp = p2PAssetBiz.getLoanAgreement(assetNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.getLoanAgreement.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 借款订单列表查询
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryloaninfolist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryLoanInfoList(@RequestBody QueryLoanInfoRequest vo) {
		log.info("P2PAssetController.queryLoanInfoList.req:" + JSON.toJSONString(vo));
		QueryLoanInfoRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryLoanInfoRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2PAssetBiz.queryLoanInfoList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryLoanInfoList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 借款订单详情查询
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryloaninfodetail", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryLoanInfoDetail(@RequestBody QueryLoanInfoRequest vo) {
		log.info("P2PAssetController.queryLoanInfoDetail.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			QueryLoanInfoRequest req=new QueryLoanInfoRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2PAssetBiz.queryLoanInfoDetail(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryLoanInfoDetail.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 借款订单审核
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/approvalloaninfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	approvalLoanInfo(@RequestBody ApprovalLoanInfoRequest vo) {
		log.info("P2PAssetController.queryLoanInfoDetail.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			ApprovalLoanInfoRequest req=new ApprovalLoanInfoRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setApprovalBy(httpSessionUtils.getCuruser().getName());
			resp = p2PAssetBiz.approvalLoanInfo(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.approvalLoanInfo.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 借款订单列表查询
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryoverduedaysgt30repayplanlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryOverdueDaysGT30RepayPlanList(@RequestBody QueryAssetRepayPlanListRequest vo) {
		log.info("P2PAssetController.queryOverdueDaysGT30RepayPlanList.req:" + JSON.toJSONString(vo));
		QueryAssetRepayPlanListRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryAssetRepayPlanListRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2PAssetBiz.queryOverdueDaysGT30RepayPlanList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryOverdueDaysGT30RepayPlanList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 借款订单列表查询
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryoverduedayslt30repayplanlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse 	queryOverdueDaysLT30RepayPlanList(@RequestBody QueryAssetRepayPlanListRequest vo) {
		log.info("P2PAssetController.queryOverdueDaysLT30RepayPlanList.req:" + JSON.toJSONString(vo));
		QueryAssetRepayPlanListRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryAssetRepayPlanListRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2PAssetBiz.queryOverdueDaysLT30RepayPlanList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryOverdueDaysLT30RepayPlanList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

}
