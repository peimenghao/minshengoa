package com.minsheng.oa.main.ueditor.upload;


import com.minsheng.oa.main.ueditor.PathFormat;
import com.minsheng.oa.main.ueditor.define.AppInfo;
import com.minsheng.oa.main.ueditor.define.BaseState;
import com.minsheng.oa.main.ueditor.define.FileType;
import com.minsheng.oa.main.ueditor.define.State;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class BinaryUploader {
    static Logger logger = LoggerFactory.getLogger(BinaryUploader.class);
    
	public static final State save(HttpServletRequest request, Map<String, Object> conf) {
		
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("upfile");

			String savePath = (String) conf.get("savePath");   //图片名字格式
			long maxSize = ((Long) conf.get("maxSize")).longValue();
			String localSavePathPrefix = (String) conf.get("localSavePathPrefix"); //上传路径前缀
			System.out.println("BinaryUploader---localSavePathPrefix--路径前缀=="+localSavePathPrefix);
			String originFileName = file.getOriginalFilename();   //文件原始名
			String suffix = FileType.getSuffixByFilename(originFileName);  //图片后缀
			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());//没有后缀的文件名字
			System.out.println("BinaryUploader---不带后缀的文件名字originFileName=="+originFileName);
			savePath = savePath + suffix;  //图片命名格式+图片后缀
			

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			savePath = PathFormat.parse(savePath, originFileName); //图片命名格式+图片后缀
			localSavePathPrefix = localSavePathPrefix + savePath; 
			String physicalPath = localSavePathPrefix;
			logger.info("BinaryUploader physicalPath:{},savePath:{}",localSavePathPrefix,savePath);
			InputStream is = file.getInputStream();
			
			//在此处调用ftp的上传图片的方法将图片上传到文件服务器
			String path = physicalPath.substring(0, physicalPath.lastIndexOf("/"));  //不带文件名字的保存路径
			//文件名+后缀
			String savefileName = physicalPath.substring(physicalPath.lastIndexOf("/")+1, physicalPath.length());
			//State storageState = StorageManager.saveFileByInputStream(request, is, path, picName, maxSize);//保存到服务器
			System.out.println("BinaryUploader---path=="+path+"----savefileName====="+savefileName);

			 // State storageState = StorageManager.saveFileToIdea(request, is, path, savefileName, maxSize);
			State storageState = StorageManager.saveTofastDFS(request, is, path, savefileName, maxSize);
			  is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("type", suffix); //后缀
				System.out.println("BinaryUploader-----返回原始文件全名==="+originFileName + suffix);
				storageState.putInfo("original", originFileName + suffix); //返回原始文件名
			}

			return storageState;
		} catch (Exception e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		}
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
