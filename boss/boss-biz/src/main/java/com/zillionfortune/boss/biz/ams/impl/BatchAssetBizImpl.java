package com.zillionfortune.boss.biz.ams.impl;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.BatchAssetFacade;
import com.zb.fincore.ams.facade.dto.req.*;
import com.zb.fincore.ams.facade.dto.resp.BatchCreateAssetResponse;
import com.zb.fincore.ams.facade.dto.resp.RegisterExchangeAssetResponse;
import com.zb.fincore.ams.facade.model.AssetExchangeRegisterModel;
import com.zb.fincore.ams.facade.model.AssetModel;
import com.zb.fincore.ams.facade.model.FileTemplateParamModel;
import com.zb.fincore.ams.facade.model.UnPackageAssetDetailModel;
import com.zillionfortune.boss.biz.ams.BatchAssetBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.dal.dao.AssetFileDao;
import com.zillionfortune.boss.dal.entity.AssetFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产批量录入业务处理
 * Created by MABIAO on 2017/6/30 0030.
 */
@Service
public class BatchAssetBizImpl implements BatchAssetBiz {

    private Logger logger = LoggerFactory.getLogger(BatchAssetBizImpl.class);

    @Autowired
    private AssetFileDao assetFileDao;

    @Autowired
    private BatchAssetFacade batchAssetFacade;

    @Override
    public BaseWebResponse importBatchAsset(ImportBatchAssetRequest req) {
        logger.info("BatchAssetBizImpl.importBatchAsset.request:" + JSON.toJSONString(req));
        BatchCreateAssetResponse response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.importBatchAsset(req);
            logger.info("BatchAssetBizImpl.batchadd.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("totalCount", response.getTotalCount());
            respMap.put("errorCount", response.getErrorCount());
            respMap.put("errorMap", response.getErrorModelList());
            respMap.put("batchNo", response.getBatchNo());

            resp.setData(respMap);

        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("totalCount", response.getTotalCount());
            respMap.put("errorCount", response.getErrorCount());
            respMap.put("errorMap", response.getErrorModelList());
            respMap.put("batchNo", response.getBatchNo());
            resp.setData(respMap);
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryCreditAssetListForApproval(QueryCreditAssetListRequest req) {
        logger.info("BatchAssetBizImpl.queryCreditAssetListForApproval.request:" + JSON.toJSONString(req));
        PageQueryResponse<AssetModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryCreditAssetListForApproval(req);
            logger.info("BatchAssetBizImpl.queryAssetListForApproval.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())||ResultCode.NO_DATA.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("dataList", response.getDataList()!=null?response.getDataList():new ArrayList());
            respMap.put("pageNo", response.getPageNo());
            respMap.put("pageSize", response.getPageSize());
            respMap.put("totalCount", response.getTotalCount());
            respMap.put("totalPage", response.getTotalPage());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse approveCreditAsset(ApprovalCreditAssetRequest req) {
        logger.info("BatchAssetBizImpl.approveCreditAsset.request:" + JSON.toJSONString(req));
        BaseResponse response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.approvalCreditAsset(req);
            logger.info("BatchAssetBizImpl.approveCreditAsset.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse selectByFileName(String fileName) {
        logger.info("selectByFileName.req:" + fileName);

        BaseWebResponse resp = null;

        try {
            // 判断文件名是否已存在
            String name = fileName.substring(0,fileName.lastIndexOf("."));
            AssetFile file = new AssetFile();
            file.setFileName(name);
            List<AssetFile> fileList = assetFileDao.selectByFileName(file);
            if (null != fileList && fileList.size() > 0) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ASSET_FILE_IS_EXIST.code(),ResultCode.ASSET_FILE_IS_EXIST.desc());
                return resp;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
        } finally {
            logger.info("selectByFileName.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    @Override
    public BaseWebResponse insertAssetFile(String fileName) {
        logger.info("add.req:" + fileName);

        BaseWebResponse resp = null;

        try {

            String name = fileName.substring(0,fileName.lastIndexOf("."));
            // 新增文件
            AssetFile assetFile = new AssetFile();
            assetFile.setFileName(name);
            assetFile.setStatus(1);
            assetFileDao.insert(assetFile);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
        } finally {
            logger.info("insertAssetFile.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    @Override
    public BaseWebResponse createFileTemplateParam(CreateFileTemplateParamRequest req) {
        logger.info("BatchAssetBizImpl.createFileTemplateParam.request:" + JSON.toJSONString(req));
        BaseResponse response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.createFileTemplateParam(req);
            logger.info("BatchAssetBizImpl.createFileTemplateParam.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());

        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse generateContract(GenerateContractRequest req) {
        logger.info("BatchAssetBizImpl.generateContract.request:" + JSON.toJSONString(req));
        BaseResponse response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.generateContract(req);
            logger.info("BatchAssetBizImpl.generateContract.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());

        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryAssetContractList(QueryAssetContractListRequest req) {
        logger.info("BatchAssetBizImpl.queryAssetContractList.request:" + JSON.toJSONString(req));
        PageQueryResponse<FileTemplateParamModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryAssetContractList(req);
            logger.info("BatchAssetBizImpl.queryAssetContractList.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())||ResultCode.NO_DATA.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("dataList", response.getDataList()!=null?response.getDataList():new ArrayList());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryUnPackageAssetList(QueryUnPackageAssetRequest req) {
        logger.info("BatchAssetBizImpl.queryUnPackageAssetList.request:" + JSON.toJSONString(req));
        PageQueryResponse<AssetModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryUnPackageAssetList(req);
            logger.info("BatchAssetBizImpl.queryUnPackageAssetList.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())||ResultCode.NO_DATA.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("dataList", response.getDataList()!=null?response.getDataList():new ArrayList());
            respMap.put("pageNo", response.getPageNo());
            respMap.put("pageSize", response.getPageSize());
            respMap.put("totalCount", response.getTotalCount());
            respMap.put("totalPage", response.getTotalPage());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryRecordAssetList(QueryRecordAssetListRequest req) {
        logger.info("BatchAssetBizImpl.queryRecordAssetList.request:" + JSON.toJSONString(req));
        PageQueryResponse<AssetModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryRecordAssetList(req);
            logger.info("BatchAssetBizImpl.queryRecordAssetList.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())||ResultCode.NO_DATA.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("dataList", response.getDataList()!=null?response.getDataList():new ArrayList());
            respMap.put("pageNo", response.getPageNo());
            respMap.put("pageSize", response.getPageSize());
            respMap.put("totalCount", response.getTotalCount());
            respMap.put("totalPage", response.getTotalPage());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryFileTemplateParam(QueryFileTemplateParamRequest req) {
        logger.info("BatchAssetBizImpl.queryFileTemplateParam.request:" + JSON.toJSONString(req));
        QueryResponse<FileTemplateParamModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryFileTemplateParam(req);
            logger.info("BatchAssetBizImpl.queryFileTemplateParam.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("data", response.getData()!=null?response.getData():new Object());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse registerExchangeAsset(RegisterExchangeAssetRequest req) {
        logger.info("BatchAssetBizImpl.registerExchangeAsset.request:" + JSON.toJSONString(req));
        RegisterExchangeAssetResponse response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.registerExchangeAsset(req);
            logger.info("BatchAssetBizImpl.generateContract.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("data", response.getAssetCode()!=null?response.getAssetCode():new Object());
            resp.setData(respMap);

        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse updateRegisterExchangeInfo(UpdateRegisterExchangeInfoRequest req) {
        logger.info("BatchAssetBizImpl.registerExchangeAsset.request:" + JSON.toJSONString(req));
        BaseResponse response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.updateRegisterExchangeInfo(req);
            logger.info("BatchAssetBizImpl.generateContract.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());

        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryUnPackageAssetDetail(QueryUnPackageAssetDetailRequest req) {
        logger.info("BatchAssetBizImpl.queryUnPackageAssetDetail.request:" + JSON.toJSONString(req));

        QueryResponse<UnPackageAssetDetailModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryUnPackageAssetDetail(req);
            logger.info("BatchAssetBizImpl.queryFileTemplateParam.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("data", response.getData()!=null?response.getData():new Object());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryExchangeRegisterRecordList(QueryExchangeRegisterRecordListRequest req) {
        logger.info("BatchAssetBizImpl.queryAssetContractList.request:" + JSON.toJSONString(req));
        PageQueryResponse<AssetExchangeRegisterModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryExchangeRegisterRecordList(req);
            logger.info("BatchAssetBizImpl.queryAssetContractList.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())||ResultCode.NO_DATA.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("dataList", response.getDataList()!=null?response.getDataList():new ArrayList());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }

    @Override
    public BaseWebResponse queryExchangeRegisterRecordDetail(QueryExchangeRegisterDetailRequest req) {
        logger.info("BatchAssetBizImpl.queryExchangeRegisterRecordDetail.request:" + JSON.toJSONString(req));

        QueryResponse<UnPackageAssetDetailModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =batchAssetFacade.queryExchangeRegisterRecordDetail(req);
            logger.info("BatchAssetBizImpl.queryFileTemplateParam.response:"
                    + JSON.toJSONString(response));
        } catch (Exception e) {
            resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
        }

        if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
            Map<String,Object> respMap = new HashMap<String,Object>();
            respMap.put("addition", response.getAddition());
            respMap.put("data", response.getData()!=null?response.getData():new Object());
            resp.setData(respMap);
        }else{
            resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
        }

        return resp;
    }
}
