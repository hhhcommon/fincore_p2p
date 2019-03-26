package com.zillionfortune.boss.web.controller.ams;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.zb.fincore.ams.facade.model.ErrorModel;
import com.zb.fincore.common.utils.DateUtils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.facade.dto.req.BatchCreateAssetRequest;
import com.zb.fincore.ams.facade.dto.req.CreateAssetRequest;
import com.zb.fincore.ams.facade.dto.req.QueryAssetListForManageRequest;
import com.zb.fincore.ams.facade.dto.req.QueryAssetRequest;
import com.zillionfortune.boss.biz.ams.AssetBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.ExcelUtil;
import com.zillionfortune.boss.web.controller.ams.vo.AssetAddVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetQueryListVO;
import com.zillionfortune.boss.web.controller.ams.vo.AssetQueryVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryAssetApprovalListVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 资产相关Controller
 * 
 * @author litaiping
 *
 */
@Controller
@RequestMapping(value = "/assetservice")
public class AssetController {
    
    private final Logger log = LoggerFactory.getLogger(AssetController.class);
    
    private final static String FILE_NAME="资产批量导入";
    
    private final static String FILE_EXT_NAME=".xlsx";
    
    @Autowired
    private AssetBiz assetBiz;
    
	@Autowired
	private HttpSessionUtils httpSessionUtils;
    /**
     * 新增资产
     * @param req
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse add(@RequestBody AssetAddVO vo) {
    	
    	log.info("AssetController.add.req:" + JSON.toJSONString(vo));
		
    	CreateAssetRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new CreateAssetRequest();
			PropertyUtils.copyProperties(req, vo);
			try {
				req.setCreateBy(httpSessionUtils.getCuruser().getName());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常",e);
			}
			
			if(req.getCreateBy()==null){
				req.setCreateBy("1");
			}
			
			//3.===调用新增方法
			resp = assetBiz.createAsset(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
    /**
     * 新增资产
     * @param req
     * @return
     */
    @RequestMapping(value = "/batchadd", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse batchAdd(@RequestParam(value = "file", required = false) MultipartFile file) {
    	BatchCreateAssetRequest req = null;
		BaseWebResponse resp = null; 

		try {
			
			//1.===参数校验
			if(file==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
			//2.解析文件
			int totalCount=0;
			int errorCount=0;
			Map<String,List<String>> errorMap=new TreeMap<String,List<String>>();
			List<AssetAddVO> assetAddVOList=new ArrayList<AssetAddVO>();
		    ExcelUtil eu=new ExcelUtil();
		    eu.setStartReadPos(0);
		    //eu.setExcelPath(file.getInputStream());
		    List<Row> rowList = eu.readExcel_xlsx(file.getInputStream());
		    totalCount=rowList.size();

            List<ErrorModel> errorModelList = new ArrayList<ErrorModel>();
            String titles[] = {"资产名称","融资类型","募集金额","成立日","起息日","到期日","结息日",
                    "还款方式","预期年利率","项目描述","还款保障措施","增信措施","融资方编号","出资方编号"};
            for(int i = 0;i < titles.length;i++){
                Row row = rowList.get(0);//获取标题行
                String str = getStringValue(row.getCell(i));
                if(!titles[i].equals(str)){
                    ErrorModel errorModel = new ErrorModel();
                    List<String> errStr=new ArrayList<String>();
                    errStr.add("第1行-标题【"+str + "】有误");
                    resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"读取文件失败");
                    errorModel.setErrorList(errStr);
                    errorModelList.add(errorModel);
                    Map<String,Object> respMap = new HashMap<String,Object>();
                    respMap.put("addition", "");
                    respMap.put("totalCount", 0);
                    respMap.put("errorCount", errorModelList.size());
                    respMap.put("errorMap", errorModelList);
                    resp.setData(respMap);
                    return resp;
                }
            }
            
		    for(int i=1;i<totalCount;i++){
		    	String assetName="";
		    	int count = 0;
		    	List<String> errStr=new ArrayList<String>();
		    	AssetAddVO adv=new AssetAddVO();
		    	Row row = rowList.get(i);
		    	for(int j=0;j<14;j++){
		    		Cell cell = row.getCell(j);
		    		
		    		if(cell==null && j!=8 && j!=10 && j!=11 && j!=13){
		    			assetName = "第"+(i)+"行";
		    			errStr.add(assetName +"-第"+(j+1)+"列的值不能为空");
		    			errorCount++;
		    			count++;
		    			continue;
		    		}
		    		
		    		int cellType=0;
		    		if (cell != null) {
		    			cellType = cell.getCellType();
		    		}
		    		try {
		    			if(j==0){
		    				if(cellType==Cell.CELL_TYPE_BLANK){
		    					assetName = "第"+(i)+"行";
				    			errStr.add(assetName+"-第"+(j+1)+"列的【资产名称】值不能为空");
				    			errorCount++;
				    			count++;
		    				}else if(cellType==Cell.CELL_TYPE_STRING){
		    					adv.setAssetName(cell.getStringCellValue());
		    					assetName=cell.getStringCellValue()+"(第"+(i)+"行)";
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【资产名称】值数据格式不正确，必须为文本格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==1){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【融资类型】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_NUMERIC){
			    				adv.setAssetType(Double.valueOf(cell.getNumericCellValue()).intValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【融资类型】值数据格式不正确，必须为数字格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==2){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【募集金额】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_NUMERIC){
			    				adv.setAssetAmount(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【募集金额】值数据格式不正确，必须为数字格式");
		    					errorCount++;
		    				}
			    			
			    			continue;
			    		}else if(j==3){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【成立日】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_NUMERIC&&DateUtil.isCellDateFormatted(cell)){
			    				adv.setEstablishTime(cell.getDateCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【成立日】值数据格式不正确，必须为时间格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==4){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【起息日】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_NUMERIC&&DateUtil.isCellDateFormatted(cell)){
			    				adv.setValueStartTime(cell.getDateCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【起息日】值数据格式不正确，必须为时间格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==5){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【到期日】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_NUMERIC&&DateUtil.isCellDateFormatted(cell)){
			    				adv.setExpireTime(cell.getDateCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【到期日】值数据格式不正确，必须为时间格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==6){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【结息日】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_NUMERIC&&DateUtil.isCellDateFormatted(cell)){
			    				adv.setValueEndTime(cell.getDateCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【结息日】值数据格式不正确，必须为时间格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==7){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【还款方式】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_NUMERIC){
			    				adv.setRepaymentType(Double.valueOf(cell.getNumericCellValue()).intValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【还款方式】值数据格式不正确，必须为数字格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==8){
			    			if (cell == null || cellType==Cell.CELL_TYPE_BLANK) {
			    				adv.setYieldRate(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
			    			}else if(cellType==Cell.CELL_TYPE_NUMERIC){
			    				adv.setYieldRate(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【预期年利率】值数据格式不正确，必须为数字格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==9){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【项目描述】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_STRING){
			    				adv.setProjectDesc(cell.getStringCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【项目描述】值数据格式不正确，必须为文本格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==10){
			    			if (cell == null || cellType==Cell.CELL_TYPE_BLANK) {
			    				adv.setRepayGuarenteeMode("");
			    			}else if(cellType==Cell.CELL_TYPE_STRING){
			    				adv.setRepayGuarenteeMode(cell.getStringCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【还款保障措施】值数据格式不正确，必须为文本格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==11){
			    			if (cell == null || cellType==Cell.CELL_TYPE_BLANK) {
			    				adv.setCreditMode("");
			    			}else if(cellType==Cell.CELL_TYPE_STRING){
			    				adv.setCreditMode(cell.getStringCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【增信措施】值数据格式不正确，必须为文本格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==12){
			    			if(cellType==Cell.CELL_TYPE_BLANK){
			    				errStr.add(assetName+"-第"+(j+1)+"列的【融资方编号】值不能为空");
				    			errorCount++;
				    			count++;
			    			} else if(cellType==Cell.CELL_TYPE_STRING){
			    				adv.setFinanceSubjectCode(cell.getStringCellValue());
		    				}else{
		    					errStr.add(assetName+"-第"+(j+1)+"列的【融资方编号】值数据格式不正确，必须为文本格式");
		    					errorCount++;
		    				}
			    			continue;
			    		}else if(j==13){
			    			if (cell == null || cellType==Cell.CELL_TYPE_BLANK) {
			    				adv.setProvideLoanCode("");
							} else if (cellType == Cell.CELL_TYPE_STRING) {
								adv.setProvideLoanCode(cell.getStringCellValue());
							} else {
								errStr.add(assetName + "-第" + (j + 1) + "列的【出资方编号】值数据格式不正确，必须为文本格式");
								errorCount++;
							}
							continue;
			    		}
					} catch (Exception e) {
						errStr.add("数据读取异常");
						errorCount++;
		    			continue;
					}
		    		
		    	}
		    	
		    	if(errStr!=null&&errStr.size()>0){
		    		if (count == 10) {
						errorCount = errorCount - 10;
					} else {
                        ErrorModel errorModel = new ErrorModel();//修改和授信错误提示一致
                        errorModel.setErrorList(errStr);//修改处
                        errorModelList.add(errorModel);//修改处
						errorMap.put((i + 1) + "", errStr);
					}
		    	}else{
		    		assetAddVOList.add(adv);
		    	}
		    }
		    
		    if(errorModelList!=null&&errorModelList.size()>0){//修改和授信错误提示一致
				resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
				Map<String,Object> respMap = new HashMap<String,Object>();
				respMap.put("addition", "");
				respMap.put("totalCount", totalCount);
				respMap.put("errorCount", errorCount);
				respMap.put("errorMap", errorModelList);//修改处
				resp.setData(respMap);
				return resp;
		    }
			
			//3.===参数对象封装
			List<CreateAssetRequest> createAssetRequestList=new ArrayList<CreateAssetRequest>();
			req = new BatchCreateAssetRequest();
			for(int i=0;i<assetAddVOList.size();i++){
				CreateAssetRequest car=new CreateAssetRequest();
				PropertyUtils.copyProperties(car, assetAddVOList.get(i));
				try {
					car.setCreateBy(httpSessionUtils.getCuruser().getName());
				} catch (Exception e) {
					log.error("获取当前登录用户系统异常",e);
				}
				
				if(car.getCreateBy()==null){
					car.setCreateBy("1");
				}
				car.setValueDays(Long.valueOf((com.zillionfortune.boss.common.utils.DateUtil.getDaysBetweenTwoDate(car.getExpireTime(), car.getValueStartTime()))).intValue());
				createAssetRequestList.add(car);
			}
			req.setCreateAssetRequestList(createAssetRequestList);
			//3.===调用新增方法
			resp = assetBiz.batchCreateAsset(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
    /** 
     * 查询资列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/querylist", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryList(@RequestBody AssetQueryListVO vo) {
    	
    	log.info("AssetController.querylist.req:" + JSON.toJSONString(vo));
		
    	QueryAssetListForManageRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryAssetListForManageRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = assetBiz.queryAssetList(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetController.querylist.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    /**
     * 查询资产详情
     * @param req
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse query(@RequestBody AssetQueryVO vo) {
    	
    	log.info("AssetPoolController.query.req:" + JSON.toJSONString(vo));
		
    	QueryAssetRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryAssetRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = assetBiz.queryAsset(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetController.query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
    }
    
    /**
     * 查询资产审核列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryassetlistforapproval", method = RequestMethod.POST)
    @ResponseBody
    public BaseWebResponse queryAssetListForApproval(@RequestBody QueryAssetApprovalListVO vo) {
    	
    	log.info("AssetController.queryAssetListForApproval.req:" + JSON.toJSONString(vo));
		
    	QueryAssetListForManageRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(vo==null){
	               resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ILLEGAL_PARAMETER.code(),
	                		ResultCode.ILLEGAL_PARAMETER.desc());
			}
		
			//2.===参数对象封装
			req = new QueryAssetListForManageRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//3.===调用新增方法
			resp = assetBiz.queryAssetListForApproval(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("AssetController.queryAssetListForApproval.resp:" + JSON.toJSONString(resp));
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
   
}
