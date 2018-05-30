package com.chance.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chance.common.utils.JsonUtils;
import com.chance.service.PictureService;

@Controller
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	
	@ResponseBody
	@RequestMapping("/pic/upload")
	public String pictureUpload(MultipartFile uploadFile) {
		Map<String, Object> result = pictureService.uploadPicture(uploadFile);
		// 为了保证兼容性，需要把result转换成json格式的字符串
		String json = JsonUtils.objectToJson(result);
		return json;
	}
}
