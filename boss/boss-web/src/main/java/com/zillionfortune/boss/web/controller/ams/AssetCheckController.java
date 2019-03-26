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
import com.zb.fincore.ams.facade.dto.req.QueryAssetTransactionRequest;
import com.zillionfortune.boss.biz.ams.AssetCheckBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.ams.vo.AssetCheckVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryNotInProcessAssetVO;

@Controller
@RequestMapping(value = "/assetCheckService")
public class AssetCheckController {

    private final Logger log = LoggerFactory.getLogger(AssetApprovalController.class);

    @Autowired
    private AssetCheckBiz assetCheckBiz;

    @RequestMapping(value = "/queryInProcessAsset", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryInProcessAsset() {

        BaseWebResponse resp = null;

        try {

            resp = assetCheckBiz.queryInProcessAsset();

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            if (e instanceof BusinessException) {

                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());

            } else {

                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("AssetApprovalController.querylist.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    @RequestMapping(value = "/queryNotInProcessAsset", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryNotInProcessAsset(@RequestBody QueryNotInProcessAssetVO vo) {

        BaseWebResponse resp = null;

        try {

            resp = assetCheckBiz.queryNotInProcessAsset(vo.getStartDate());

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            if (e instanceof BusinessException) {

                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());

            } else {

                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("AssetApprovalController.querylist.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    @RequestMapping(value = "/queryAssetTransaction", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryAssetTransaction(@RequestBody AssetCheckVO vo) {

        BaseWebResponse resp = null;
        QueryAssetTransactionRequest req = null;
        try {
            if(vo==null){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
                        ResultCode.ILLEGAL_PARAMETER.desc());
            }
            req = new QueryAssetTransactionRequest();
            req.setPageNo(vo.getPageNo());
            req.setPageSize(vo.getPageSize());

            resp = assetCheckBiz.queryAssetTransaction(req);

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            if (e instanceof BusinessException) {

                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());

            } else {

                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("AssetApprovalController.querylist.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }
}
