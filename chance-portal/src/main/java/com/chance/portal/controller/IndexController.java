package com.chance.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chance.common.pojo.ChanceResult;
import com.chance.portal.service.ContentService;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 返回首页视图，以及广告位的json数据模型
	 * @param model 封装返回的json数据模型
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		String adJson = contentService.getContentList();
		model.addAttribute("ad1", adJson);
		
		return "index";
	}
	
	@RequestMapping(value = "/httpclient/post", method=RequestMethod.POST)
	@ResponseBody
	public ChanceResult testPost(String username, String password) {
		String result = username + "-------" + password;
		return ChanceResult.ok(result);
	}
	
}