package com.zillionfortune.boss.web.controller.ams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSException;
import com.zb.fincore.ams.facade.dto.req.*;
import com.zb.fincore.ams.facade.model.*;
import com.zb.fincore.common.utils.DateUtils;
import com.zillionfortune.boss.biz.ams.AssetBiz;
import com.zillionfortune.boss.biz.ams.BatchAssetBiz;
import com.zillionfortune.boss.biz.file.FileBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.enums.SignLevelEnum;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 批量录入相关
 * Created by MABIAO on 2017/6/29 0029.
 */
@Controller
@RequestMapping(value = "/batchAssetService")
public class BatchAssetController {

    private final Logger log = LoggerFactory.getLogger(BatchAssetController.class);

    @Autowired
    private BatchAssetBiz batchAssetBiz;

    @Autowired
    private AssetBiz assetBiz;

    @Autowired
    private FileBiz fileBiz;

    @Autowired
    private HttpSessionUtils httpSessionUtils;

    /**
     * 文件导入
     * @return
     */
    @RequestMapping(value = "importBatchAsset",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse importBatchAsset(@RequestParam(value = "file", required = false) MultipartFile file){
        InputStream is = null;
        Workbook wb = null;
        ImportBatchAssetRequest req = new ImportBatchAssetRequest();
        //计划表
        List<IssuePlanModel> planModelList = new ArrayList<IssuePlanModel>();
        BaseWebResponse resp = null;
        List<ErrorModel> errorModelList = new ArrayList<ErrorModel>();
        int errorCount = 0;
        try{
            String fileName = file.getOriginalFilename();
            resp = batchAssetBiz.selectByFileName(fileName);
            if(null != resp){
                return resp;
            }

            is = file.getInputStream();
            wb = WorkbookFactory.create(is);//POI读取excel文件流

            Sheet sheet1 = wb.getSheetAt(0);//第一个sheet页
            Sheet sheet2 = wb.getSheetAt(1);//第一个sheet页

            String sheet1Titles[] = {"授信业务编号","授信额度","融资方","出资方","证件号","法人代表","联系方式",
                    "地址","用途","收益率","起息日","结息日","天数","还款方式"};
            String sheet2Titles[] = {"产品天数","合同天数","（合同起息）/备案开始","备案完成",
                    "空档期开始","空档期结束","募集开始","募集结束","产品计息","（合同结息）/产品结息","单包金额","个数","总金额","融资方"};
            for(int i = 0;i < sheet1Titles.length;i++){//业务授信表
                Row row = sheet1.getRow(0);//获取标题行
                String str = getStringValue(row.getCell(i));
                if(!str.equals(sheet1Titles[i])){
                    ErrorModel errorModel = new ErrorModel();
                    List<String> errStr=new ArrayList<String>();
                    errStr.add("业务授信表标题【"+str + "】有误");
                    resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"读取文件失败");
                    errorModel.setErrorList(errStr);
                    errorModelList.add(errorModel);
                    Map<String,Object> respMap = new HashMap<String,Object>();
                    respMap.put("totalCount", planModelList.size());
                    respMap.put("errorCount", errorModelList.size());
                    respMap.put("errorMap", errorModelList);
                    resp.setData(respMap);
                    return resp;
                }
            }

            for(int i = 0;i < sheet2Titles.length;i++){//产品计划发行表
                Row row = sheet2.getRow(0);//获取标题行
                String str = getStringValue(row.getCell(i));
                if(!str.equals(sheet2Titles[i])){
                    ErrorModel errorModel = new ErrorModel();
                    List<String> errStr=new ArrayList<String>();
                    errStr.add("产品计划发行表标题【"+str + "】有误");
                    resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"读取文件失败");
                    errorModel.setErrorList(errStr);
                    errorModelList.add(errorModel);
                    Map<String,Object> respMap = new HashMap<String,Object>();
                    respMap.put("totalCount", planModelList.size());
                    respMap.put("errorCount", errorModelList.size());
                    respMap.put("errorMap", errorModelList);
                    resp.setData(respMap);
                    return resp;
                }
            }

            List<String> errStr=new ArrayList<String>();
            for(int j = 1;j < 2; j ++){

                if(errorModelList.size() > 0){
                    break;//有一个校验未通过即跳出循环
                }
                Row row = sheet1.getRow(j);
                //授信
//                List<String> errStr=new ArrayList<String>();
                BusinessCreditModel creditModel = new BusinessCreditModel();
                Cell cell = row.getCell(0);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【授信业务编号】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setCreditBusinessCode(getStringValue(row.getCell(0)));//授信业务编号
                }

                cell = row.getCell(1);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【授信额度】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setCreditLimitAmount(new BigDecimal(getStringValue(row.getCell(1))));//授信额度
                }

                cell = row.getCell(2);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【融资方】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setFinancingName(getStringValue(row.getCell(2)));//融资方
                }

                cell = row.getCell(3);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【出资方】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setProvideLoanName(getStringValue(row.getCell(3)));//出资方
                }

                cell = row.getCell(4);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【证件号码】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setCertNo(getStringValue(row.getCell(4)));//证件号码
                }

                cell = row.getCell(5);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【法人代表】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setLegalPersonName(getStringValue(row.getCell(5)));//法人代表
                }

                cell = row.getCell(6);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【联系方式】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setContactWay(getStringValue(row.getCell(6)));//联系方式
                }

                cell = row.getCell(7);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【地址】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setAddress(getStringValue(row.getCell(7)));//地址
                }

                cell = row.getCell(8);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【用途】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setFinancingPurpose(getStringValue(row.getCell(8)));//用途
                }

                cell = row.getCell(9);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【收益率】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setYieldRate(new BigDecimal(getStringValue(row.getCell(9))));//收益率
                }

                cell = row.getCell(10);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【起息日】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        creditModel.setValueStartTime(row.getCell(10).getDateCellValue());//起息日
                    }catch (Exception e){
                        errStr.add("第"+j+"行的【起息日】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(11);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【结息日】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        creditModel.setValueEndTime(row.getCell(11).getDateCellValue());//结息日
                    }catch (Exception e){
                        errStr.add("第"+j+"行的【结息日】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(12);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【天数】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
                        CellValue cellValue = evaluator.evaluate(row.getCell(12));
                        String value = cellValue.formatAsString().substring(0,cellValue.formatAsString().lastIndexOf("."));
                        creditModel.setDayCount(Integer.valueOf(value));//天数
                    }catch (Exception e){
                        errStr.add("第"+j+"行的【天数】格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(13);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+j+"行的【还款方式】值不能为空");
                    errorCount++;
                    break;
                }else{
                    creditModel.setRepaymentType(getStringValue(row.getCell(13)));//还款方式
                }

                req.setCreditModel(creditModel);
            }
            if(errStr.size() > 0){
                ErrorModel errorModel = new ErrorModel();
                errorModel.setErrorList(errStr);
                errorModelList.add(errorModel);
            }
            req.setPlanModelList(planModelList);
            if(errorModelList.size() > 0){
                resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"读取文件失败");
                Map<String,Object> respMap = new HashMap<String,Object>();
                respMap.put("totalCount", planModelList.size());
                respMap.put("errorCount", errorModelList.size());
                respMap.put("errorMap", errorModelList);
                resp.setData(respMap);
                return resp;
            }

            int sheetNum = sheet2.getLastRowNum() + 1;
            for(int i = 1;i < sheetNum;i ++){
                Row row = sheet2.getRow(i);//获取第i行

                IssuePlanModel planModel = new IssuePlanModel();

                if(errorModelList.size() > 0){
                    break;//有一个校验未通过即跳出循环
                }
                Cell cell = row.getCell(0);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【产品天数】值不能为空");
                    errorCount++;
                    break;
                }else{
                    String str = getStringValue(row.getCell(0));
                    planModel.setProductDayCount(Integer.valueOf(str));//产品天数
                }

                cell = row.getCell(1);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【合同天数】值不能为空");
                    errorCount++;
                    break;
                }else{
                    XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
                    CellValue cellValue = evaluator.evaluate(cell);
                    String value = cellValue.formatAsString().substring(0,cellValue.formatAsString().lastIndexOf("."));
                    planModel.setContractDayCount(Integer.valueOf(value));//合同天数
                }

                cell = row.getCell(2);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【(合同起息)/备案开始】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        planModel.setValueStartTime(cell.getDateCellValue());//合同起息时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【(合同起息)/备案开始】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(3);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【备案完成】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        planModel.setValueEndTime(row.getCell(3).getDateCellValue());//合同结息时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【备案完成】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(4);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
//                    errStr.add("第"+i+"行的【空档期开始】值不能为空");
//                    errorCount++;
//                    break;
                }else{
                    try {
                        planModel.setFreeStartTime(row.getCell(4).getDateCellValue());//空档开始时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【空档期开始】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(5);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
//                    errStr.add("第"+i+"行的【空档期结束】值不能为空");
//                    errorCount++;
//                    break;
                }else{
                    try {
                        planModel.setFreeEndTime(row.getCell(5).getDateCellValue());//空档结束时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【空档期结束】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(6);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【募集开始】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        planModel.setSaleStartTime(row.getCell(6).getDateCellValue());//募集开始时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【募集开始】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(7);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【募集开始】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        planModel.setSaleEndTime(row.getCell(7).getDateCellValue());//募集结束时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【募集开始】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(8);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【产品计息】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        planModel.setProductValueStartTime(row.getCell(8).getDateCellValue());//产品计息开始时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【产品计息】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(9);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【(合同结息)/产品结息】值不能为空");
                    errorCount++;
                    break;
                }else{
                    try {
                        planModel.setProductValueEndTime(row.getCell(9).getDateCellValue());//产品结息时间
                    }catch (Exception e){
                        errStr.add("第"+i+"行的【(合同结息)/产品结息】日期格式不正确");
                        errorCount++;
                        break;
                    }
                }

                cell = row.getCell(10);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【单包金额】值不能为空");
                    errorCount++;
                    break;
                }else{
                    String singleAmount = getStringValue(row.getCell(10));
                    planModel.setSingleAmount(new BigDecimal(singleAmount));//单包金额
                }

                cell = row.getCell(11);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【个数】值不能为空");
                    errorCount++;
                    break;
                }else{
                    planModel.setAssetCount(Integer.valueOf(getStringValue(row.getCell(11))));//个数
                }

                cell = row.getCell(12);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【总金额】值不能为空");
                    errorCount++;
                    break;
                }else{
                    XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
                    CellValue cellValue = evaluator.evaluate(cell);
                    String value = cellValue.formatAsString().substring(0,cellValue.formatAsString().lastIndexOf("."));
                    planModel.setTotalAmount(new BigDecimal(value));//总金额
                }

                cell = row.getCell(13);
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    errStr.add("第"+i+"行的【融资方】值不能为空");
                    errorCount++;
                    break;
                }else{
                    String subjectName = getStringValue(row.getCell(13));
                    BusinessCreditModel creditModel = req.getCreditModel();
                    if(!creditModel.getProvideLoanName().equals(subjectName)){
                        errStr.add("第"+i+"行的【融资方】与业务授信表的【出资方】不一致");
                        errorCount++;
                        break;
                    }
                    planModel.setSubjectName(subjectName);
                }

                if(planModel.getProductDayCount() >= planModel.getContractDayCount()){
                    errStr.add("第"+i+"行的【产品天数】要小于【合同天数】");
                    errorCount++;
                    break;
                }

                //起息日小于结息日
                if(DateUtils.isAfterOrEqual(planModel.getValueStartTime(), planModel.getProductValueEndTime())){
                    errStr.add("第"+i+"行的【合同起息】要小于【合同结息】");
                }

                planModelList.add(planModel);
            }
            if(errStr.size() > 0){
                ErrorModel errorModel = new ErrorModel();
                errorModel.setErrorList(errStr);
                errorModelList.add(errorModel);
            }

            if(errorModelList.size() > 0){
                resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"读取文件失败");
                Map<String,Object> respMap = new HashMap<String,Object>();
                respMap.put("totalCount", planModelList.size());
                respMap.put("errorCount", errorModelList.size());
                respMap.put("errorMap", errorModelList);
                resp.setData(respMap);
                return resp;
            }

            resp = batchAssetBiz.importBatchAsset(req);
            if(ResultCode.SUCCESS.code().equals(resp.getResultCode())){
                batchAssetBiz.insertAssetFile(fileName);//保存
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
        } finally {
            IOUtils.closeQuietly(is);
            log.info("BatchAssetController.importBatchAsset.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }


    /**
     * 授信资产录入审核列表
     */
    @RequestMapping(value = "queryCreditAssetListForApproval",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryCreditAssetListForApproval(@RequestBody QueryCreditAssetListRequest req){
        log.info("BatchAssetController.queryCreditAssetListForApproval.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            //1.===参数校验
            if(req==null){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(),
                        ResultCode.ILLEGAL_PARAMETER.desc());
            }
            if(null == SignLevelEnum.getEnum(req.getApprovalLevel())){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.QUERY_POWER_NOT_EXIST.code(),
                        ResultCode.QUERY_POWER_NOT_EXIST.desc());
                return resp;
            }

            List<String> signLevels = httpSessionUtils.getCuruser().getSignLevels();
            boolean isSign = false;
            for (String level : signLevels){
                if(level.equals(req.getApprovalLevel())){
                    isSign = true;
                }
            }

            if(!isSign){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.QUERY_POWER_NOT_EXIST.code(),
                        ResultCode.QUERY_POWER_NOT_EXIST.desc());
                return resp;
            }else {
                if(SignLevelEnum.ASSET_APPROVE_LEVEL1.getCode().equals(req.getApprovalLevel())){
                    req.setSign("A");
                }
                if(SignLevelEnum.ASSET_APPROVE_LEVEL2.getCode().equals(req.getApprovalLevel())){
                    req.setSign("B");
                }
            }

            //3.===调用查询方法
            resp = batchAssetBiz.queryCreditAssetListForApproval(req);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.queryCreditAssetListForApproval.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 审核资产
     *
     * @param req 审核资产请求对象
     * @return 审核资产响应对象
     */
    @RequestMapping(value = "approvalCreditAsset", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse approvalCreditAsset(@RequestBody ApprovalCreditAssetRequest req) {
        log.info("BatchAssetController.approvalCreditAsset.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            //1.===参数校验
            if(req==null){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(),
                        ResultCode.ILLEGAL_PARAMETER.desc());
                return resp;
            }
            if(null == SignLevelEnum.getEnum(req.getApprovalLevel())){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(),
                        ResultCode.ILLEGAL_PARAMETER.desc());
                return resp;
            }

            List<String> signLevels = httpSessionUtils.getCuruser().getSignLevels();
            boolean isSign = false;
            for (String level : signLevels){
                if(level.equals(req.getApprovalLevel())){
                    isSign = true;
                }
            }

            if(!isSign){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(),
                        ResultCode.ILLEGAL_PARAMETER.desc());
                return resp;
            }else {
                if(SignLevelEnum.ASSET_APPROVE_LEVEL1.getCode().equals(req.getApprovalLevel())){
                    req.setSign("A");
                }
                if(SignLevelEnum.ASSET_APPROVE_LEVEL2.getCode().equals(req.getApprovalLevel())){
                    req.setSign("B");
                }
            }

            resp = batchAssetBiz.approveCreditAsset(req);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.queryCreditAssetListForApproval.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 创建合同模板参数
     * @param req 创建合同模板参数请求对象
     * @return 创建合同模板参数响应对象
     */
    @RequestMapping(value = "createFileTemplateParam", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse createFileTemplateParam(@RequestBody CreateFileTemplateParamRequest req) {
        log.info("BatchAssetController.createFileTemplateParam.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.createFileTemplateParam(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.createFileTemplateParam.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 合同生成
     * @param req
     * @return
     */
    @RequestMapping(value = "generateContract",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse generateContract(@RequestBody GenerateContractRequest req){
        log.info("BatchAssetController.generateContract.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.generateContract(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.generateContract.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 合同生成列表
     * @param req
     * @return
     */
    @RequestMapping(value = "queryAssetContractList",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryAssetContractList(@RequestBody QueryAssetContractListRequest req){
        log.info("BatchAssetController.generateContract.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.queryAssetContractList(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.generateContract.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 授信资产合同生成查询列表
     * @param req
     * @return
     */
    @RequestMapping(value = "queryUnPackageAssetList",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryUnPackageAssetList(@RequestBody QueryUnPackageAssetRequest req){
        log.info("BatchAssetController.generateContract.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.queryUnPackageAssetList(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.generateContract.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 授信资产合同生成详情
     * @param req
     * @return
     */
    @RequestMapping(value = "queryUnPackageAssetDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryUnPackageAssetDetail(@RequestBody QueryUnPackageAssetDetailRequest req){
        log.info("BatchAssetController.queryUnPackageAssetDetail.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.queryUnPackageAssetDetail(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.generateContract.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 备案资产查询列表
     * @param req
     * @return
     */
    @RequestMapping(value = "queryRecordAssetList",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryRecordAssetList(@RequestBody QueryRecordAssetListRequest req){
        log.info("BatchAssetController.generateContract.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.queryRecordAssetList(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.generateContract.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 资产合同参数详情
     */
    @RequestMapping(value = "queryFileTemplateParam",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryFileTemplateParam(@RequestBody QueryFileTemplateParamRequest req){
        log.info("BatchAssetController.generateContract.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.queryFileTemplateParam(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.generateContract.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 资产备案
     * @param req
     * @return
     */
    @RequestMapping(value = "registerExchangeAsset",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse registerExchangeAsset(@RequestBody RegisterExchangeAssetRequest req){
        log.info("BatchAssetController.registerExchangeAsset.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.registerExchangeAsset(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.registerExchangeAsset.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 备案更新
     * @param req
     * @return
     */
    @RequestMapping(value = "updateRegisterExchangeInfo",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse updateRegisterExchangeInfo(@RequestBody UpdateRegisterExchangeInfoRequest req){
        log.info("BatchAssetController.updateRegisterExchangeInfo.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.updateRegisterExchangeInfo(req);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.updateRegisterExchangeInfo.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * download:文件下载. <br/>
     *
     * @param fileName
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/download",method=RequestMethod.GET)
    public String download(String fileName, HttpServletResponse response) {
        log.info("BatchAssetController.download.req:fileName = " + fileName);
        OutputStream out = null;
        InputStream inputStream = null;
        try {
            // step1: 参数校验
            if (fileName == null) {
                return "文件名不能为空";
            }

            // step2: 调用文件下载
            // 获取文件下载输入流
            inputStream = fileBiz.getInputStream(fileName);
            if (inputStream == null) {
                return "没有找到相应文件";
            }
            // 将文件名的特殊英文字符反转码,字符包含        空格 () {} []
//            fileName = URLEncoder.encode(fileName.substring(26), "UTF-8").replaceAll("\\+", "%20")
//                    .replaceAll("%28", "\\(").replaceAll("%29", "\\)").replaceAll("%7B", "\\{").replaceAll("%7D", "\\}")
//                    .replaceAll("%5B", "\\[").replaceAll("%5D", "\\]");
            response.setContentType("application/octet-stream;charset=UTF-8");//流的形式
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            out = response.getOutputStream();
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size=inputStream.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
        } catch (OSSException e) {
            log.error(e.getMessage(), e);
            return "没有找到相应文件";
        }catch (Exception e) {
            log.error("文件下载失败");
            log.error(e.getMessage(), e);
            return "文件下载失败";
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("文件下载失败");
                log.error(e.getMessage(), e);
                return "文件下载失败";
            }
        }

        return null;
    }

    /**
     * download:文件上传. <br/>
     *
     * @return
     */
        @RequestMapping(value = "upload",method = RequestMethod.POST)
        @ResponseBody
        public BaseWebResponse upload(@RequestParam(value = "file", required = false) MultipartFile file){

            BaseWebResponse resp = null;
            try {
                resp = fileBiz.uploadAssetFile(file);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
                resp = new BaseWebResponse(RespCode.FAIL.code(),"上传文件处理异常");
            } finally {
                log.info("BatchAssetController.upload.resp:" +JSONObject.toJSONString(resp) );
            }

            return resp;
        }




    /**
     * getStringValue:读取excel文本. <br/>
     * @param cell
     * @return
     * @throws Exception
     */
    private static String getStringValue(Cell cell) throws Exception{
        if(cell == null) return null;

        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)){
            return DateUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");//日期类型
        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            return NumberToTextConverter.toText(cell.getNumericCellValue());//数值类型
        }else{
            return cell.getStringCellValue().trim();//字符串类型
        }
    }

    /**
     * getStringValue:读取excel金额. <br/>
     * @param cell
     * @return
     * @throws Exception
     */
    private static BigDecimal getBigDecimalValue(Cell cell) throws Exception{
        if(cell == null) return null;

        BigDecimal bg = null;
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){//数值类型
            try {
                bg = new BigDecimal(NumberToTextConverter.toText(cell.getNumericCellValue()));
                return bg;
            } catch (Exception e) {
                return null;
            }
        }else{//字符串类型
            return null;
        }
    }

    /**
     * 更新资产披露信息
     *
     * @return
     */
    @RequestMapping(value = "updateAssetPublishInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse updateAssetPublishInfo(
            @RequestParam(value = "assetCode", required = true) String assetCode,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        InputStream is = null;
        BaseWebResponse resp = null;
        try {
            is = file.getInputStream();
            Workbook wb = WorkbookFactory.create(is);//POI读取excel文件流
            Sheet sheet = wb.getSheetAt(0);//第一个sheet页
            Row titleRow = sheet.getRow(0);//获取标题行
            Row contentRow = sheet.getRow(1); //获取内容行

            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < titleRow.getLastCellNum() - 1; i++) {
                Cell titleCell = titleRow.getCell(i);
                if (titleCell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }
                String key = getStringValue(titleCell);
                key = key.split("/")[1];
                Cell contentCell = contentRow.getCell(i);
                if (null != contentCell && contentCell.getCellType() != Cell.CELL_TYPE_BLANK) {
                    jsonObject.put(key, getStringValue(contentCell));
                } else {
                    jsonObject.put(key, "");
                }
            }
            resp = assetBiz.updateAssetPublishInfo(assetCode, jsonObject.toJSONString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
            }
        } finally {
            IOUtils.closeQuietly(is);
            log.info("BatchAssetController.updateAssetPublishInfo.resp:" + JSON.toJSONString(resp));
        }
        return resp;
    }

    /**
     * 查询备案记录列表
     */
    @RequestMapping(value = "queryExchangeRegisterRecordList",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryExchangeRegisterRecordList(@RequestBody QueryExchangeRegisterRecordListRequest req){
        log.info("BatchAssetController.queryExchangeRegisterRecordList.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.queryExchangeRegisterRecordList(req);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.queryExchangeRegisterRecordList.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }

    /**
     * 查询备案记录详情
     */
    @RequestMapping(value = "queryExchangeRegisterRecordDetail",method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryExchangeRegisterRecordDetail(@RequestBody QueryExchangeRegisterDetailRequest req){
        log.info("BatchAssetController.queryExchangeRegisterRecordDetail.req:" + JSON.toJSONString(req));

        BaseWebResponse resp = null;
        try {
            resp = batchAssetBiz.queryExchangeRegisterRecordDetail(req);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                        e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }

        } finally {
            log.info("BatchAssetController.queryExchangeRegisterRecordDetail.resp:" + JSON.toJSONString(resp));
        }

        return resp;
    }
}
