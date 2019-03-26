package com.zb.p2p.paychannel.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * ClassName: OssUploadUtil <br/>
 * Function: Oss上传工具. <br/>
 * Date: 2016年12月15日 下午6:34:55 <br/>
 *
 * @author mabiao@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
public class OssFileUtil {

	protected static Logger LOGGER = (Logger) LoggerFactory
			.getLogger(OssFileUtil.class);

	private static OSSClient client;
	
	/**
	 * OssFileUtil 构造器
	 *
	 * @param ossEndpoint 阿里云OSS_ENDPOINT Url
	 * @param accessId 阿里云ACCESS_ID
	 * @param accessKey 阿里云ACCESS_KEY
	 */
	public OssFileUtil(String ossEndpoint, String accessId, String accessKey) {
		client  = new OSSClient(ossEndpoint, accessId, accessKey);
	}

	/**
	 * 
	 * 创建Bucket
	 * 
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            BUCKET名
	 * @throws OSSException
	 * @throws ClientException
	 */

	public void createBucket(String bucketName)
			throws ClientException, OSSException {
		LOGGER.info("OssUploadUtil.createBucket.OSSClient:"
				+ JSON.toJSONString(client)
				+ "/n OssUploadUtil.createBucket.bucketName:" + bucketName);
		try {
			client.createBucket(bucketName);
		} catch (ServiceException e) {
			if (!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())) {
				throw e;
			}
		}

	}

	/**
	 * 
	 * 上传文件
	 * 
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            Bucket名
	 * @param Objectkey
	 *            上传到OSS起的名
	 * @throws OSSException
	 * @throws ClientException
	 * @throws FileNotFoundException
	 */

	public String uploadFile(String bucketName,InputStream input, 
			String objectKey) throws Exception,
			ClientException, FileNotFoundException {

		ObjectMetadata objectMeta = new ObjectMetadata();// 元数据对象
		objectMeta.setContentLength(Long.parseLong(input.available() + ""));// 设置传输文件长度
		objectMeta.setContentType(readFileType(objectKey.substring(objectKey.lastIndexOf("."))));// 设置文件类型
		PutObjectResult result = client.putObject(bucketName, objectKey, input, objectMeta);
		// 上传成功返回结果
		if (null != result) {
			return result.getETag();// 上传成功
		}

		return "";
	}

	/**
	 * 
	 * 删除一个Bucket和其中的Objects
	 * 
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            Bucket名
	 * @throws OSSException
	 * @throws ClientException
	 */

	public void deleteBucket(OSSClient client, String bucketName)
			throws OSSException, ClientException {

		ObjectListing ObjectListing = client.listObjects(bucketName);
		List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();

		for (int i = 0; i < listDeletes.size(); i++) {

			String objectName = listDeletes.get(i).getKey();
			System.out.println("objectName = " + objectName);

			// 如果不为空，先删除bucket下的文件
			client.deleteObject(bucketName, objectName);
		}

		client.deleteBucket(bucketName);

	}
	
	/**
	 * 
	 * 删除一个Bucket下对应的文件
	 * 
	 * @param bucketName
	 *            Bucket名
	 * @param objectKey
	 *            上传到OSS起的名
	 * @throws OSSException
	 * @throws ClientException
	 */

	public void deleteFile(String bucketName, String objectKey)
			throws OSSException, ClientException, FileNotFoundException {

		client.deleteObject(bucketName, objectKey);
	}

	/**
	 * 
	 * 下载文件
	 * 
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            Bucket名
	 * @param Objectkey
	 *            上传到OSS起的名
	 * @param filename
	 *            文件下载到本地保存的路径
	 * @throws OSSException
	 * @throws ClientException
	 * @throws FileNotFoundException
	 */

	public void downloadFile(String bucketName,
			String objectKey, String fileName) throws OSSException,
			ClientException, FileNotFoundException {

		// 获取Object，返回结果为OSSObject对象
		OSSObject object = client.getObject(bucketName, objectKey);

		// 获取Object的输入流
		InputStream objectContent = object.getObjectContent();

		// 输出流
		FileOutputStream out = new FileOutputStream(fileName);
		int BUFFER_SIZE = 8096;// 缓冲大小
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			while ((size = objectContent.read(buf)) != -1) {
				out.write(buf, 0, size);
			}
			objectContent.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				objectContent.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * readFileStream:读取文件流. <br/>
	 *
	 * @param bucketName
	 * @param Objectkey
	 * @return
	 */
	public InputStream readFileStream(String bucketName, String Objectkey) {
		LOGGER.info("OssFileUtil.readFileStream.bucketName:"+bucketName+
				";OssFileUtil.readFileStream.Objectkey:"+Objectkey);

		// 获取Object，返回结果为OSSObject对象
		OSSObject object = null;
		
		try {
			object = client.getObject(bucketName, Objectkey);
			
			if (null == object) {
				LOGGER.info("OssFileUtil.readFileStream.object:没有找到相应文件!");
				LOGGER.info("OssFileUtil.readFileStream.object:"+JSON.toJSONString(object));
				return null;
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage(),e);
		}

		return object.getObjectContent();
	}
	

	/**
	 * isRename:上传文件是否重命名. <br/>
	 * @param filePath
	 * @param fileName
	 * @param isRename
	 * @return
	 */
	public static String isRename(String filePath, String fileName,
			boolean isRename) {// filePath：bucket下文件目录;fileName:文件名称;true：重命名
		String newName = filePath + fileName;
		if (isRename) {
			String extention = fileName.substring(fileName.lastIndexOf("."));// 后缀名
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
			String dateFormat = df.format(new Date());
			newName = filePath + "/" + dateFormat + extention;// 文件重命名
		}

		return newName;
	}

	/**
	 * 返回http头
	 * 
	 * @param fileType
	 * @return
	 */
	public static String readFileType(String fileType) {
		if (fileType.toLowerCase().equals("bmp")) {
			return "image/bmp";
		}
		if (fileType.toLowerCase().equals("gif")) {
			return "image/gif";
		}
		if (fileType.toLowerCase().equals("jpeg")
				|| fileType.toLowerCase().equals("jpg")
				|| fileType.toLowerCase().equals("png")) {
			return "image/jpeg";
		}
		if (fileType.toLowerCase().equals("html")) {
			return "text/html";
		}
		if (fileType.toLowerCase().equals("txt")) {
			return "text/plain";
		}
		if (fileType.toLowerCase().equals("vsd")) {
			return "application/vnd.visio";
		}
		if (fileType.toLowerCase().equals("pptx")
				|| fileType.toLowerCase().equals("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (fileType.toLowerCase().equals("xlsx")
				|| fileType.toLowerCase().equals("xls")) {
			return "application/vnd.ms-excel";
		}
		if (fileType.toLowerCase().equals("docx")
				|| fileType.toLowerCase().equals("doc")) {
			return "application/msword";
		}
		if (fileType.toLowerCase().equals("xml")) {
			return "text/xml";
		}
		return "text/html";
	}
	
	public OSSClient getClient() {
		return client;
	}

	public void setClient(OSSClient client) {
		OssFileUtil.client = client;
	}

	public static void main(String[] args) {
		String filePath = "E:/t-t.png";
		String objectKey = "uptest_pt.png";
		FileInputStream input = null;
		try {
			File file = new File(filePath);
			input = new FileInputStream(file);
			OssFileUtil client = new OssFileUtil("http://oss-cn-shanghai.aliyuncs.com", "LTAIHCgkz0SQn4c9", "G8I2BpLp9iC5Wx0J6nlmrvkjacqAKt");
			String result = client.uploadFile("bucket-zb", input,objectKey);
			System.out.println(result);
			
		} catch (OSSException e) {
			LOGGER.info(e.getMessage(),e);
			e.printStackTrace();
		} catch (ClientException e) {
			LOGGER.info(e.getMessage(),e);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			LOGGER.info(e.getMessage(),e);
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.info(e.getMessage(),e);
			e.printStackTrace();
		} finally{
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.info(e.getMessage(),e);
			}
		}
	}

}
