package com.cencl.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cencl.common.utils.FtpUtil;
import com.cencl.common.utils.IDUtils;
import com.cencl.service.PictureService;

/*
 * 	接收Controller传递过来的参数(一个文件MutiPartFile对象),
 * 把文件上传到ftp服务器，生成一个新的文件名，
 * 返回文件URL路径,需要包装成图片上传插件要求的格式
 * */
@Service
public class PictureServiceImpl implements PictureService {
	
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap = new HashMap<>();
		try {
			//生成一个新的文件名
			//取原始文件名
			String oldName = uploadFile.getOriginalFilename();
			//生成新文件名
			//UUID.randomUUID();
			String newName = IDUtils.genImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			//图片上传
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, 
					FTP_BASE_PATH, imagePath, newName, uploadFile.getInputStream());
			//返回结果
			if(!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return resultMap;
			
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
