package com.chance.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chance.common.utils.FtpUtil;
import com.chance.common.utils.IDUtils;
import com.chance.service.PictureService;

/**
 * 图片上传服务
 * 
 * @author Chance
 *
 */
@Service
public class PictureServiceImpl implements PictureService {

	// 使用@Value注解可以帮助从properties文件中取值，并赋值给被注解的字段
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
	public Map<String, Object> uploadPicture(MultipartFile uploadFile) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			// 取原文件名
			String originalFilename = uploadFile.getOriginalFilename();
			
			// 生成新文件名
			String newFilename = IDUtils.genImageName();
			newFilename += originalFilename.substring(originalFilename.lastIndexOf("."));
			
			// 图片上传
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newFilename, uploadFile.getInputStream());
			
			// 上传出错
			if(!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			// 上传成功
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newFilename);
			return resultMap;
			
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
