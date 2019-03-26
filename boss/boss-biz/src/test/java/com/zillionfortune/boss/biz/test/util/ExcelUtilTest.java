package com.zillionfortune.boss.biz.test.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.zb.fincore.ams.facade.dto.req.BatchCreateAssetRequest;
import com.zb.fincore.ams.facade.dto.req.CreateAssetRequest;
import com.zillionfortune.boss.common.utils.ExcelUtil;

public class ExcelUtilTest {

	@Test
	public void testRead() {  
	    try {  
	          
//	        ExcelUtil eu = new ExcelUtil();  
//	        eu.setExcelPath("e:\\工作\\运营平台\\资产批量导入模板.xlsx"); 
//	          
//	        System.out.println("=======测试Excel 默认 读取========");  
//	        eu.setStartReadPos(1);
//	        List<Row> rowList = eu.readExcel();  
//	        for (Row row : rowList) {
//				Iterator<Cell> cellIterator = row.cellIterator();
//				while(cellIterator.hasNext()){
//					Cell nextCell = cellIterator.next();
//					System.out.println(nextCell.getStringCellValue());
//				}
//			}
	        List<AssetAddVO> assetAddVOList=new ArrayList<AssetAddVO>();
		    ExcelUtil eu=new ExcelUtil();
		    eu.setStartReadPos(1);
		    eu.setExcelPath("e:\\工作\\运营平台\\资产批量导入模板.xlsx");
		    List<Row> rowList = eu.readExcel();
		    for(int i=0;i<rowList.size();i++){
		    	AssetAddVO adv=new AssetAddVO();
		    	Row row = rowList.get(i);
		    	for(int j=0;j<row.getLastCellNum();j++){
		    		Cell cell = row.getCell(j);
		    		if(j==0){
		    			adv.setAssetName(cell.getStringCellValue());
		    			continue;
		    		}else if(j==1){
		    			adv.setAssetType(Double.valueOf(cell.getNumericCellValue()).intValue());
		    			continue;
		    		}else if(j==2){
		    			adv.setAssetAmount(new BigDecimal(cell.getNumericCellValue()));
		    			continue;
		    		}else if(j==3){
		    			adv.setEstablishTime(cell.getDateCellValue());
		    			continue;
		    		}else if(j==4){
		    			adv.setValueStartTime(cell.getDateCellValue());
		    			continue;
		    		}else if(j==5){
		    			adv.setExpireTime(cell.getDateCellValue());
		    			continue;
		    		}else if(j==6){
		    			adv.setValueEndTime(cell.getDateCellValue());
		    			continue;
		    		}else if(j==7){
		    			adv.setRepaymentType(Double.valueOf(cell.getNumericCellValue()).intValue());
		    			continue;
		    		}else if(j==8){
		    			adv.setYieldRate(new BigDecimal(cell.getNumericCellValue()));
		    			continue;
		    		}else if(j==9){
		    			adv.setProjectDesc(cell.getStringCellValue());
		    			continue;
		    		}else if(j==10){
		    			adv.setRepayGuarenteeMode(cell.getStringCellValue());
		    			continue;
		    		}else if(j==11){
		    			adv.setCreditMode(cell.getStringCellValue());
		    			continue;
		    		}else if(j==12){
		    			adv.setFinanceSubjectCode(cell.getStringCellValue());
		    			break;
		    		}
		    	}
		    	
		    	assetAddVOList.add(adv);
		
		    }
			
			//3.===参数对象封装
			List<CreateAssetRequest> createAssetRequestList=new ArrayList<CreateAssetRequest>();
			BatchCreateAssetRequest req = new BatchCreateAssetRequest();
			for(int i=0;i<assetAddVOList.size();i++){
				CreateAssetRequest car=new CreateAssetRequest();
				PropertyUtils.copyProperties(car, assetAddVOList.get(i));
				createAssetRequestList.add(car);
			}
	          
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	} 

}
